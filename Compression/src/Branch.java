
public class Branch<T> {
	
	Branch leftChild;
	Branch rightChild;
	boolean isLeaf;
	T info;
	//hold information
	
	public Branch(Branch firstchild, Branch secondchild){
		leftChild = firstchild;
		rightChild = secondchild;
		isLeaf = false;
	}

	public Branch(T info){
		this.info = info;
		isLeaf = true;
	}
}
