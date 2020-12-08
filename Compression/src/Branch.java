
public class Branch<T> {
	
	public Branch leftChild;
	public Branch rightChild;
	public boolean isLeaf;
	public T info;
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
