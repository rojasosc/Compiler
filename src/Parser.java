/**
 * @author orojas
 * This class is my implementation of an LL(1) grammar for a 
 * small calculator. As of now, it doesn't work with spaces, but
 * that should only be a matter of removing spaces from the list of 
 * tokens. 
 *
 */
import java.util.*;
public class Parser {
	private Token currentToken;
	private ArrayList<Token> tokens;
	private int index, varCount, funCount, stmtCount;
	private Analyzer tokenizer;
	private NodeAST program;
	public Parser(String fileName){
		tokenizer = new Analyzer(fileName);
		tokenizer.startScanning();
		tokens = tokenizer.getTokens();
		prepareProgram();
		index = 0;
		varCount = 0;
		funCount = 0;
		stmtCount = 0;
		currentToken = tokens.get(0);
	}

	
	public void prepareProgram(){
		TokenType tokenType;
		TokenType nextTokenType;
		for(int i = 0; i < tokens.size() - 1; i++){
			tokenType = tokens.get(i).getType();
			nextTokenType = tokens.get(i + 1).getType();
			if(tokenType == TokenType.LEFT_PARAN){
				if(nextTokenType == TokenType.RIGHT_PARAN){
					tokens.add(i + 1, new Token("$", TokenType.EMPTY));
				}
				
			}
			else if(tokenType == TokenType.LEFT_BRACE){
				if(nextTokenType == TokenType.RIGHT_BRACE){
					tokens.add(i + 1, new Token("$", TokenType.EMPTY));
				}
			}
			else if(tokenType == TokenType.META_STATEMENT){
				tokens.remove(i);
			}
		}
		tokens.add(new Token("$$", TokenType.EPSILON));
	}
	// Grammar start symbol
	// program --> globalData funcList
	public void program(){
		program = new NodeAST(NodeType.PROGRAM);
		try {
			program.addChild(globalData());
			program.addChild(funcList());
			System.out.println("Pass");
			System.out.println("vars: " + varCount);
			System.out.println("functions: " + funCount);
			System.out.println("Statements: " + stmtCount);
			program.printAST();
			//program.outputCode();
		} catch (TokenException e) {
			System.err.println(e.getMsg());
			e.printStackTrace();
			System.out.println("Parser failed.");
		}

	}
	
	// globalData --> typeName idListB;  | funcDecl funcTail
	public NodeAST globalData() throws TokenException{
		NodeAST globalData = new NodeAST(NodeType.GLOBAL_DATA);
		switch(peak()){
		case "$$":
			match(TokenType.EPSILON, globalData);
			break;
		case "int":
		case "void":
			match(peak(), globalData);
			match(TokenType.IDENTIFIER, globalData);
			switch(peakType()){
			case COMMA:
				globalData.addChild(idListB());
				globalData.addChild(globalData());
				break;
			case LEFT_BRACKET:
				globalData.addChild(idB());
				match(TokenType.SEMI_COLON, globalData);
				varCount++;
				globalData.addChild(globalData());
				break;
			case SEMI_COLON:
				match(TokenType.SEMI_COLON, globalData);
				varCount++;
				globalData.addChild(globalData());
				break;
			case LEFT_PARAN:
				globalData.addChild(func());
				globalData.addChild(globalData());
				break;
			default:
				break;
			}
			break;
		}
		return globalData;
	}
	
	//funcList --> func funcList | $$
	public NodeAST funcList() throws TokenException{
		NodeAST funcList = new NodeAST(NodeType.FUNC_LIST);
		if(peakType() == TokenType.EPSILON){
			match(TokenType.EPSILON, funcList);
		}else{
			funcList.addChild(func());
			funcList.addChild(funcList());
		}
		return funcList;

	}
	// func --> funcDecl funcTail
	public NodeAST func() throws TokenException{
		NodeAST func = new NodeAST(NodeType.FUNC);
		func.addChild(funDecl());
		func.addChild(funcTail());
		return func;
	}
	// funcTail --> ; | { dataDecls stmts }
	public NodeAST funcTail() throws TokenException{
		NodeAST funcTail = new NodeAST(NodeType.FUNC_TAIL);
		switch(peak()){
		case ";":
			match(TokenType.SEMI_COLON, funcTail);
			funCount++;
			break;
		case "{":
			match(TokenType.LEFT_BRACE, funcTail);
			funcTail.addChild(dataDecls());
			funcTail.addChild(stmts());
			match(TokenType.RIGHT_BRACE, funcTail);
			funCount++;
			break;
		default:
			throw new TokenException("Predicted funcTail, but "+ currentToken.getStringRep() +" is not in FIRST(funcTail)");
		}
		return funcTail;
	}
	// funcDecl --> ( paramList )
	public NodeAST funDecl() throws TokenException{
		// we need the functionID
		NodeAST funcDecl = new NodeAST(NodeType.FUNC_DECL);
		match(TokenType.LEFT_PARAN, funcDecl);
		funcDecl.addChild(paramList());
		match(TokenType.RIGHT_PARAN, funcDecl);
		return funcDecl;
	}
	
