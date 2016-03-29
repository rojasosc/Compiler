/**
 * @author orojas
 * CSC-254
 * Compiler: Lexical Analyzer
 * 
 * This class represents the analyzer. 
 * In essence, it is the DFA, but with I/O utilities. 
 */
import java.io.*;
import java.util.*;

/**
 * @author orojas
 *
 */
public class Analyzer {
	
	//SCANNER TOOLS
	private Token token; 		// current token
	private Character c; 		// current char
	private String strToken;	// current token string
	private String output;		// output string
	private int index; 			// current char index
	private char lineCharArray[];
	
	//I/O 
	private BufferedReader buffReader;
	private String filePath;
	
	//SETS
	private String keywords[] = {"int", "void", "if", "else", "while", "return", "continue", "break", "scanf", "printf", "main"};
	private String conditionOps[] = {"&&", "||", "!"};
	private char formatIdentifiers[] = {'d', 'i', 'o','x','X', 'u','c','s','f','e','E','g','G','p','n','%'};
	private char symbols[] = {'(', ')', '{', '}', '[', ']', ',', ';', '+', '-', '*', '/'};
	private ArrayList<Token> tokens;
	
	//STATUS FLAGS
	private boolean validProgram;
	
	//Used for testing
	//TODO: implement a constructor to initialize inputFile object
	public Analyzer(String filePath){
		this.filePath = filePath;
		try {
			buffReader = new BufferedReader(
				    new InputStreamReader(
				        new FileInputStream(filePath),
				        "UTF-8"));
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println(filePath + " not found!");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Token getToken(){
		return token;
	}
	
	public ArrayList<Token> getTokens(){
		return tokens;
	}
	
	private void createOutput(){
		if(validProgram){
			output = "";
			Token currentToken;
			for(int i = 0; i < tokens.size(); i++){
				currentToken = tokens.get(i);
				output += currentToken.getStringRep();
			}
		}
	}
	
	public void printOutput(){
		createOutput();
		if(output != null){
			System.out.println(output);
		}
	}
	
	public boolean isTokenKeyword(){
		for(int i = 0; i < keywords.length; i++){
			if(keywords[i].equals(strToken)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isEqualityPrefix(){
		char nextChar = peekChar();
		if(nextChar == '='){
			return true;
		}
		return false;
	}
	public boolean isFormatingIdentifier(){
		char nextChar = peekChar();
		for(int i = 0; i < formatIdentifiers.length; i++){
			if(nextChar == formatIdentifiers[i]){
				return true;
			}
		}
		return false;
	}
	
	public void addCurrentToken(){
		strToken += c;
	}
	
	
	
	/**
	 * Purpose: peeks one character ahead 
	 * @return next character in buffer or '.' if 
	 * an error occurs 
	 */
	public char peekChar(){
		char a = '.';
		try {
			buffReader.mark(1);
			try {
				a = (char) buffReader.read();
				buffReader.reset();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
		
	}
	
	public char getC(){
		return c;
	}
	
	public void updateCurrentToken(){
		try {
			c = (char) buffReader.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isIndexValid(){
		return index < lineCharArray.length;
	}
	public void currentTokenToSymbol(){
		token = new Token(c.toString(), TokenType.SYMBOL);
		updateCurrentToken();
	}
	
	public void whiteSpaceToken(){
		token = new Token(c.toString(), TokenType.WHITESPACE);
		updateCurrentToken();
	}
	
	public boolean isCurrentCharSymbol(){
		for(int i = 0; i < symbols.length; i++){
			if(symbols[i] == c){
				return true;
			}
		}
		return false;
	}
	
	public void startScanning(){
		try {
			scan();
		} catch (Exception e) {
			if(e instanceof TokenException){
				((TokenException) e).printError();
			}
			else{
				e.printStackTrace();
			}
		}
	}
	
	private void scan() throws TokenException, IOException{
		validProgram = false;
		tokens = new ArrayList<Token>();
		updateCurrentToken();
		
		while(buffReader.ready()){
			strToken = "";
			//	Begin DFA
			//	Ignore all white space
			if(Character.isWhitespace(c)){
				updateCurrentToken();
				continue;
			}
			else if(c == '/'){
				addCurrentToken();
				updateCurrentToken();
				//Verify that the next character is also '/'
				if(c == '/'){
					addCurrentToken();
					updateCurrentToken();
					//advance the character pointer until '\n'
					String commentLine = buffReader.readLine();
					strToken += commentLine;				
					token = new Token(strToken, TokenType.META_STATEMENT);
					tokens.add(token);
					updateCurrentToken();
					continue;
				}
				else{
					token = new Token(strToken, TokenType.DIV_OP);	
				}
			
			}
			else if(c == '\n'){
				addCurrentToken();
				updateCurrentToken();
				token = new Token(strToken, TokenType.EMPTY);
			}
			else if(c == '#'){
				addCurrentToken();
				//updateCurrentToken();
				//advance the character pointer until '\n'
				String commentLine = buffReader.readLine();
				strToken += commentLine;				
				token = new Token(strToken, TokenType.META_STATEMENT);
				tokens.add(token);
				updateCurrentToken();
				continue;

			}
			else if(c == '('){
				token = new Token(c.toString(), TokenType.LEFT_PARAN);
				updateCurrentToken();
			}
			else if(c == ')'){
				token = new Token(c.toString(), TokenType.RIGHT_PARAN);
				updateCurrentToken();
			}
			else if(c == '{'){
				token = new Token(c.toString(), TokenType.LEFT_BRACE);
				updateCurrentToken();
			}
			else if(c == '}'){
				token = new Token(c.toString(), TokenType.RIGHT_BRACE);
				updateCurrentToken();
			}
			else if(c == '['){
				token = new Token(c.toString(), TokenType.LEFT_BRACKET);
				updateCurrentToken();
			}
			else if(c == ']'){
				token = new Token(c.toString(), TokenType.RIGHT_BRACKET);
				updateCurrentToken();
			}
			else if(c == ','){	
				token = new Token(c.toString(), TokenType.COMMA);
				updateCurrentToken();
			}			
			else if(c == ';'){
				token = new Token(c.toString(), TokenType.SEMI_COLON);
				updateCurrentToken();	
			}
			// ARITHMETIC OPERATIONS
			else if(c == '-'){
				token = new Token(c.toString(), TokenType.MINUS);
				updateCurrentToken();
			}
			else if(c == '+'){
				token = new Token(c.toString(), TokenType.PLUS);
				updateCurrentToken();
			}
			else if(c == '*'){	
				token = new Token(c.toString(), TokenType.MULT_OP);
				updateCurrentToken();
			}
			// EQUALITY/BINARY OPERATORS
			// != > >= < <= =
			else if(c == '!' || c == '>' || c == '<' || c == '='){
				//Check if this is a prefix
				addCurrentToken();
				updateCurrentToken();
				if(c == '='){
					addCurrentToken();
					updateCurrentToken();
					token = new Token(strToken.toString(), TokenType.COMPARSION_OP);
				}
				else {
					token = new Token(strToken.toString(), TokenType.COMPARSION_OP);
				}
			}
			else if(c == '&'){
				addCurrentToken();
				updateCurrentToken();
				if(c == '&'){
					addCurrentToken();
					updateCurrentToken();
					token = new Token(strToken.toString(), TokenType.CONDITION_OP);
				}else{
					token = new Token(strToken.toString(), TokenType.CONDITION_OP);
				}
			}
			else if(c == '|'){
				addCurrentToken();
				updateCurrentToken();
				if(c == '|'){
					addCurrentToken();
					updateCurrentToken();
					token = new Token(strToken.toString(), TokenType.CONDITION_OP);
				}else{
					token = new Token(strToken.toString(), TokenType.CONDITION_OP);
				}
			}			
			else if(c == '%'){
				//TODO: do we need to verify that this is a valid format identifier?
				//if not, do we return the next char as a symbol/identifier ? 
				addCurrentToken();
				if(isFormatingIdentifier()){
					updateCurrentToken();
					addCurrentToken();
					token = new Token(strToken, TokenType.SYMBOL);
				}
				updateCurrentToken();
			}
			else if(c == '"'){
				addCurrentToken();
				updateCurrentToken();				
				while(c != '"'){
					addCurrentToken();
					updateCurrentToken();
				}
				addCurrentToken();
				updateCurrentToken();
				token = new Token(strToken, TokenType.STRING);
			}
			else if(Character.isLetter(c) || c == '_'){
				addCurrentToken();
				updateCurrentToken();				
				while((Character.isLetter(c) || Character.isDigit(c) || c == '_') && !isCurrentCharSymbol()){
					addCurrentToken();
					updateCurrentToken();
				}
				//Is this a keyword? 
				if(isTokenKeyword()){
					token = new Token(strToken, TokenType.IDENTIFIER);
				}
				else if(c == '('){
					token = new Token(strToken, TokenType.IDENTIFIER); // function name
				}
				else{
					token = new Token(strToken + "_cs254", TokenType.IDENTIFIER);
				}
				
			}
			else if(Character.isDigit(c)){
				//TODO: do we need to check for real numbers?
				addCurrentToken();
				updateCurrentToken();	
				while(!isCurrentCharSymbol() && !Character.isWhitespace(c)){
					addCurrentToken();
					if(!Character.isDigit(c)){
 						throw new TokenException(new TokenError(new Token(strToken, TokenType.NUMBER), "Illegal token"));
					}					
					updateCurrentToken();	
				}
				token = new Token(strToken, TokenType.NUMBER);			
			}else{
				//Unrecognized token
				throw new TokenException(new TokenError(new Token(strToken, TokenType.SYMBOL), "Unrecognized Illegal token"));
			}
			//System.out.println("pushing token " + token.getStringRep());
			tokens.add(token);
		}
		validProgram = true;
		//System.out.println("done");
	}
	

}
