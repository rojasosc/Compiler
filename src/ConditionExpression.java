
public class ConditionExpression extends Node {
	Condition condition;
	ConditionExpressionTail conditionExprTail;
	public ConditionExpression(Node parent){
		super(NodeType.CONDITION_EXPR, parent);
	}
	@Override
	public void accept(PrintVisitor v) {
		v.visitConditionExpression(this);
	}
}
