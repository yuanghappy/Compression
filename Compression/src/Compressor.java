import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Compressor {

	Map<Character, Integer> FrequencyMap;
	Map<Character, String> CodeMap;
	PriorityQueue<Branch> q = new PriorityQueue<Branch>();
	
	public void CalculateFrequency(String path){
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
		} catch (IOException e) {
			System.out.print("file not found");
			e.printStackTrace();
		}
	}
	
	public void PrintFrequencyMap(){
		System.out.println(FrequencyMap.entrySet());
	}
	
	public void AddtoPriorityQueue(){
		
		for (Entry<Character, Integer> entry : FrequencyMap.entrySet()){
			q.add(new Branch<Character>(entry.getKey()), entry.getValue());
		}
	}
	
	public void BuildTree(){
		while(q.size()>1){
			Node<Branch>branch1 = q.pop();
			Node<Branch>branch2 = q.pop();
			q.add(new Branch(branch2.info, branch1.info), branch2.priority+branch1.priority);
		}
	}
	
	public void PrintPriorityQueue(){
		q.print();
	}
	
	public boolean Compress(){
		CodeMap = new HashMap<Character, String>(); 
		BuildCode((Branch<Character>) q.get(0).info, "");

		
		return true;
	}
	
	public Boolean BuildCode(Branch<Character> b, String code){
		
		if(b.isLeaf){
			CodeMap.put((Character) b.info, code);
			return true;
		}
		BuildCode(b.leftChild, code + "0");
		BuildCode(b.rightChild, code + "1");
		return true;
	}
	
	public static void main(String[] args){
		Compressor comp = new Compressor();
		comp.CalculateFrequency("testfile");
		comp.AddtoPriorityQueue();
		comp.PrintFrequencyMap();
		comp.BuildTree();
		comp.PrintPriorityQueue();
		comp.Compress();
		comp.PrintFrequencyMap();
		System.out.println(comp.CodeMap.toString());
		
		
	}
}
