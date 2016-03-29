import java.util.HashMap;
import java.util.LinkedList;

public class Program extends Node{
	HashMap<String, String> map = new HashMap<String, String>();
	LinkedList<GlobalVariable> globalVars;
	LinkedList<Function> functions;
	LinkedList<MetaStatement> metaStatements;
	public Program(){
		super(NodeType.PROGRAM, null);
		
	}
	
	public void accept(PrintVisitor v){
		v.visitProgram(this);
	}
}
