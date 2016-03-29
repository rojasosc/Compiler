import java.util.LinkedList;

public class Stmts extends Node{
	LinkedList<Stmt> stmtList;
	public Stmts(Node parent){
		super(NodeType.STMTS, parent);
		stmtList = new LinkedList<Stmt>();
	}

	@Override
	public void accept(PrintVisitor v) {
		v.visitStmts(this);
		
	}
	
}
