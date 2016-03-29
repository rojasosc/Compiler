public class BlockStmts extends Node {
	public Stmts stmts;
	public BlockStmts(Node parent) {
		super(NodeType.BLOCK_STMTS, parent);
	}
	
	public void accept(PrintVisitor v) {
		v.visitBlockStmts(this);
		
	}
}
