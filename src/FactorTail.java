import java.util.LinkedList;
public class FactorTail extends Node {
	Expression expr;
	LinkedList<Expression> exprList;
	FactorTailType tailType; 
	public FactorTail(Factor parent) {
		super(NodeType.FACTOR_TAIL, parent);
	}
	@Override
	public void accept(PrintVisitor v) {
		if(nodeType == NodeType.EMPTY){
			return;
		}
		v.visitFactorTail(this);		
	}

}
