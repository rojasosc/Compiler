import java.util.LinkedList;
public class ExprList extends Node {
	LinkedList<Expression> exprList;
	public ExprList(Node parent) {
		super(NodeType.EXPR_LIST, parent);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void accept(PrintVisitor v) {
		v.visitExprList(this);
		
	}

}
