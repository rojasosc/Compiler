
public class BreakStmt extends Node{
	public BreakStmt(Node parent){
		super(NodeType.BREAK_STMT, parent);
	}

	
	public void outputCode() {
		System.out.print("break ;");
		
	}


	@Override
	public void accept(PrintVisitor v) {
		v.visitBreakStmt(this);
		
	}
}
