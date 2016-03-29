
public class ReturnStmt extends Node{
	ReturnStmtTail returnStmtTail;
	public ReturnStmt(Node parent){
		super(NodeType.RETURN_STMT, parent);
	}
	@Override
	public void accept(PrintVisitor v) {
		v.visitReturnStmt(this);
	}

}
