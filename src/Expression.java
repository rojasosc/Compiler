public class Expression extends Node{
	Term term; 
	ExpressionB exprB;
	int value; //a factor is either a var or num or funCall
	boolean isConstantExpr;
	public Expression(Node parent) {
		super(NodeType.EXPRESSION, parent);
	}
	@Override
	public void accept(PrintVisitor v) {
		v.visitExpression(this);
		
	}

	public Expression copy(){
		Expression copy = new Expression(null);
		copy.term = term;
		copy.exprB = new ExpressionB(copy);
		copy.value = value;
		return copy;
	}
}
