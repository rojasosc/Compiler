import java.util.LinkedList;


public class NonEmptyExprList extends Node {
	LinkedList<Expression> exprList;
	public NonEmptyExprList(Node parent) {
		super(NodeType.NON_EMPTY_EXPR_LIST, parent);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void accept(PrintVisitor v) {
		v.visitNonEmptyExprList(this);
		
	}

	
}
