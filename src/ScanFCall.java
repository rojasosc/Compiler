
public class ScanFCall extends Node{
	String string;
	Expression expr;
	public ScanFCall(Node parent){
		super(NodeType.SCANF_FUN_CALL,parent);
	}
	@Override
	public void accept(PrintVisitor v) {
		v.visitScanFCall(this);		
	}
	
}
