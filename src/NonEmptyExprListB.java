
public class NonEmptyExprListB extends Node {
	Expression expr;
	public NonEmptyExprListB(Node parent) {
		super(NodeType.NON_EMPTY_EXPR_LIST_B, parent);
		
	}
	
	public void accept(PrintVisitor v ){
		v.visitNonEmptyExprListB(this);
	}

}
