/**
 * @author orojas
 * This class is my implementation of an LL(1) grammar for a 
 * small calculator. As of now, it doesn't work with spaces, but
 * that should only be a matter of removing spaces from the list of 
 * tokens. 
 *
 */
import java.util.*;
public class ParserAST {
	private Token currentToken;
	private ArrayList<Token> tokens;
	private int index, varCount, funCount, stmtCount;
	private Analyzer tokenizer;
	Program program;
	public ParserAST(String fileName){
		tokenizer = new Analyzer(fileName);
		tokenizer.startScanning();
		tokens = tokenizer.getTokens();
		program = new Program();
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
		program.metaStatements = new LinkedList<MetaStatement>();
		for(int i = 0; i < tokens.size(); i++){
			if(tokens.get(i).type == TokenType.META_STATEMENT){
				program.metaStatements.add(new MetaStatement(NodeType.META_STATEMENT, program, tokens.get(i).getStringRep()));
				tokens.remove(i);
				i--;
			}
		}
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
		}
		tokens.add(new Token("$$", TokenType.EPSILON));
	}
	// Grammar start symbol
	// program --> globalData funcList
	public void program(){
		program.globalVars = new LinkedList<GlobalVariable>();
		program.functions = new LinkedList<Function>();
		try {
			parse();
			funcList();
			//System.out.println("Pass");
			//System.out.println("vars: " + varCount);
			//System.out.println("functions: " + funCount);
			//System.out.println("Statements: " + stmtCount);		
		} catch (TokenException e) {
			System.err.println(e.getMsg());
			e.printStackTrace();
			System.out.println("Parser failed.");
		}

	}
	
	// globalData --> typeName idListB;  | funcDecl funcTail
	public void parse() throws TokenException{
		GlobalVariable gVar = new GlobalVariable(program);
		gVar.idList = new LinkedList<Identifier>();
		switch(peak()){
		case "$$":
			match(TokenType.EPSILON);
			break;
		default:
			DataType type = typeName();
			gVar.dataType = type;
			Identifier identifier = new Identifier(program);
			identifier.idB = new IdentifierB(identifier);
			if(peakType() == TokenType.IDENTIFIER){
				identifier.string = currentToken.getStringRep();
				
			}
			match(TokenType.IDENTIFIER);
			switch(peakType()){
			case COMMA:
				//id list declaration
				gVar.idList.add(identifier);
				idListB(gVar.idList);
				//gVar.id.parent = gVar;
				program.globalVars.add(gVar);
				match(TokenType.SEMI_COLON);
				parse();
				break;
			case LEFT_BRACKET:
				identifier.parent = gVar;
				gVar.idList.add(identifier);
				identifier.idB = idB(identifier);
				match(TokenType.SEMI_COLON);
				varCount++;
				parse();
				program.globalVars.add(gVar);
				break;
			case SEMI_COLON:
				//single var declaration
				gVar.idList.add(identifier);
				match(TokenType.SEMI_COLON);
				gVar.idList.add(identifier);
				program.globalVars.add(gVar);
				varCount++;
				parse();
				break;
			case LEFT_PARAN:
				Function function = new Function(program);
				program.functions.add(function);
				func(function, identifier);
				break;
			default:
				break;
			}
			break;
		}
	}
	//funcList --> func funcList
	public void funcList() throws TokenException{
		if(peakType() == TokenType.EPSILON){
			match(TokenType.EPSILON);
		}else{
			DataType dataType = typeName();
			if(peakType() == TokenType.IDENTIFIER){
				Identifier identifier = new Identifier(null, currentToken.getStringRep(), dataType);
				Function function = new Function(program);
				program.functions.add(function);
				match(TokenType.IDENTIFIER);
				func(function, identifier);
				funcList();
			}
		}

	}
	// func --> funcDecl funcTail
	public void func(Function function, Identifier functionId) throws TokenException{
		processFunctionName(functionId);
		function.signature = new FunctionSignature(function);
		function.signature.id = functionId;
		function.signature.id.parent = function.signature;
		function.body = new FunctionBody(function);
		funDecl(function.signature);
		function.body.params = function.signature.paramList;
		funcTail(function.body);
	}
	
	public void processFunctionName(Identifier functionId){
		switch(functionId.string){
		case "printf":
		case "scanf":
		case "read":
		case "write":
			break;
			default:
				functionId.string = functionId.string;
		}
	}
	
	// funcDecl --> ( paramList )
	public void funDecl(FunctionSignature signature) throws TokenException{
		signature.paramList = new LinkedList<Param>();
		match(TokenType.LEFT_PARAN);
		paramList(signature.paramList);
		match(TokenType.RIGHT_PARAN);
	}
	
	// funcTail --> ; | { dataDecls stmts }
	public void funcTail(FunctionBody body) throws TokenException{
		switch(peak()){
		case ";":
			match(TokenType.SEMI_COLON);
			funCount++;
			body.nodeType = NodeType.EMPTY;
			break;
		case "{":
			match(TokenType.LEFT_BRACE);
			body.dataDecls = new LinkedList<DataDecl>();
			body.stmts = new Stmts(body);
			body.stmts.stmtList = new LinkedList<Stmt>();
			dataDecls(body.dataDecls, body);
			stmts(body.stmts, body.stmts.stmtList);
			match(TokenType.RIGHT_BRACE);
			funCount++;
			break;
		default:
			throw new TokenException("Predicted funcTail, but "+ currentToken.getStringRep() +" is not in FIRST(funcTail)");
		}
	}

	
	// typeName --> int | void
	public DataType typeName() throws TokenException {
		switch(peak()){
		case "int":
			match("int");
			return DataType.INT;
		case "void":
			match("void");
			return DataType.VOID;
		default:
			throw new TokenException("Predicted typeName, but " + currentToken.getStringRep() + " is not in FIRST(typeName) type: ");
		}
	}
	// paramList --> $ | void | nonEmptyList
	public void paramList(LinkedList<Param> paramList) throws TokenException{
		switch(peak()){
		case "$":
		case "void":
			match(peak());
			break;
		default: 
			nonEmptyList(paramList);
		}
	}
	
	// nonEmptyList --> typeName ID nonEmptyListB
	public void nonEmptyList(LinkedList<Param> paramList) throws TokenException{
		Param param = new Param();
		param.typeName = typeName();
		param.paramName = currentToken.getStringRep();
		match(TokenType.IDENTIFIER);
		varCount++;
		paramList.add(param);
		nonEmptyListB(paramList);
	}
	
	// nonEmptyLIstB --> $ | COMMA typeName ID nonEmptyListB
	public void nonEmptyListB(LinkedList<Param> paramList) throws TokenException{
		Param param = new Param();
		if(peakType() == TokenType.COMMA){
			match(TokenType.COMMA);
			param.typeName = typeName();
			param.paramName = currentToken.getStringRep();
			paramList.add(param);
			match(TokenType.IDENTIFIER);
			varCount++;
			nonEmptyListB(paramList);
		}
		
	}
	
	
	// dataDecls --> $ | typeName idList SEMI_COLON dataDecls
	public DataDecl dataDecls(LinkedList<DataDecl> dataDecl, FunctionBody body) throws TokenException{
		DataDecl data = new DataDecl(body);
		switch(peak()){
		case "int":
		case "void":
			data.dataType = typeName();
			data.idList = new LinkedList<Identifier>();
			idList(data.idList);
			match(TokenType.SEMI_COLON);
			dataDecl.add(data);
			varCount++;
			return data;
		default:
			return data;
		}
	}
	// idList --> id idListB 
	public void idList(LinkedList<Identifier> list) throws TokenException{
		list.add(id(null));
		idListB(list);
		return;
	}
	// idListB --> ; | , id idListB
	public void idListB(LinkedList<Identifier> list) throws TokenException{
		switch(peakType()){
		case COMMA:
			match(TokenType.COMMA);
			varCount++;
			list.add(id(null));
			idListB(list);
			break;
		default:
			break;
		}
	}
	
	// id --> ID idB
	public Identifier id(Node parent) throws TokenException{
		Identifier identifier = new Identifier(parent);
		if(currentToken.type == TokenType.IDENTIFIER){
			identifier.string = currentToken.getStringRep();
			match(TokenType.IDENTIFIER);
			identifier.idB = idB(identifier);
			return identifier;
		}else{
			throw new TokenException("Predicted id, but token is not an identifier");
		}
	}
	// idB --> $ | [ expr ]
	public IdentifierB idB(Identifier id) throws TokenException{
		IdentifierB idB = new IdentifierB(id);
		id.idB = idB;
		switch(peak()){
		case "[":
			match(TokenType.LEFT_BRACKET);
			idB.expr = new Expression(idB);
			expr(idB.expr);
			match(TokenType.RIGHT_BRACKET);
			return idB;
		default:
			return idB;
		}
	}
	// blockStmts --> { stmts } 
	public void blockStmts(BlockStmts bStmts) throws TokenException{
		bStmts.stmts = new Stmts(bStmts);
		bStmts.stmts.stmtList = new LinkedList<Stmt>();
		switch(peak()){
		case "{":
			match(TokenType.LEFT_BRACE);
			stmts(bStmts.stmts, bStmts.stmts.stmtList);
			match(TokenType.RIGHT_BRACE);
			break;
		default:
			throw new TokenException("Predicted blockStmts, but token is not in FIRST(blockStmts)");
		}
	}
	// stmts --> $ | stmt stmts
	public void stmts(Node parent, LinkedList<Stmt> list) throws TokenException{
		switch(peakType()){
		case IDENTIFIER:
			list.add(stmt(parent));
			stmts(parent, list);
			break;
		default:
			break;
		}
	}
	// stmt --> ID stmtTail | genFuncall | printfFunCall | scanfFunCall | ifStmt | whileStmt | returnStmt | breakStmt | continueStmt
	public Stmt stmt(Node parent) throws TokenException{
		Stmt stmt = new Stmt(parent);
		switch(peak()){
		case "scanf":
			stmt.stmtType = StmtType.SCANF_CALL;
			stmt.scanCall = new ScanFCall(stmt);
			scanfFunCall(stmt.scanCall);
			break;
		case "printf":
			stmt.stmtType = StmtType.PRINTF_CALL;
			stmt.printCall = new PrintFCall(stmt);
			printfFunCall(stmt.printCall);
			break;
		case "if":
			stmt.stmtType = StmtType.IF;
			stmt.ifStmt = new IfStmt(stmt);
			ifStmt(stmt.ifStmt);
			break;
		case "while":
			stmt.stmtType = StmtType.WHILE;
			stmt.whileStmt = new WhileStmt(stmt);
			whileStmt(stmt.whileStmt);
			break;
		case "return":
			stmt.stmtType = StmtType.RETURN;
			stmt.returnStmt = new ReturnStmt(stmt.returnStmt);
			returnStmt(stmt.returnStmt);
			break;
		case "break":
			stmt.stmtType = StmtType.BREAK;
			stmt.breakStmt = new BreakStmt(stmt);
			breakStmt(stmt.breakStmt);
			break;
		case "continue":
			stmt.stmtType = StmtType.CONTINUE;
			stmt.continueStmt = new ContinueStmt(stmt);
			continueStmt(stmt.continueStmt);
			break;
		default:
			if(currentToken.getType() == TokenType.IDENTIFIER){
				stmt.identifier = new Identifier(stmt);
				stmt.identifier.string = currentToken.getStringRep();
				match(TokenType.IDENTIFIER);
				stmt.tail = new StmtTail(stmt);
				stmtTail(stmt, stmt.tail);
			}
			break;
		}
		return stmt;
	}
	
	public void stmtTail(Stmt stmt, StmtTail tail) throws TokenException{
		switch(peak()){
		case "=":
			stmt.stmtType = StmtType.ASSIGNMENT;
			tail.assignment = new Assignment(tail);
			tail.assignment.id = stmt.identifier;
			assignment(tail.assignment);
			break;
		case "(":
			stmt.stmtType = StmtType.FUN_CALL;
			tail.funCall = new GenFunCall(tail);
			tail.funCall.functionId = stmt.identifier;
			genFunCall(tail.funCall);
			break;
		case "[":
			tail.assignment = new Assignment(tail);
			tail.assignment.expr = new Expression(tail.assignment);
			stmt.stmtType = StmtType.ARRAY_ACCESS;
			stmt.identifier.idType = IDType.VAR_ARRAY;
			tail.idB = idB(stmt.identifier);
			match("=");
			expr(tail.assignment.expr);
			match(";");
			break;
		default: 
			throw new TokenException("stmtTail parse error saw " + currentToken.toString());
		}
	}
	
	//breakStmt --> break ;
	public void breakStmt(BreakStmt stmt) throws TokenException{
		match("break");
		match(TokenType.SEMI_COLON);
	}
	
	// assignment --> = expr ;
	public void assignment(Assignment assignment) throws TokenException{
		match("=");
		assignment.expr = new Expression(assignment);
		expr(assignment.expr);
		match(TokenType.SEMI_COLON);
	}
	// genFunCall --> ID ( exprList ) ;
	public void genFunCall(GenFunCall gCall) throws TokenException{
		match(TokenType.LEFT_PARAN);
		gCall.exprList = new LinkedList<Expression>();
		//set parent of exprs
		exprList(gCall.exprList);
		match(TokenType.RIGHT_PARAN);
		match(TokenType.SEMI_COLON);
		processFunctionName(gCall.functionId);
	}
	
	// printfFunCall --> printf ( STRING printfFunTail
	public void printfFunCall(PrintFCall pCall) throws TokenException{
		match("printf");
		match(TokenType.LEFT_PARAN);
		if(peakType() == TokenType.STRING){
			pCall.string = currentToken.getStringRep();
		}
		match(TokenType.STRING);
		pCall.tail = new PrintFTail(pCall);
		printfFunTail(pCall.tail);
	}

	// printfFunTail --> ) ; | , expr ) ;
	public void printfFunTail(PrintFTail tail) throws TokenException{
		switch(peakType()){
		case RIGHT_PARAN:
			match(TokenType.RIGHT_PARAN);
			match(TokenType.SEMI_COLON);
			tail.nodeType = NodeType.EMPTY;
			break;
		case COMMA:
			match(TokenType.COMMA);
			tail.expr = new Expression(tail);
			expr(tail.expr);
			match(TokenType.RIGHT_PARAN);
			match(TokenType.SEMI_COLON);
			break;
		default: 
			throw new TokenException("Predicted printfFunTail, but token is not in FIRST(printfFunTail)");
		}
	}
	// scanfFunCall --> ID ( STRING , & expr ) ;
	public void scanfFunCall(ScanFCall scanCall) throws TokenException{
		match(TokenType.IDENTIFIER);
		match(TokenType.LEFT_PARAN);
		if(peakType() == TokenType.STRING){
			scanCall.string = currentToken.getStringRep();
		}
		match(TokenType.STRING);
		match(TokenType.COMMA);
		match("&");
		scanCall.expr = new Expression(scanCall);
		expr(scanCall.expr);
		match(TokenType.RIGHT_PARAN);
		match(TokenType.SEMI_COLON);
	}
	
	// exprList --> $ | nonEmptyExprList
	public void exprList(LinkedList<Expression> list) throws TokenException{
		switch(peak()){
		case "$":
			match("$");
			break;
		default:
			nonEmptyExprList(list);
			break;
		}
	}
	
	// nonEmptyExprList --> expr nonEmptyExprListB
	public void nonEmptyExprList(LinkedList<Expression> list) throws TokenException{
		list.add(expr(new Expression(null)));
		nonEmptyExprListB(list);
	}
	// nonEmptyExprListB --> $ | , expr
	public void nonEmptyExprListB(LinkedList<Expression> list) throws TokenException{ 
		switch(peak()){
		case "$":
			match("$");
			break;
		case ",":
			match(TokenType.COMMA);
			list.add(expr(new Expression(null)));
			nonEmptyExprListB(list);
			break;
		default:
			break;
		}
	}
	
	// ifStmt --> if ( conditionExpr ) blockStmts ifStmtB

	public void ifStmt(IfStmt ifStmt) throws TokenException{
		match("if");
		match(TokenType.LEFT_PARAN);
		ifStmt.condExpr = new ConditionExpression(ifStmt);
		condExpr(ifStmt.condExpr);
		match(TokenType.RIGHT_PARAN);
		ifStmt.blockStmts = new BlockStmts(ifStmt);
		blockStmts(ifStmt.blockStmts);
		ifStmt.ifStmtTail = new IfStmtTail(ifStmt);
		ifStmtB(ifStmt.ifStmtTail);
	}
	//ifStmtB --> $ | else blockStmts
	public void ifStmtB(IfStmtTail tail) throws TokenException{
		switch(peak()){
		case "else":
			match("else");
			tail.blockStmts = new BlockStmts(tail);
			blockStmts(tail.blockStmts);
			break;
		default:
			tail.nodeType = NodeType.EMPTY;
			break;
		}
	}
	
	// condExpr --> condition conditionExprTail
	public void condExpr(ConditionExpression cExpr) throws TokenException{
		cExpr.condition = new Condition(cExpr);
		condition(cExpr.condition);
		cExpr.conditionExprTail = new ConditionExpressionTail(cExpr);
		conditionExprTail(cExpr.conditionExprTail);
	}
	
	//condExprTail --> $ | conditionOp condition
	public void conditionExprTail(ConditionExpressionTail tail) throws TokenException{
		tail.condition = new Condition(tail);
		tail.tail = new ConditionExpressionTail(tail);
		switch(peak()){
		case "&&":
			tail.conditionOp = conditionOp();
			condition(tail.condition);
			conditionExprTail(tail);
			break;
		case "||":
			tail.conditionOp = conditionOp();
			condition(tail.condition);
			conditionExprTail(tail);
			break;
		case "!":
			tail.conditionOp = conditionOp();
			condition(tail.condition);
			conditionExprTail(tail);
			break;
		default:
			tail.nodeType = NodeType.EMPTY;
			break;
		}
	}
	// condition --> expr comparisionOP expr
	public void condition(Condition condition) throws TokenException{
		condition.leftExpr = new Expression(condition);
		expr(condition.leftExpr);
		condition.relOp = comparisonOp();
		condition.rightExpr = new Expression(condition);
		expr(condition.rightExpr);
	}
	
	
	public RelationalOperator comparisonOp() throws TokenException{
		switch(peak()){
		case "==":
			match("==");
			return RelationalOperator.EQUALITY;
		case "!=":
			match("!=");
			return RelationalOperator.NOT_EQUAL;
		case ">":
			match(">");
			return RelationalOperator.GT;
		case ">=":
			match(">=");
			return RelationalOperator.GTE;
		case "<":
			match("<");
			return RelationalOperator.LT;
		case "<=":
			match("<=");
			return RelationalOperator.LTE;
		default:
			throw new TokenException("Predicted comparsionOp, but token is not in FIRST(comparisionOp)");
		}
	}
	
	public ConditionOperator conditionOp() throws TokenException{
		switch(peak()){
		case "&&":
			match("&&");
			return ConditionOperator.AND;
		case "||":
			match("||");
			return ConditionOperator.OR;
		case "!":
			match("!");
			return ConditionOperator.NOT;
		default:
			throw new TokenException("Predicted conditionOp, but +"+ currentToken.getStringRep() + "is not in FIRST(conditionOP)");
		}
	}
	public void whileStmt(WhileStmt whileStmt)  throws TokenException{
		match("while");
		match(TokenType.LEFT_PARAN);
		whileStmt.conditionExpr = new ConditionExpression(whileStmt);
		condExpr(whileStmt.conditionExpr);
		match(TokenType.RIGHT_PARAN);
		whileStmt.blockStmts = new BlockStmts(whileStmt);
		blockStmts(whileStmt.blockStmts);
	}
	// returnStmt --> return return_tail
	public void returnStmt(ReturnStmt returnStmt)  throws TokenException{
		match("return");
		returnStmt.returnStmtTail = new ReturnStmtTail(returnStmt);
		returnTail(returnStmt.returnStmtTail);
	}
	//return_tail --> expr ; | ;
	public void returnTail(ReturnStmtTail tail)  throws TokenException{
		switch(peak()){
		case ";":
			match(TokenType.SEMI_COLON);
			tail.nodeType = NodeType.EMPTY;
			break;
		default:
			tail.expr = new Expression(tail);
			expr(tail.expr);
			match(TokenType.SEMI_COLON);
			break;
		}
	}
	
	// continueStmt --> continue ;
	public void continueStmt(ContinueStmt continueStmt)  throws TokenException{
		match("continue");
		match(TokenType.COMMA);
	}
	// expr --> term exprB
	public Expression expr(Expression expr)  throws TokenException{
		expr.term = new Term(expr);
		expr.exprB = new ExpressionB(expr);
		term(expr.term);
		exprB(expr.exprB);
		return expr;
	}
	// exprB --> $ | addop term exprB
	public void exprB(ExpressionB exprB)  throws TokenException{
		exprB.term = new Term(exprB);
		exprB.exprB = new ExpressionB(exprB);
		switch(peak()){
		case "+":
		case "-":
			exprB.addOpType = addOp();
			term(exprB.term);
			exprB(exprB.exprB);
			break;
		default:
			exprB.nodeType = NodeType.EMPTY;
			break;
		}
	}
	
	// + | -
	public AddOpType addOp() throws TokenException{
		switch(peakType()){
		case PLUS:
			match(TokenType.PLUS);
			return AddOpType.PLUS;
		case MINUS:
			match(TokenType.MINUS);
			return AddOpType.MINUS;
		default:
			throw new TokenException("Predicted addOp, but token is not in FIRST(addOp)");
		}
	}
	
	// term --> factor termB
	public void term(Term term)  throws TokenException{
		term.factor = new Factor(term);
		//term.factor.factorType = NodeType.EMPTY;
		term.termB = new TermB(term);
		factor(term.factor);
		termB(term.termB);
	
	}
	
	// termB --> $ | multOp factor termB
	public void termB(TermB termB) throws TokenException{
		termB.factor = new Factor(termB);
		termB.termB = new TermB(termB);
		switch(peak()){
		case "*":
		case "/":
			termB.multOp = multOp();
			factor(termB.factor);
			termB(termB.termB);
			break;
		default:
			termB.nodeType = NodeType.EMPTY;
			break;
		}
	}
	
	public MultOp multOp() throws TokenException{
		switch(peakType()){
		case MULT_OP:
			match(TokenType.MULT_OP);
			return MultOp.MULTIPLY;
		case DIV_OP:
			match(TokenType.DIV_OP);
			return MultOp.DIVIDE;
		default:
			throw new TokenException("Predicted multOp, but token is not in FIRST(addOp)");
		}	
	}
	
	// factor --> ID factorB | NUMBER | - NUMBER | ( expr )
	public void factor(Factor factor) throws TokenException{
		factor.factorTail = new FactorTail(factor);
		factor.factorTail.nodeType = NodeType.EMPTY;
		switch(peakType()){
		case IDENTIFIER:
			factor.factorType = FactorType.ID;
			factor.identifier = new Identifier(factor);
			factor.identifier.string = currentToken.getStringRep();
			match(TokenType.IDENTIFIER);
			factor.factorTail = new FactorTail(factor);
			factorB(factor.identifier, factor.factorTail);
			break;
		case NUMBER:
			factor.factorType = FactorType.NUMBER;
			factor.value = Integer.parseInt(currentToken.getStringRep());
			match(TokenType.NUMBER);
			factor.factorType = FactorType.NUMBER;
			break;
		case MINUS:
			factor.factorType = FactorType.NUMBER;
			match(TokenType.MINUS);
			factor.value = Integer.parseInt(currentToken.getStringRep());
			match(TokenType.NUMBER);
			factor.factorType = FactorType.NUMBER;
			factor.value = -1 * factor.value;
			break;
		case LEFT_PARAN:
			factor.factorType = FactorType.EXPRESSION;
			match(TokenType.LEFT_PARAN);
			factor.expr = new Expression(factor);
			expr(factor.expr);
			match(TokenType.RIGHT_PARAN);
			break;
		default:
			throw new TokenException("Predicted factor, but " + currentToken.getStringRep()+ " is not in FIRST(factor)");
		}
		
	}
	// factorB --> $ | [ expr ] | ( <exprlist )
	public void factorB(Identifier identifier, FactorTail tail)  throws TokenException{
		tail.nodeType = NodeType.FACTOR_TAIL;
		switch(peakType()){
		case LEFT_BRACKET:
			tail.tailType = FactorTailType.ARRAY;
			match(TokenType.LEFT_BRACKET);
			tail.expr = new Expression(tail);
			expr(tail.expr);
			match(TokenType.RIGHT_BRACKET);
			break;
		case LEFT_PARAN:
			identifier.idType = IDType.FUNCTION;
			tail.tailType = FactorTailType.PARAM_LIST;
			match(TokenType.LEFT_PARAN);
			tail.exprList = new LinkedList<Expression>();
			//set parent of exprs
			exprList(tail.exprList);
			match(TokenType.RIGHT_PARAN);
			break;
		default:
			tail.nodeType = NodeType.EMPTY;
			break;
		}
	}
	
	
	public String peak(){
		return currentToken.getStringRep();
	}
	
	public TokenType peakType(){
		return currentToken.type;
	}
	
	public boolean match(TokenType type) throws TokenException{
		if(currentToken.type == type){
			//System.out.println("matched " + type);
			if(type == TokenType.SEMI_COLON){
				stmtCount++;
			}
			if(type == TokenType.EPSILON){
				return true;
			}
			nextToken();
			return true;
		}
		throw new TokenException("Parse Error: expected " + type + " but saw " + currentToken.getStringRep() + " --> " + currentToken.type);
	}
	
	public boolean match(String expected) throws TokenException{
		String curr = currentToken.getStringRep();
		if (curr.equalsIgnoreCase(expected)){
			//System.out.println("Matched: " + expected);
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