	// typeName --> int | void
	public NodeAST typeName() throws TokenException{
		NodeAST typeName = new NodeAST(NodeType.TYPE_NAME);
		switch(peak()){
		case "int":
		case "void":
			match(peak(), typeName);
			break;
		default:
			throw new TokenException("Predicted typeName, but " + currentToken.getStringRep() + " is not in FIRST(typeName)");
		}
		return typeName;
	}
	// paramList --> $ | void | nonEmptyList
	public NodeAST paramList() throws TokenException{
		NodeAST paramList = new NodeAST(NodeType.PARAM_LIST);
		switch(peak()){
		case "$":
		case "void":
			match(peak(), paramList);
			break;
		default: 
			paramList.addChild(nonEmptyList());
		}
		return paramList;
	}
	
	// nonEmptyList --> typeName ID nonEmptyListB
	public NodeAST nonEmptyList() throws TokenException{
		NodeAST nonEmptyList = new NodeAST(NodeType.NON_EMPTY_LIST);
		nonEmptyList.addChild(typeName());
		match(TokenType.IDENTIFIER, nonEmptyList);
		varCount++;
		nonEmptyList.addChild(nonEmptyListB());
		return nonEmptyList;
	}
	
	// nonEmptyLIstB --> $ | COMMA typeName ID nonEmptyListB
	public NodeAST nonEmptyListB() throws TokenException{
		NodeAST nonEmptyListB = new NodeAST(NodeType.NON_EMPTY_LISTB);
		if(peakType() == TokenType.COMMA){
			match(TokenType.COMMA, nonEmptyListB);
			nonEmptyListB.addChild(typeName());
			match(TokenType.IDENTIFIER, nonEmptyListB);
			varCount++;
			nonEmptyListB.addChild(nonEmptyListB());
		}
		return nonEmptyListB;
	}
	
	
	// dataDecls --> $ | typeName idList SEMI_COLON dataDecls
	public NodeAST dataDecls() throws TokenException{
		NodeAST dataDecls = new NodeAST(NodeType.DATA_DECLS);
		switch(peak()){
		case "int":
		case "void":
			dataDecls.addChild(typeName());
			dataDecls.addChild(idList());
			match(TokenType.SEMI_COLON, dataDecls);
			varCount++;
			break;
		default:
			break;
		}
		return dataDecls;
	}
	// idList --> id idListB 
	public NodeAST idList() throws TokenException{
		NodeAST idList = new NodeAST(NodeType.ID_LIST);
		// should idListB take a var name? 
		idList.addChild(id());
		idList.addChild(idListB());
		return idList;
	}
	// idListB --> $ | , id idListB
	public NodeAST idListB() throws TokenException{
		NodeAST idListB = new NodeAST(NodeType.ID_LIST_B);
		switch(peakType()){
		case COMMA:
			match(TokenType.COMMA, idListB);
			varCount++;
			idListB.addChild(id());
			idListB.addChild(idListB());
			break;
		default:
			break;
		}
		return idListB;
	}
	
