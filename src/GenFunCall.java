import java.util.LinkedList;
public class GenFunCall extends Node{
	Identifier functionId; 
	LinkedList<Expression> exprList;
	public GenFunCall(Node parent) {
		super(NodeType.GEN_FUN_CALL, parent);
		// TODO Auto-generated constructor stub
	}
	public void accept(PrintVisitor v){
		v.visitGenFunCall(this);
	}
}
