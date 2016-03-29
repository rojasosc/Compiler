
public class IfStmt extends Node{
	ConditionExpression condExpr;
	BlockStmts blockStmts;
	IfStmtTail ifStmtTail;
	public IfStmt(Node parent) {
		super(NodeType.IF_STMT, parent);
		
	}
	
	public void accept(PrintVisitor v){
		v.visitIfStmt(this);
	}

}