	// id --> ID idB
	public NodeAST id() throws TokenException{
		NodeAST id = new NodeAST(NodeType.ID);
		if(currentToken.type == TokenType.IDENTIFIER){
			match(TokenType.IDENTIFIER, id);
			id.addChild(idB());
			return id;
		}else{
			throw new TokenException("Predicted id, but token is not an identifier");
		}
	}
	// idB --> $ | [ expr ]
	public NodeAST idB() throws TokenException{
		NodeAST idB = new NodeAST(NodeType.ID_B);
		switch(peak()){
		case "[":
			match(TokenType.LEFT_BRACKET, idB);
			idB.addChild(expr());
			match(TokenType.RIGHT_BRACKET, idB);
				switch(peak()){
				case "=":
					idB.addChild(assignment());
				default:
					break;
				}
			break;
		default:
			break;
		}
		return idB;
	}
	// blockStmts --> { stmts } 
	public NodeAST blockStmts() throws TokenException{
		NodeAST blockStmts = new NodeAST(NodeType.BLOCK_STMTS);
		switch(peak()){
		case "{":
			match(TokenType.LEFT_BRACE, blockStmts);
			blockStmts.addChild(stmts());
			match(TokenType.RIGHT_BRACE, blockStmts);
			break;
		default:
			throw new TokenException("Predicted blockStmts, but token is not in FIRST(blockStmts)");
		}
		return blockStmts;
	}
	// stmts --> $ | stmt stmts
	public NodeAST stmts() throws TokenException{
		NodeAST stmts = new NodeAST(NodeType.STMTS);
		switch(peakType()){
		case IDENTIFIER:
			stmts.addChild(stmt());
			stmts.addChild(stmts());
		default:
			return stmts;
		}
	}
	// stmt --> ID stmtTail | genFuncall | printfFunCall | scanfFunCall | ifStmt | whileStmt | returnStmt | breakStmt | continueStmt
	public NodeAST stmt() throws TokenException{
		NodeAST stmt = new NodeAST(NodeType.STMT);
		switch(peak()){
		case "scanf":
			stmt.addChild(scanfFunCall());
			break;
		case "printf":
			stmt.addChild(printfFunCall());
			break;
		case "if":
			stmt.addChild(ifStmt());
			break;
		case "while":
			stmt.addChild(whileStmt());
			break;
		case "return":
			stmt.addChild(returnStmt());
			break;
		case "break":
			stmt.addChild(breakStmt());
			break;
		case "continue":
			stmt.addChild(continueStmt());
			break;
		default:
			if(currentToken.getType() == TokenType.IDENTIFIER){
				match(TokenType.IDENTIFIER, stmt);
				stmt.addChild(stmtTail());
			}
			break;
		}
		return stmt;
	}
	
	public NodeAST stmtTail() throws TokenException{
		NodeAST stmtTail = new NodeAST(NodeType.STMT_TAIL);
		switch(peak()){
		case "=":
			stmtTail.addChild(assignment());
			break;
		case "(":
			stmtTail.addChild(genFunCall());
			break;
		case "[":
			stmtTail.addChild(idB());
			break;
		default: 
			throw new TokenException("stmtTail parse error saw " + currentToken.toString());
		}
		return stmtTail;
	}
	
	//breakStmt --> break ;
	public NodeAST breakStmt() throws TokenException{
		NodeAST breakStmt = new NodeAST(NodeType.BREAK_STMT);
		match("break", breakStmt);
		match(TokenType.SEMI_COLON, breakStmt);
		return breakStmt;
	}
	
	// assignment --> = expr ;
	public NodeAST assignment() throws TokenException{
		NodeAST assignment = new NodeAST(NodeType.ASSIGNMENT);
		match("=", assignment);
		assignment.addChild(expr());
		match(TokenType.SEMI_COLON, assignment);
		return assignment;
	}
	// genFunCall --> ID ( exprList ) ;
	public NodeAST genFunCall() throws TokenException{
		NodeAST genFunCall = new NodeAST(NodeType.GEN_FUN_CALL);
			match(TokenType.LEFT_PARAN, genFunCall);
			genFunCall.addChild(exprList());
			match(TokenType.RIGHT_PARAN, genFunCall);
			match(TokenType.SEMI_COLON, genFunCall);
			return genFunCall;
	}
	
	// printfFunCall --> printf ( STRING printfFunTail
	public NodeAST printfFunCall() throws TokenException{
		NodeAST printfFunCall = new NodeAST(NodeType.PRINTF_FUN_CALL);
		match("printf", printfFunCall);
		match(TokenType.LEFT_PARAN, printfFunCall);
		match(TokenType.STRING, printfFunCall);
		printfFunCall.addChild(printfFunTail());
		return printfFunCall;
	}

