import java.util.ArrayList;
import java.util.LinkedList;

// This class represents a node in the AST 
// created by the parser. 
public class NodeAST {
	private NodeType type;
	private ArrayList<Token> tokens;
	private LinkedList<NodeAST> children;
	private String data; // variables , functions , assignments, terminal values

	public NodeAST(NodeType type){
		this.type = type;
		tokens = new ArrayList<Token>();
		children = new LinkedList<NodeAST>();
		
	}
	
	public NodeAST(NodeType type, String data){
		this.type = type;
		tokens = new ArrayList<Token>();
		children = new LinkedList<NodeAST>();
		this.data = data;
	}
	
	public NodeType getType(){
		return type;
		
	}
	
	public void printAST(){
		if(type != NodeType.TERMINAL){
			System.out.printf("<" + type +"> --> ");
		}
		for(NodeAST child: children){
			if(child.type == NodeType.TERMINAL){
				System.out.print(" " + child.data);
			}else{
				System.out.printf(" " + child.type);
			}
		}
		System.out.println();
		for(NodeAST child: children){
			child.printAST();
		}
	}
	
	public void outputCode(){
		//NOW ITS EASY
		if(type == NodeType.STMT){
			//check children for id followed by assignment
			for(NodeAST child: children){
				if(child.type == NodeType.IDENTIFIER){
					int index = children.indexOf(child);
					NodeAST nextChild = children.get(index + 1);
					if(nextChild.type == NodeType.STMT_TAIL){
						if(nextChild.children.peekFirst().type == NodeType.ASSIGNMENT){
							//replace expression with <operand>
							//evaluate expression, returns <operand>
							//print generated code here. 
							System.out.println("generated code..");
						}
					}
				}
				
			}
		}
		if(type == NodeType.TERMINAL || type == NodeType.IDENTIFIER){
			System.out.print(data);
		}
		for(NodeAST child: children){
			child.outputCode();
		}
	}
	
	
	public ArrayList<Token> getTokens(){
		return tokens; 
	}
	
	public void addToken(Token token){
		tokens.add(token);
	}
	
	// how will we access children ?? 
	
	public boolean addChild(NodeAST child){
		children.add(child);
		return true;
	}
	
}
