
public class StmtTail extends Node {
	Assignment assignment;
	GenFunCall funCall;
	IdentifierB idB;
	public StmtTail(Stmt parent) {
		super(NodeType.STMT_TAIL, parent);
	}

	public void accept(PrintVisitor v) {
		v.visitStmtTail(this);
		
	}
}
