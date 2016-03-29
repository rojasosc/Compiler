public class Assignment extends Node{
	public Identifier id;
	public Expression expr; 
	public Assignment(Node parent) {
		super(NodeType.ASSIGNMENT, parent);
	}
	
	public void outputCode(){
		System.out.print(" = " + expr.toString());
	}

	public void accept(PrintVisitor v) {
		v.visitAssignment(this);
	}
}