	// printfFunTail --> ) ; | , expr ) ;
	public NodeAST printfFunTail() throws TokenException{
		NodeAST printfFunTail = new NodeAST(NodeType.PRINTF_FUN_TAIL);
		switch(peakType()){
		case RIGHT_PARAN:
			match(TokenType.RIGHT_PARAN, printfFunTail);
			match(TokenType.SEMI_COLON, printfFunTail);
			break;
		case COMMA:
			match(TokenType.COMMA, printfFunTail);
			printfFunTail.addChild(expr());
			match(TokenType.RIGHT_PARAN, printfFunTail);
			match(TokenType.SEMI_COLON, printfFunTail);
			break;
		default: 
			throw new TokenException("Predicted printfFunTail, but token is not in FIRST(printfFunTail)");
		}
		return printfFunTail;
	}
	// scanfFunCall --> ID ( STRING , & expr ) ;
	public NodeAST scanfFunCall() throws TokenException{
		NodeAST scanfFunCall = new NodeAST(NodeType.SCANF_FUN_CALL);
		match(TokenType.IDENTIFIER, scanfFunCall);
		match(TokenType.LEFT_PARAN, scanfFunCall);
		match(TokenType.STRING, scanfFunCall);
		match(TokenType.COMMA, scanfFunCall);
		match("&", scanfFunCall);
		scanfFunCall.addChild(expr());
		match(TokenType.RIGHT_PARAN, scanfFunCall);
		match(TokenType.SEMI_COLON, scanfFunCall);
		return scanfFunCall;
	}
	
	// exprList --> $ | nonEmptyExprList
	public NodeAST exprList() throws TokenException{
		NodeAST exprList = new NodeAST(NodeType.EXPR_LIST);
		switch(peak()){
		case "$":
			match("$", exprList);
			return exprList;
		default:
			exprList.addChild(nonEmptyExprList());
			return exprList;
		}
	}
	
	// nonEmptyExprList --> expr nonEmptyExprListB
	public NodeAST nonEmptyExprList() throws TokenException{
		NodeAST nonEmptyExprList = new NodeAST(NodeType.NON_EMPTY_EXPR_LIST);
		nonEmptyExprList.addChild(expr());
		nonEmptyExprList.addChild(nonEmptyExprListB());
		return nonEmptyExprList;
	}
	// nonEmptyExprListB --> $ | , expr
	public NodeAST nonEmptyExprListB() throws TokenException{
		NodeAST nonEmptyExprListB = new NodeAST(NodeType.NON_EMPTY_EXPR_LIST_B);
		switch(peak()){
		case "$":
			match("$", nonEmptyExprListB);
			return nonEmptyExprListB;
		case ",":
			match(TokenType.COMMA, nonEmptyExprListB);
			nonEmptyExprListB.addChild(expr());
			if(currentToken.getStringRep() == ","){
				nonEmptyExprListB.addChild(nonEmptyExprListB());
			}
			return nonEmptyExprListB;
		}
		return nonEmptyExprListB;
	}
	
	// ifStmt --> if ( conditionExpr ) blockStmts ifStmtB

	public NodeAST ifStmt() throws TokenException{
		NodeAST ifStmt = new NodeAST(NodeType.IF_STMT);
		match("if", ifStmt);
		match(TokenType.LEFT_PARAN, ifStmt);
		ifStmt.addChild(condExpr());
		match(TokenType.RIGHT_PARAN, ifStmt);
		ifStmt.addChild(blockStmts());
		ifStmt.addChild(ifStmtB());
		return ifStmt;
	}
	//ifStmtB --> $ | else blockStmts
	public NodeAST ifStmtB() throws TokenException{
		NodeAST ifStmtB = new NodeAST(NodeType.IF_STMT);
		switch(peak()){
		case "else":
			match("else", ifStmtB);
			ifStmtB.addChild(blockStmts());
			break;
		}
		return ifStmtB;
	}
	
	// condExpr --> condition conditionExprTail
	public NodeAST condExpr() throws TokenException{
		NodeAST condExpr = new NodeAST(NodeType.CONDITION_EXPR);
		condExpr.addChild(condition());
		condExpr.addChild(conditionExprTail());
		return condExpr;
	}
	
