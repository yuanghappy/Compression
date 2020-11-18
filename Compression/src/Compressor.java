import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Compressor {

	Map<Character, Integer> FrequencyMap;
	
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
	
	public static void main(String[] args){
		Compressor comp = new Compressor();
		comp.CalculateFrequency("testfile");
		comp.PrintFrequencyMap();
	}
}
