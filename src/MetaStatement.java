
public class MetaStatement extends Node{
	public String string; 
	public MetaStatement(NodeType type, Node parent, String string) {
		super(type, parent);
		this.string = string + "\n";
	}

	@Override
	public void accept(PrintVisitor v) {
		v.visitMetaStatement(this);
		
	}

}
