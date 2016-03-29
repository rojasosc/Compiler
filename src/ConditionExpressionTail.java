
public class ConditionExpressionTail extends Node {
	ConditionOperator conditionOp;
	Condition condition;
	ConditionExpressionTail tail;
	public ConditionExpressionTail(Node parent) {
		super(NodeType.CONDITION_EXPR_TAIL, parent);
	}

	@Override
	public void accept(PrintVisitor v) {
		if(nodeType == NodeType.EMPTY){
			return;
		}
		v.visitConditionExpressionTail(this);
	}

}
