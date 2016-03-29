import java.util.LinkedList;
public class FunctionSignature extends Node{
	Identifier id;
	LinkedList<Param> paramList;
	public FunctionSignature(Function parent){
		super(NodeType.FUNC_DECL, parent);
		
	}
	@Override
	public void accept(PrintVisitor v) {
		v.visitFunctionSignature(this);
		
	}
}
