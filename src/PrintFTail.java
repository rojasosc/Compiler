
public class PrintFTail extends Node {
	Expression expr;
	public PrintFTail(Node parent) {
		super(NodeType.PRINTF_FUN_TAIL, parent);
	}
	@Override
	public void accept(PrintVisitor v) {
		v.visitPrintFTail(this);
	}

}
