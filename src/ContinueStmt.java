
public class ContinueStmt extends Node{
	public ContinueStmt(Node parent){
		super(NodeType.CONTINUE_STMT, parent);
	}

	@Override
	public void accept(PrintVisitor v) {
		v.visitContinueStmt(this);
		
	}
}
