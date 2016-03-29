public class Function extends Node{
	FunctionSignature signature;
	FunctionBody body;
	
	public Function(Node parent) {
		super(NodeType.FUNC, parent);
		
	}

	@Override
	public void accept(PrintVisitor v) {
		v.visitFunction(this);
	}

}
