
public class Factor extends Node {
	FactorType factorType;
	Identifier identifier;
	FactorTail factorTail;
	int value;
	Expression expr; // ( expr ) types
	public Factor(Term parent) {
		super(NodeType.FACTOR, parent);
	}
	
	public Factor(TermB parent) {
		super(NodeType.FACTOR, parent);
	}
	
	public Factor(Term parent, Identifier id){
		super(NodeType.FACTOR, parent);
		identifier = id;
		factorType = FactorType.ID;
		factorTail = new FactorTail(this);
		factorTail.nodeType = NodeType.EMPTY;
		
	}
	
	@Override
	public void accept(PrintVisitor v) {
		v.visitFactor(this);
		
	}

}
