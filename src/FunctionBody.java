import java.util.HashMap;
import java.util.LinkedList;
public class FunctionBody extends Node {
	LinkedList<DataDecl> dataDecls;
	LinkedList<Param> params;
	HashMap<String, String> map;
	Stmts stmts;
	
	public FunctionBody(Function parent) {
		super(NodeType.FUNC_TAIL, parent);
		
	}
	
	public void accept(PrintVisitor v) {
		v.visitFunctionBody(this);
	}

}
