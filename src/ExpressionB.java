
public class ExpressionB extends Node{
	AddOpType addOpType;
	Term term;
	ExpressionB exprB;
	int value;
	public ExpressionB(Node parent){
		super(NodeType.EXPRESSION_B, parent);

	}

	@Override
	public void accept(PrintVisitor v) {
		v.visitExpressionB(this);
		
	}
	
	public ExpressionB copy(){
		ExpressionB copy = new ExpressionB(parent);
		copy.addOpType = addOpType;
		copy.term = term;
		copy.exprB = new ExpressionB(copy);
		copy.exprB.nodeType = NodeType.EMPTY;
		copy.value = value;
		return copy;
	}
	
}
