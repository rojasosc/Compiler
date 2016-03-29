import java.util.LinkedList;

public class DataDecl extends Node{
	DataType dataType;
	LinkedList<Identifier> idList;
	public DataDecl(Node parent) {
		super(NodeType.DATA_DECLS, parent);
	}
	@Override
	public void accept(PrintVisitor v) {
		v.visitDataDecl(this);
	}
}
