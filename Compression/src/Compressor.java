import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Compressor {

	private Map<Character, Integer> FrequencyMap;
	private Map<Character, String> CodeMap;
	private Map<String, String> ReverseCodeMap;
	private PriorityQueue<Branch> q = new PriorityQueue<Branch>();
	
	private boolean CalculateFrequency(String path){
		FrequencyMap = new HashMap<Character, Integer>(); 
		try {
			FileReader reader = new FileReader(path);
			int c;
			while((c = reader.read()) != -1){
				if (FrequencyMap.containsKey((char)c)){
					FrequencyMap.put((char)c, FrequencyMap.get((char)c)+1);
				}else{
					FrequencyMap.put((char)c, 1);
				}
			}
			reader.close();
		} catch (IOException e) {
			System.out.print("file not found");
			e.printStackTrace();
		}
		return true;
	}
	
	private void PrintFrequencyMap(){
		System.out.println("FrequencyMap");
		System.out.println(FrequencyMap.entrySet());
		System.out.println();
	}
	
	private boolean AddtoPriorityQueue(){
		for (Entry<Character, Integer> entry : FrequencyMap.entrySet()){
			q.add(new Branch<Character>(entry.getKey()), entry.getValue());
		}
		return true;
	}
	
	private boolean BuildTree(){
		while(q.size()>1){
			Node<Branch>branch1 = q.pop();
			Node<Branch>branch2 = q.pop();
			q.add(new Branch(branch2.info, branch1.info), branch2.priority+branch1.priority);
		}
		return true;
	}
	
	private void PrintPriorityQueue(){
		System.out.println("PriorityQueue:");
		q.print();
		System.out.println();
	}
	
	private boolean Compress(String path) throws FileNotFoundException{
		CodeMap = new HashMap<Character, String>(); 
		BuildCode((Branch<Character>) q.get(0).info, "");
		//write compressed file
		String charactercode;
			BufferedBitWriter bbw = new BufferedBitWriter(path+"(Comp)");
		try {
			FileReader reader = new FileReader(path);
			int c;
			while((c = reader.read()) != -1){
				charactercode = CodeMap.get((char)c);
				for(int i = 0; i < charactercode.length(); i++){
					if(charactercode.charAt(i) == '0' ){
						bbw.writeBit(false);
					}else{
						bbw.writeBit(true);
					}
				}
			}
			bbw.close();
		} catch (IOException e) {
			System.out.print("file not found");
			e.printStackTrace();
		}
		
		File codefile = new File(path + "(Code)");
		//write the code map to file for storage
				try {
		   		 BufferedWriter writer = new BufferedWriter(new FileWriter(codefile));
		   		 for (Entry<Character, String> entry : CodeMap.entrySet()){
						writer.write(entry.getKey());
		   				writer.newLine();
		   				writer.write(entry.getValue());
		   				writer.newLine();
		   			}
		   		 writer.write("here&starts%The/ComPressED//File123123123123");
		   		 writer.close();
		   	    } catch (IOException a) {
		   	      System.out.println("An error occurred.");
		   	      a.printStackTrace();
		   	    }
				
		
		return true;
	}
	
	private boolean BuildCode(Branch<Character> b, String code){
		if(b.isLeaf){
			CodeMap.put((Character) b.info, code);
			return true;
		}
		BuildCode(b.leftChild, code + "0");
		BuildCode(b.rightChild, code + "1");
		return true;
	}
	
	private boolean ReadCompressionCode(String CodeFilePath) throws IOException{
		//use stored compression code to construct a reverse code map
				BufferedReader in = new BufferedReader(new FileReader(CodeFilePath));
				ReverseCodeMap = new HashMap<String, String>(); 
				String CodeLine;
				while((CodeLine = in.readLine()) != null){
					if(CodeLine.length() != 0){
						ReverseCodeMap.put(in.readLine(), CodeLine);
					}else{
						in.readLine();
						ReverseCodeMap.put(in.readLine(), "\n");
					}
				}
				in.close();
				System.out.println("Decompression Code Map:");
				System.out.println(ReverseCodeMap.toString() + "\n");
				return true;
	}
	
	private boolean WriteDecompressedFile(String CompFilePath){
		File DecompressedFile = new File(CompFilePath.substring(0, CompFilePath.length()-6) + "(Decomp)");
    	try {
    		 BufferedWriter writer = new BufferedWriter(new FileWriter(DecompressedFile));
    		 BufferedBitReader bitreader = new BufferedBitReader(CompFilePath);
    		 String readbitstring = "";
    		 while(bitreader.hasNext()){
    			 if(bitreader.readBit()==true){
    				 readbitstring += "1";
    			 }else{
    				 readbitstring += "0";
    			 }
    			 if (ReverseCodeMap.containsKey(readbitstring)){
    				 writer.write(ReverseCodeMap.get(readbitstring));
    				 readbitstring = "";
    			 }
    		 }
    		 writer.close();
    	    } catch (IOException a) {
    	      System.out.println("An error occurred.");
    	      a.printStackTrace();
    	    }
    	return true;
	}
	
	//method available to users
	public boolean CompressFile(String path) throws FileNotFoundException{
		CalculateFrequency(path);
		PrintFrequencyMap();
		AddtoPriorityQueue();
		BuildTree();
		PrintPriorityQueue();
		Compress(path);
		System.out.println("Code Map:");
		System.out.println(CodeMap.toString() + "\n");
		return true;
	}
	
	//method available to users
	public boolean DecompressFile(String CompFilePath, String CodeFilePath) throws IOException{
		//use stored compression code to construct a reverse code map
		ReadCompressionCode(CodeFilePath);
		//read bits from the compressed file, compare that to reverse code map, and write
		//char to decompressed file
		WriteDecompressedFile(CompFilePath);
		return true;
	}
	
	public static void main(String[] args) throws IOException{
		Compressor comp = new Compressor();
		comp.CompressFile("testfile");
		comp.DecompressFile("testfile(Comp)", "testfile(Code)");
	}
}
