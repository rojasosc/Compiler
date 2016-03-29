
public class PrintFCall extends Node{
	String string; 
	PrintFTail tail;
	public PrintFCall(Node parent){
		super(NodeType.PRINTF_FUN_CALL, parent);
	}
	
	public void accept(PrintVisitor v){
		v.visitPrintFCall(this);
	}
}
