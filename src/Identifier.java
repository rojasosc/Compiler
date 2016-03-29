
public class Identifier extends Node{
	String string;
	IdentifierB idB; // could be an array
	IDType idType; // var, array, function
	DataType dataType;
	public Identifier(Node parent) {
		super(NodeType.IDENTIFIER, parent);
	}
	
	public Identifier(Node parent, String string, DataType dataType){
		super(NodeType.IDENTIFIER, parent);
		this.dataType = dataType;
		this.string = string;
	}

	@Override
	public void accept(PrintVisitor v) {
		v.visitIdentifier(this);		
	}
	
	
}
