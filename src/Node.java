import java.util.LinkedList;
public abstract class Node {
	NodeType nodeType;
	Node parent;
	LinkedList<Node> children;
	public Node(NodeType type, Node parent){
		this.nodeType = type;
		this.parent = parent;
		children = new LinkedList<Node>();
		
	}
	
	public void push(Node child){
		children.add(child);
	}
	
	// general purpose print method
	public void printNode() {
		System.out.println("Node Type: " + nodeType);
		System.out.println("Parent" + parent);
		System.out.print("Children: -->");
		for(Node child: children){
			System.out.print(" " + child.nodeType);
		}
	}
	
	public abstract void accept(PrintVisitor v);
}