	//condExprTail --> $ | conditionOp condition
	public NodeAST conditionExprTail() throws TokenException{
		NodeAST conditionExprTail = new NodeAST(NodeType.CONDITION_EXPR_TAIL);
		switch(peak()){
		case "$":
			match("$", conditionExprTail);
			return conditionExprTail;
		case "&&":
			conditionExprTail.addChild(conditionOp());
			conditionExprTail.addChild(condition());
			return conditionExprTail;
		case "||":
			conditionExprTail.addChild(conditionOp());
			conditionExprTail.addChild(condition());
			return conditionExprTail;
		case "!":
			conditionExprTail.addChild(conditionOp());
			conditionExprTail.addChild(condition());
			return conditionExprTail;
		}
		return conditionExprTail;
	}
	// condition --> expr comparisionOP expr
	public NodeAST condition() throws TokenException{
		NodeAST condition = new NodeAST(NodeType.CONDITION);
		condition.addChild(expr());
		condition.addChild(comparisonOp());
		condition.addChild(expr());
		return condition;
	}
	
	
	public NodeAST comparisonOp() throws TokenException{
		NodeAST comparisonOp = new NodeAST(NodeType.COMPARISON_OP);
		switch(peak()){
		case "==":
			match("==",comparisonOp);
			break;
		case "!=":
			match("!=", comparisonOp);
			break;
		case ">":
			match(">", comparisonOp);
			break;
		case ">=":
			match(">=", comparisonOp);
			break;
		case "<":
			match("<", comparisonOp);
			break;
		case "<=":
			match("<=", comparisonOp);
			break;
		default:
			throw new TokenException("Predicted comparsionOp, but token is not in FIRST(comparisionOp)");
		}
		return comparisonOp;
	}
	
	public NodeAST conditionOp() throws TokenException{
		NodeAST conditionOp = new NodeAST(NodeType.CONDITION_OP);
		switch(peak()){
		case "&&":
			match("&&", conditionOp);
			break;
		case "||":
			match("||", conditionOp);
			break;
		case "!":
			match("!", conditionOp);
			break;
		default:
			throw new TokenException("Predicted conditionOp, but +"+ currentToken.getStringRep() + "is not in FIRST(conditionOP)");
		}
		return conditionOp;
	}
	public NodeAST whileStmt()  throws TokenException{
		NodeAST whileStmt = new NodeAST(NodeType.WHILE_STMT);
		match("while", whileStmt);
		match(TokenType.LEFT_PARAN, whileStmt);
		whileStmt.addChild(condExpr());
		match(TokenType.RIGHT_PARAN, whileStmt);
		whileStmt.addChild(blockStmts());
		return whileStmt;
	}
	// returnStmt --> return return_tail
	public NodeAST returnStmt()  throws TokenException{
		NodeAST returnStmt = new NodeAST(NodeType.RETURN_STMT);
		match("return", returnStmt);
		returnStmt.addChild(returnTail());
		return returnStmt;
	}
	//return_tail --> expr ; | ;
	public NodeAST returnTail()  throws TokenException{
		NodeAST returnTail = new NodeAST(NodeType.RETURN_STMT_TAIL);
		switch(peak()){
		case ";":
			match(TokenType.SEMI_COLON, returnTail);
			return returnTail;
		default:
			returnTail.addChild(expr());
			match(TokenType.SEMI_COLON, returnTail);
			return returnTail;
		}
	}
	
	// continueStmt --> continue ;
	public NodeAST continueStmt()  throws TokenException{
		NodeAST continueStmt = new NodeAST(NodeType.CONTINUE_STMT);
		match("continue", continueStmt);
		match(TokenType.COMMA, continueStmt);
		return continueStmt;
	}
	// expr --> term exprB
	public NodeAST expr()  throws TokenException{
		NodeAST expr = new NodeAST(NodeType.EXPRESSION);
		expr.addChild(term());
		expr.addChild(exprB());
		return expr;
	}
	// exprB --> $ | addop term exprB
	public NodeAST exprB()  throws TokenException{
		NodeAST exprB = new NodeAST(NodeType.EXPRESSION_B);
		switch(peak()){
		case "+":
		case "-":
			exprB.addChild(addOp());
			exprB.addChild(term());
			exprB.addChild(exprB());
			break;
		default:
			return exprB; 
		}
		return exprB;
	}
	
	// + | -
	public NodeAST addOp() throws TokenException{
		NodeAST addOp = new NodeAST(NodeType.ADD_OP);
		switch(peakType()){
		case PLUS:
		case MINUS:
			match(peakType(), addOp);
			break;
		default:
			throw new TokenException("Predicted addOp, but token is not in FIRST(addOp)");
		}
		return addOp;
	}
	
