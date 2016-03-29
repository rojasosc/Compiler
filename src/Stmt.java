
public class Stmt extends Node{
	// The type of this Stmt_Node is used to 
	// denote the structure of this subtree.
	// e.g., if, while, funCall, assignment, dataDecl
	StmtType stmtType;
	Identifier identifier;
	PrintFCall printCall;
	ScanFCall scanCall;
	IfStmt ifStmt; 
	WhileStmt whileStmt;
	BreakStmt breakStmt;
	ContinueStmt continueStmt;
	StmtTail tail;
	ReturnStmt returnStmt;
	public Stmt(Node parent){
		super(NodeType.STMT,parent);
	}
	@Override
	public void accept(PrintVisitor v) {
		v.visitStmt(this);
		
	}
	
}
