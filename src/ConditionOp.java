
public class ConditionOp extends Node {
	//this class might be obsolete.
	ConditionOperator operator; 
	public ConditionOp(Node parent) {
		super(NodeType.CONDITION_OP, parent);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void accept(PrintVisitor v) {
		
		
	}

}
