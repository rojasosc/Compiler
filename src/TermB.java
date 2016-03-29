
public class TermB extends Node {
	MultOp multOp;
	Factor factor;
	TermB termB;
	public TermB(Node parent) {
		super(NodeType.TERM_B, parent);
		
	}
	
	public TermB copy(){
		TermB copy = new TermB(parent);
		copy.multOp = multOp;
		copy.factor = factor;
		copy.termB = new TermB(copy);
		copy.termB.nodeType = NodeType.EMPTY;
		return copy;
	}
	
	public void accept(PrintVisitor v) {
		v.visitTermB(this);
		
	}

}
