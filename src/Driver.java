
/**
 * @author orojas
 * This driver is used to test the parser.
 */
public class Driver {
	public static void main(String[] arg){
		String fileName = arg[0];
		ParserAST parser = new ParserAST(fileName);
		
		parser.program();
		PrintVisitor visitor = new PrintVisitor(parser.program);
		visitor.printProgram();
	}

}