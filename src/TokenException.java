/**
 * @author orojas
 * CSC-254
 * Compiler: Lexical Analyzer
 * 
 * This class is used to throw errors and is used 
 * to output the illegal token.  
 */
public class TokenException extends Exception{
	private TokenError error;
	private String msg;
	public TokenException(TokenError error){
		super(error.getErrorMessage());
		this.error = error;
		
	}
	
	public TokenException(String msg){
		this.msg = msg; 
	}
	
	public TokenError getError(){
		return this.error;
	}
	
	public String getMsg(){
		return this.msg;
	}
	
	public void printError(){
		System.out.println(error.getErrorMessage() + " " + error.getToken().getStringRep());
	}

}
