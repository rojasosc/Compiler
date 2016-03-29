
public class Term extends Node{
	Factor factor;
	TermB termB;
	int value;
	public Term(Node parent){
		super(NodeType.TERM, parent);
		
	}
	
	//used for new assignments
	public Term(Expression parent, Identifier id){
		super(NodeType.TERM, parent);
		factor = new Factor(this, id);
		termB = new TermB(this);
		termB.nodeType =  NodeType.EMPTY;
	}
	
	public Term copy(){
		Term copy = new Term(null);
		copy.factor = factor;
		copy.termB = new TermB(copy);
		copy.value = value;
		return copy;
	}
	
	public void accept(PrintVisitor v) {
		v.visitTerm(this);
		
	}
}
