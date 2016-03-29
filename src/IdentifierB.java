
public class IdentifierB extends Node {
	Expression expr;
	Assignment assignment;
	boolean isEmpty;
	
	public IdentifierB(Identifier parent){
		super(NodeType.ID_B, parent);
		
	}
	public void accept(PrintVisitor v){
		return;
	}

}
