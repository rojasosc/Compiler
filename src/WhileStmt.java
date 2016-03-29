
public class WhileStmt extends Node{
	ConditionExpression conditionExpr;
	BlockStmts blockStmts;
	public WhileStmt(Node parent) {
		super(NodeType.WHILE_STMT, parent);
	}

	
	public void accept(PrintVisitor v) {
		v.visitWhileStmts(this);
		
	}

}
