/**
 * @author orojas
 * CSC-254
 * Compiler: Lexical Analyzer
 * 
 * This class represents a token error.
 */
public class TokenError extends Throwable{
	private Token token;
	private String errorMessage; 
	public TokenError(Token token, String errorMessage){
		this.token = token; 
		this.errorMessage = errorMessage;
	}
	public Token getToken() {
		return this.token;
	}
	public String getErrorMessage() {
		return this.errorMessage;
	}
}
