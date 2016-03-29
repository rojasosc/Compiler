
public class ReturnStmtTail extends Node{
	
	Expression expr;
	public ReturnStmtTail(Node parent){
		super(NodeType.RETURN_STMT_TAIL, parent);
		
	}
	@Override
	public void accept(PrintVisitor v) {
		v.visitReturnStmtTail(this);
	}
}
