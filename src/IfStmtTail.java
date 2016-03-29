
public class IfStmtTail extends Node {
	//TODO: account for else stmts
	BlockStmts blockStmts; // only if else keyword is matched
	public IfStmtTail(Node parent) {
		super(NodeType.IF_STMT_TAIL, parent);
		
	}

	public void accept(PrintVisitor v ){
		if(nodeType == NodeType.EMPTY){
			return;
		}
		v.visitIfStmtTail(this);
	}
}
