
public class Condition extends Node {
	Expression leftExpr; 
	RelationalOperator relOp;
	Expression rightExpr;
	public Condition(Node parent) {
		super(NodeType.CONDITION, parent);
	
	}
	public void outputCode() {
		System.out.print(relOp);
		
	}
	@Override
	public void accept(PrintVisitor v) {
		v.visitCondition(this);
		
	}

}