	// term --> factor termB
	public NodeAST term()  throws TokenException{
		NodeAST term = new NodeAST(NodeType.TERM);
		term.addChild(factor());
		term.addChild(termB());
		return term;
	}
	
	// termB --> $ | multOp factor termB
	public NodeAST termB() throws TokenException{
		NodeAST termB = new NodeAST(NodeType.TERM_B);
		switch(peak()){
		case "*":
		case "/":
			termB.addChild(multOp());
			termB.addChild(factor());
			termB.addChild(termB());
			return termB;
		}
		return termB;
	}
	
	public NodeAST multOp() throws TokenException{
		NodeAST multOp = new NodeAST(NodeType.MULT_OP);
		switch(peakType()){
		case MULT_OP:
		case DIV_OP:
			match(peakType(), multOp);
			break;
		default:
			throw new TokenException("Predicted multOp, but token is not in FIRST(addOp)");
		}	
		return multOp;
	}
	
	// factor --> ID factorB | NUMBER | - NUMBER | ( expr )
	public NodeAST factor() throws TokenException{
		NodeAST factor = new NodeAST(NodeType.FACTOR);
		switch(peakType()){
		case IDENTIFIER:
			match(TokenType.IDENTIFIER, factor);
			factor.addChild(factorB());
			break;
		case NUMBER:
			match(TokenType.NUMBER, factor);
			break;
		case MINUS:
			match(TokenType.MINUS, factor);
			match(TokenType.NUMBER, factor);
			break;
		case LEFT_PARAN:
			match(TokenType.LEFT_PARAN, factor);
			factor.addChild(expr());
			match(TokenType.RIGHT_PARAN, factor);
			break;
		default:
			throw new TokenException("Predicted factor, but " + currentToken.getStringRep()+ " is not in FIRST(factor)");
		}
		
		return factor;
	}
	// factorB --> $ | [ expr ] | ( <exprlist )
	public NodeAST factorB()  throws TokenException{
		NodeAST factorB = new NodeAST(NodeType.FACTOR_TAIL);
		switch(peakType()){
		case LEFT_BRACKET:
			match(TokenType.LEFT_BRACKET, factorB);
			factorB.addChild(expr());
			match(TokenType.RIGHT_BRACKET, factorB);
			break;
		case LEFT_PARAN:
			match(TokenType.LEFT_PARAN, factorB);
			factorB.addChild(exprList());
			match(TokenType.RIGHT_PARAN, factorB);
			break;
		default:
			return factorB;
		}
		return factorB;
	}
	
	
	public String peak(){
		return currentToken.getStringRep();
	}
	
	public TokenType peakType(){
		return currentToken.type;
	}
	
	public boolean match(TokenType type, NodeAST node) throws TokenException{
		if(currentToken.type == type){
			//System.out.println("matched " + type);
			if(type == TokenType.SEMI_COLON){
				stmtCount++;
			}
			if(type == TokenType.EPSILON){
				return true;
			}
			if(type == TokenType.IDENTIFIER){
				node.addChild(new NodeAST(NodeType.IDENTIFIER, currentToken.getStringRep()));
			}else{
				node.addChild(new NodeAST(NodeType.TERMINAL, currentToken.getStringRep()));
			}
			// add the token to the current node
			//node.addToken(currentToken.copy());
			nextToken();
			return true;
		}
		throw new TokenException("Parse Error: expected " + type + " but saw " + " --> " + currentToken.type);
	}
	
	public boolean match(String expected, NodeAST node) throws TokenException{
		String curr = currentToken.getStringRep();
		if (curr.equalsIgnoreCase(expected)){
			//System.out.println("Matched: " + expected);
			node.addToken(currentToken.copy());
			node.addChild(new NodeAST(NodeType.TERMINAL, currentToken.getStringRep()));
			nextToken();
			return true;
		}
		throw new TokenException("Parse Error: expected " + expected + "but saw " + curr);
	}

	public void printProgram(){
		for(int i = 0; i < tokens.size(); i++){
			System.out.print(tokens.get(i).getStringRep());
		}
		System.out.println();
		
	}
	
	public void nextToken(){
		this.index++;
		this.currentToken = this.tokens.get(index);
	}
   	
}