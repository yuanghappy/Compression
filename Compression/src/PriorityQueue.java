import java.util.ArrayList;
import java.util.Random;


class PriorityQueue<T> {
	
	int startindex, endindex, midpoint;
	
	ArrayList<Node> Queue = new ArrayList<Node>();
	
	private class Node {
		
		private T info;
		private int frequency;
		
		public Node(T info, int frequency) {
			this.info = info;
			this.frequency = frequency;
		}
	}
	
	public boolean add(T info, int frequency){
		//1
		if(Queue.size() == 0){
			Queue.add(new Node(info, frequency));
			System.out.println("Mode 1");
			return true;
		}
		//2
		else if (Queue.get(Queue.size()-1).frequency <= frequency){
			Queue.add(new Node(info, frequency));
			System.out.println("Mode 2");
			return true;
		}
		//3
		else if (Queue.get(0).frequency >= frequency){
			Queue.add(0, new Node(info, frequency));
			System.out.println("Mode 3");
			return true;
		}
		//7
		else if (Queue.size() == 2){
			Queue.add(1, new Node(info, frequency));
			System.out.println("7");
			return true;
		}
		startindex = 0;
		endindex = Queue.size()-1;
		while(true){
			midpoint = (startindex+endindex)/2;
			System.out.println(midpoint);
			//4
			if(Queue.get(midpoint).frequency == frequency || (Queue.get(midpoint-1).frequency < frequency && Queue.get(midpoint+1).frequency > frequency)){
				if(Queue.get(midpoint).frequency < frequency){
					Queue.add(midpoint+1, new Node(info, frequency));
					System.out.println("Mode 4");
					return true;
				}
				Queue.add(midpoint, new Node(info, frequency));
				System.out.println("Mode 4");
				return true;
			}
			//5
			else if(Queue.get(midpoint).frequency > frequency){
				endindex = midpoint;
				System.out.println("Mode 5");
			}
			//6
			else if(Queue.get(midpoint).frequency < frequency){
				startindex = midpoint;
				System.out.println("Mode 6");
			}
		}
	}
	
	public boolean remove(T info){
		for(int i = 0; i < Queue.size(); i++){
			if(Queue.get(i).info == info){
				Queue.remove(i);
			}
		} 
		return true;
	}
	
	public void print(){
		for(int i = 0; i < Queue.size(); i++){
			System.out.println(Queue.get(i).frequency);
		}
	}
	
	public static void main (String[] args){
		PriorityQueue<String> q = new PriorityQueue<String>();
		for(int i = 0; i < 20; i++){
			q.add("test", new Random().nextInt(100));
		}
		q.remove("test");
		q.print();
		
	}

}
