import java.util.LinkedList;


public class GlobalVariable extends Node{
	DataType dataType;
	LinkedList<Identifier> idList;
	public GlobalVariable(Program parent){
		super(NodeType.GLOBAL_DATA, parent);
	}

	@Override
	public void accept(PrintVisitor v) {
		v.visitGlobalVariable(this);
	}
	
}
