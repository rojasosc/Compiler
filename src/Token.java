/**
 * @author orojas
 * CSC-254
 * Compiler: Lexical Analyzer
 * 
 * This class represents a token. 
 */
public class Token {
	private String  stringRep; 	// string representation of the token
	TokenType type; 	// type of token
	private boolean valid;
	public boolean isValid() {
		return valid;
	}
	public Token(String token, TokenType type){
		this.stringRep = token;
		this.type = type;
		this.valid = true;
	}
	public Token(boolean valid){
		this.valid = false;
	}
	public String getToken(){
		return this.stringRep;
	}
	public String getStringRep() {
		return stringRep;
	}
	public String toString(){
		return this.stringRep;
	}
	public void setStringRep(String stringRep) {
		this.stringRep = stringRep;
	}
	public TokenType getType() {
		return type;
	}
	public void setType(TokenType type) {
		this.type = type;
	}
	
	public Token copy(){
		return new Token(stringRep, type);
	}
	
	public boolean compareTo(String str){
		if(this.stringRep.equalsIgnoreCase(str)){
			return true;
		}else{
			return false;
		}
	}
	
}
