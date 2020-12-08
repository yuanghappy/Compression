import java.util.ArrayList;
import java.util.Random;


class PriorityQueue<T> {
	
	private int startindex, endindex, midpoint;
	
	private ArrayList<Node> Queue = new ArrayList<Node>();
	
	
	public boolean add(T info, int priority){
		//1
		if(Queue.size() == 0){
			Queue.add(new Node(info, priority));
			System.out.println("Mode 1");
			return true;
		}
		//2
		else if (Queue.get(Queue.size()-1).priority <= priority){
			Queue.add(new Node(info, priority));
			System.out.println("Mode 2");
			return true;
		}
		//3
		else if (Queue.get(0).priority >= priority){
			Queue.add(0, new Node(info, priority));
			System.out.println("Mode 3");
			return true;
		}
		//7
		else if (Queue.size() == 2){
			Queue.add(1, new Node(info, priority));
			System.out.println("7");
			return true;
		}
		startindex = 0;
		endindex = Queue.size()-1;
		
		while(true){
			midpoint = (startindex+endindex)/2;
			System.out.println(midpoint);
			//4
			if(Queue.get(midpoint).priority == priority || (Queue.get(midpoint-1).priority < priority && Queue.get(midpoint+1).priority > priority)){
				if(Queue.get(midpoint).priority < priority){
					Queue.add(midpoint+1, new Node(info, priority));
					System.out.println("Mode 4");
					return true;
				}
				Queue.add(midpoint, new Node(info, priority));
				System.out.println("Mode 4");
				return true;
			}
			//5
			else if(Queue.get(midpoint).priority > priority){
				endindex = midpoint;
				System.out.println("Mode 5");
			}
			//6
			else if(Queue.get(midpoint).priority < priority){
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
	
	public int size(){
		return Queue.size();
	}
	
	//removing the node with the smallest priority number
	public Node<T> pop(){
		return Queue.remove(0);
	}
	
	public void print(){
		for(int i = 0; i < Queue.size(); i++){
			System.out.println(Queue.get(i).info + ", " + Queue.get(i).priority);
		}
	}
	
	public Node get(int i){
		return Queue.get(i);
	}
	
	public static void main (String[] args){
		PriorityQueue<String> q = new PriorityQueue<String>();
		for(int i = 0; i < 20; i++){
			q.add("test", new Random().nextInt(100));
		}
		q.print();
		
	}

}
