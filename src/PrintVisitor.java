import java.util.HashMap;
import java.util.Map;

public class PrintVisitor extends Visitor {
	Program program; 
	int label, returnLabel;
	int localCount, count;
	String localVars = "LOCAL"; // local variable array name/id
	String globalVars = "GLOBAL"; // global variable array
	public PrintVisitor(Program program){
		this.program = program;
		label = 1;
	}
	
	public void printProgram(){
		program.accept(this);
	}
	
	@Override
	public void visitAssignment(Assignment node) {
		Expression expr = node.expr;
		ExpressionB next = expr.exprB;

		//redefine assignments to only have 2 operands on RHS
				
		if(next.nodeType == NodeType.EMPTY){
			
			if(expr.term.factor.factorTail.tailType == FactorTailType.PARAM_LIST){
				
				GenFunCall fCall = new GenFunCall(null);			
				fCall.functionId = expr.term.factor.identifier;
				fCall.exprList = expr.term.factor.factorTail.exprList;
				fCall.accept(this);
				
				expr.term.factor.identifier = new Identifier(expr.term.factor);
				expr.term.factor.identifier.string = "mem[(mem[1] + " + localCount +" )]";
				expr.term.factor.factorTail.nodeType = NodeType.EMPTY;
				System.out.print("    mem[(mem[1] + " + localCount +" )]");
				System.out.print(" = ");
				System.out.print("mem[(mem[0] + " + (count + 1) + ")];\n");
				
				return;
				
			}
			else{
				node.id.accept(this);
				System.out.print(" = ");
				node.expr.accept(this);
				System.out.print(";\n");
				return;
			}

		}
		
		if(next.exprB.exprB.nodeType == NodeType.EMPTY){
			node.id.accept(this);
			System.out.print(" = ");
			node.expr.accept(this);
			System.out.print(";\n");
			return;
		}
		
		generateExpressionCode(expr);
		node.id.accept(this);
		System.out.print(" = ");

		node.expr.accept(this);
		System.out.print(";\n");
	}

	
	
	
	@Override
	public void visitBlockStmts(BlockStmts node) {
	
		node.stmts.accept(this);
		
		
	}

	@Override
	public void visitBreakStmt(BreakStmt node) {
		System.out.print("break ;\n");
	}

	@Override
	public void visitCondition(Condition node) {
		generateExpressionCode(node.leftExpr);
		generateExpressionCode(node.rightExpr);
		node.leftExpr.accept(this);
		switch(node.relOp){
		case EQUALITY:
			System.out.print(" == ");
			break;
		case NOT_EQUAL:
			System.out.print(" != ");
			break;
		case LT:
			System.out.print(" < ");
			break;
		case GT:
			System.out.print(" > ");
			break;
		case LTE:
			System.out.print(" <= ");
			break;
		case GTE:
			System.out.print(" >= ");
			break;
		}
		node.rightExpr.accept(this);
	}

	@Override
	public void visitConditionExpression(ConditionExpression node) {
		node.condition.accept(this);
		node.conditionExprTail.accept(this);
		
	}

	@Override
	public void visitConditionExpressionTail(ConditionExpressionTail node) {
		if(node.nodeType != NodeType.EMPTY){
			switch(node.conditionOp){
			case AND:
				System.out.print("&&");
				node.tail.accept(this);
				break;
			case OR:
				System.out.print("||");
				node.tail.accept(this);
				break;
			case NOT:
				System.out.print("!");
				node.tail.accept(this);
				break;
			}
		}
	}

	@Override
	public void visitConditionOp(ConditionOp node) {
	
		
	}

	@Override
	public void visitConditionOperator(ConditionOperator node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitContinueStmt(ContinueStmt node) {
		System.out.print("Continue ;");
	}

	@Override
	public void visitDataDecl(DataDecl node) {
		switch(node.dataType){
		case INT:
			System.out.print("    int ");
			break;
		case VOID:
			System.out.print("    void ");
			break;
		default: 
			break;
		}
		for(Identifier id: node.idList){
			System.out.print(id.string + ", ");
		}
		System.out.print(";\n");
	}

	@Override
	public void visitExpression(Expression node) {
		node.term.accept(this);
		node.exprB.accept(this);
		
	}
	
	public void generateExpressionCode(Expression expr){
		
		ExpressionB next = expr.exprB;
				
		// check if the expression is just a single term
		if(next.nodeType == NodeType.EMPTY){
			generateTermCode(expr.term);
			return;
		}
		
		// check if the expression is just two terms
		
//		if(next.exprB.nodeType == NodeType.EMPTY){
//			generateTermCode(expr.term);
//			generateTermCode(next.term);
//			return;
//		}

		// First check that we have more than two operands
		while(next.nodeType != NodeType.EMPTY){
			//make a new assignment
			Assignment a = new Assignment(null);
			Identifier i = new Identifier(a);
			// set up new expression pointers
			Expression e = expr.copy();
			e.parent = a;
			generateTermCode(e.term);
			e.exprB = next.copy();
			generateTermCode(e.exprB.term);
			// set up new identifier pointers
			i.string = "mem[(mem[1] + " + localCount +" )]";
			i.idType = IDType.VARIABLE;
			
			// set up new assignment pointers
			a.id = i;
			a.expr = e;
			System.out.print(a.id.string + " = ");
			a.expr.accept(this);
			System.out.print(";\n");
			expr.term = new Term(expr, i);
			expr.exprB = next.exprB;
			next = expr.exprB;
			System.out.print("    ");
			localCount++;
		}
	}
	
	public void generateTermCode(Term term){
		TermB next = term.termB;
		
		
		if(term.factor.factorType == FactorType.EXPRESSION){
			generateExpressionCode(term.factor.expr);
		}
		
		if(term.factor.factorTail != null){
			// either an array or function call
			if(term.factor.factorTail.tailType == FactorTailType.PARAM_LIST){
				for(Expression expr: term.factor.factorTail.exprList){
					//System.out.println("here");
					generateExpressionCode(expr);
					//expr.accept(this);
				}
				if(term.factor.factorTail.tailType == FactorTailType.PARAM_LIST){
					
					GenFunCall fCall = new GenFunCall(null);			
					fCall.functionId = term.factor.identifier;
					fCall.exprList = term.factor.factorTail.exprList;
					fCall.accept(this);
					term.factor = new Factor(term);
					term.factor.factorTail = new FactorTail(term.factor);
					term.factor.identifier = new Identifier(term.factor);
					term.factor.factorTail.nodeType = NodeType.EMPTY;
					term.factor.factorType = FactorType.ID;
					term.factor.identifier.idType = IDType.VARIABLE;
					term.factor.identifier.string = "mem[(mem[1] + " + localCount +" )]";
					System.out.print("    mem[(mem[1] + " + localCount +" )]");
					System.out.print(" = ");
					System.out.print("mem[(mem[0] + " + "4" + ")];\n");
					
				}
			}
		}
		if(next.nodeType == NodeType.EMPTY){
			return;
		}
		

		
		while(next.nodeType != NodeType.EMPTY){
				
			// make a new assignment stmt
			Assignment a = new Assignment(null);
			Identifier i = new Identifier(a);

			// set up new term pointers
			Term newTerm = term.copy();
			newTerm.parent = a;
			newTerm.termB = next.copy();
			
			// set up new identifier pointers
			i.string = "mem[(mem[1] + " + localCount +" )]";
			i.idType = IDType.VARIABLE;
			Expression newExpr = new Expression(null);
			newExpr.term = newTerm; 
			newExpr.exprB = new ExpressionB(null);
			newExpr.exprB.nodeType = NodeType.EMPTY;
			a.id = i;
			a.expr = newExpr;	
			term.factor = new Factor(term, i);
			term.termB = next.termB;
			a.accept(this);
			next = term.termB;
			System.out.print("    ");
			localCount++;
		}
	}

	@Override
	public void visitExpressionB(ExpressionB node) {
		if(node.nodeType == NodeType.EMPTY){
			return;
		}
		switch(node.addOpType){
		case PLUS:
			System.out.print(" + ");
			break;
		case MINUS:
			System.out.print(" - ");
			break;
		}
		node.term.accept(this);
		node.exprB.accept(this);
	}

	@Override
	public void visitExprList(ExprList node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitFactor(Factor node) {
		switch(node.factorType){
		case NUMBER:
			System.out.print(node.value);
			break;
		case ID:
			node.identifier.accept(this);
			//System.out.print("function call in assignment");
			break;
		case EXPRESSION:
			//System.out.print("(");
			node.expr.accept(this);
			//System.out.print(")");
			break;
		default:
			break;
		}
		node.factorTail.accept(this);
		
	}
	
	@Override
	public void visitFactorTail(FactorTail node) {	
		switch(node.tailType){
		case ARRAY:
			System.out.print("(");
			node.expr.accept(this);
			System.out.print(")]");
			break;
		case PARAM_LIST:
			System.out.print("(");
			if(!node.exprList.isEmpty()){
				node.exprList.getFirst().accept(this);
				for(int i = 1; i < node.exprList.size(); i++){
					System.out.print(", ");
					node.exprList.get(i).accept(this);
				}
			}

			System.out.print(")");
			break;
		}
	}

	@Override
	public void visitFunction(Function node) {
		node.signature.accept(this);
		node.body.accept(this);
		
	}

	@Override
	public void visitFunctionBody(FunctionBody node) {
		if(node.nodeType == NodeType.EMPTY){
			System.out.print(";\n");
			return;
		}
		
		//System.out.print(" {\n");
		int localVarCount = 0;
		for(DataDecl data: node.dataDecls){
			localVarCount += data.idList.size();
		}

		//create a local variable array
		node.map = new HashMap<String, String>();
		//System.out.print("    int " + localVars + "[" + 20 + "];\n" );

		
		for(int j = 0; j < node.dataDecls.size(); j++){
			
			DataDecl data = node.dataDecls.get(j);
			//System.out.print("    " + newName + " = " + data.idList.peekFirst().string + ";\n");
			for(Identifier id: data.idList){
				String newName = "mem[(mem[1] + " + localCount +" )]";
				node.map.put(id.string, newName);
				localCount++;
			}
			
		}
		count = 1;
		count += localVarCount;

		for(Stmt stmt: node.stmts.stmtList){
			findIDs(stmt, node.map);
		}
		System.out.print("    mem[0] = (mem[1] + " + (count + 1) + ");\n");

		for(int i = 0; i < node.params.size(); i++){
			String newName = "mem[(mem[1] - " + count + ")]";
			Param param = node.params.get(i);

			node.map.put(param.paramName, newName);
			//localCount++;
		}
	
		for(Stmt stmt: node.stmts.stmtList){
			findIDs(stmt, node.map);
		}

		node.stmts.accept(this);
		
		//System.out.print("}\n");
		if(node.stmts.stmtList.getLast().stmtType != StmtType.RETURN){
		    System.out.print("    mem[(mem[1] - 1)] = 0;\n");
		    System.out.print("    mem[0] = mem[(mem[1] - 2)];\n");
		    System.out.print("    mem[1] = mem[(mem[1] - 3)];\n");
		    System.out.print("    goto *(void *) mem[mem[0]];\n");
		}
		localCount = 0;
	}
	
	public void countExpression(Expression expr){
		
		ExpressionB next = expr.exprB;
				
		// check if the expression is just a single term
		if(next.nodeType == NodeType.EMPTY){
			countTerm(expr.term);
			return;
		}
		
		// check if the expression is just two terms
		
		if(next.exprB.nodeType == NodeType.EMPTY){
			countTerm(expr.term);
			countTerm(next.term);
			return;
		}

		// First check that we have more than two operands
		while(next.nodeType != NodeType.EMPTY){
			count++;
			next = next.exprB;
		}
	}
	
	public void countTerm(Term term){
		TermB next = term.termB;
		
		
		if(term.factor.factorType == FactorType.EXPRESSION){
			countExpression(term.factor.expr);
			return;
		}
		
//		if(next.nodeType == NodeType.EMPTY){
//			return;
//		}
		
		if(term.factor.factorTail != null){
			// either an array or function call
			if(term.factor.factorTail.tailType == FactorTailType.PARAM_LIST){
				count++;
				for(Expression expr: term.factor.factorTail.exprList){
					countExpression(expr);
				}
			}
			
			
		}

	
		while(next.nodeType != NodeType.EMPTY){
			count++;
			next = next.termB;

		}
		
	}
	
	public void findIDs(Stmt stmt, HashMap<String, String> map){
		switch(stmt.stmtType){
		case PRINTF_CALL:
			if(stmt.printCall.tail != null){
				if(stmt.printCall.tail.nodeType != NodeType.EMPTY){
					findID(stmt.printCall.tail.expr, map);
					countExpression(stmt.printCall.tail.expr);
				}
			}
			break;
		case SCANF_CALL:
			//node.scanCall.accept(this);
			break;
		case IF:
			Expression left = stmt.ifStmt.condExpr.condition.leftExpr;
			Expression right = stmt.ifStmt.condExpr.condition.rightExpr;
			//System.out.println("searching in if expression");
			findID(left, map);
			countExpression(left);
			findID(right, map);
			countExpression(right);
			//System.out.println("finished if expression search");
			
			if(stmt.ifStmt.blockStmts != null){
				for(Stmt s: stmt.ifStmt.blockStmts.stmts.stmtList){
					findIDs(s, map);
				}
				if(stmt.ifStmt.ifStmtTail.nodeType != NodeType.EMPTY){
					for(Stmt s: stmt.ifStmt.ifStmtTail.blockStmts.stmts.stmtList){
						findIDs(s, map);
					}
				}
			}
			
			break;
		case WHILE:
			Expression leftW = stmt.whileStmt.conditionExpr.condition.leftExpr;
			Expression rightW = stmt.whileStmt.conditionExpr.condition.rightExpr;
			findID(leftW, map);
			countExpression(leftW);
			findID(rightW, map);
			countExpression(rightW);
			if(stmt.whileStmt.blockStmts != null ){
				for(Stmt s: stmt.whileStmt.blockStmts.stmts.stmtList){
					findIDs(s, map);
				}
			}
			break;
		case RETURN:
			if(stmt.returnStmt.returnStmtTail != null){
				if(stmt.returnStmt.returnStmtTail.nodeType != NodeType.EMPTY){
					findID(stmt.returnStmt.returnStmtTail.expr, map);
					countExpression(stmt.returnStmt.returnStmtTail.expr);
				}
			}
			break;
		case BREAK:
			//node.breakStmt.accept(this);
			break;
		case CONTINUE:
			//node.continueStmt.accept(this);
			break;
		case ASSIGNMENT:
			if(map.containsKey(stmt.identifier.string)){
				stmt.identifier.string = map.get(stmt.identifier.string);
			}
			if(program.map.containsKey(stmt.identifier.string)){
				stmt.identifier.string = program.map.get(stmt.identifier.string);
			}
			// search the RHS
			findID(stmt.tail.assignment.expr, map);
			countExpression(stmt.tail.assignment.expr);
			break;
		case ARRAY_ACCESS:
			if(map.containsKey(stmt.identifier.string)){
				stmt.identifier.string = map.get(stmt.identifier.string);
			}
			if(program.map.containsKey(stmt.identifier.string)){
				stmt.identifier.string = program.map.get(stmt.identifier.string);
			}
			if(stmt.identifier.idB != null){
				findID(stmt.identifier.idB.expr, map);
			}
			findID(stmt.tail.assignment.expr, map);
			countExpression(stmt.tail.assignment.expr);
			break;
		case FUN_CALL:
			for(Expression expr: stmt.tail.funCall.exprList){
				findID(expr, map);
				countExpression(expr);
			}
			break;
		default:
			//node.tail.accept(this);
			break;
		}
	}
	
	public void findID(Expression expr, HashMap<String, String> map){
		Term term = expr.term;
		ExpressionB next = expr.exprB;
	
		findID(term, map);
		while(next.nodeType != NodeType.EMPTY){
			findID(next.term, map);
			next = next.exprB;
		}
	}
	
	public void findID(Term term, HashMap<String, String> map){
		Factor factor = term.factor;
		TermB next = term.termB;
		findID(factor, map);
		while(next.nodeType != NodeType.EMPTY){
			findID(next.factor, map);
			next = next.termB;
		}
	}
	
	public void findID(Factor factor, HashMap<String, String> map){
		if(factor.factorType == FactorType.ID){
			//System.out.println("HERE id: " + factor.identifier.string);
			if(map.containsKey(factor.identifier.string)){
				//System.out.println("id exists in map w\\value: " + map.get(factor.identifier.string));
				factor.identifier.string = map.get(factor.identifier.string);
				//System.out.println("changed value to: " + factor.identifier.string);
			}
			if(program.map.containsKey(factor.identifier.string)){
				factor.identifier.string = program.map.get(factor.identifier.string);
			}
		}
		
		if(factor.factorType == FactorType.EXPRESSION){
			findID(factor.expr, map);
		}
		
		if(factor.factorTail != null){
			if(factor.factorTail.tailType == FactorTailType.PARAM_LIST){
				for(Expression expr: factor.factorTail.exprList){
					findID(expr,map);
				}
			}
			if(factor.factorTail.tailType == FactorTailType.ARRAY){
				findID(factor.factorTail.expr, map);
			}
		}
	}
	

	@Override
	public void visitFunctionSignature(FunctionSignature node) {
		node.id.accept(this);
		System.out.print(":\n    ;\n");
	}

	@Override
	public void visitGenFunCall(GenFunCall node) {
		for(Expression expr: node.exprList){
			generateExpressionCode(expr);
		}

		switch(node.functionId.string){
		case "read":
		case "write":
			node.functionId.accept(this);
			System.out.print("(");
			if(!node.exprList.isEmpty()){
				node.exprList.getFirst().accept(this);
			}

			for(int i = 1; i < node.exprList.size(); i++){
				System.out.print(", ");
				node.exprList.get(i).accept(this);
			
			}
			
			System.out.print(");\n");
			break;
			default:
				// store parameters
				int i = 1;

				
				//store base and frame pointers
				System.out.print("mem[mem[0]]");
				System.out.print(" = ");
				System.out.print("(long) &&_R" + returnLabel +";\n");
				
				
				for(Expression expr: node.exprList){
					System.out.print("    mem[(mem[0] + " + i + ")] = mem[(mem[1] + " + (count - i -1) + ")];\n");
					i++;
				}
				
				
				System.out.print("    mem[(mem[0] + " + (i) + ")] = mem[1];\n");
				System.out.print("    mem[(mem[0] + " + (i + 1) + ")] = mem[0];\n");
				System.out.print("    mem[1] = (mem[0] + " + (i + 3) + ");\n");
				System.out.print("    goto ");
				node.functionId.accept(this);
				System.out.print(";\n");
				System.out.print("_R" + returnLabel + ":\n    ;\n");
				returnLabel++;
				break;
		}
		

		
	}
	

	@Override
	public void visitIdentifier(Identifier node) {
		if(node.idType == IDType.VAR_ARRAY){
			generateExpressionCode(node.idB.expr);
			System.out.print(node.string);
			System.out.print("(");
			node.idB.expr.accept(this);
			System.out.print(")]");
		}else{
			System.out.print(node.string);
		}
		
		
	}

	@Override
	public void visitIfStmt(IfStmt node) {
		int bodyLabel = label;
		label++;
		
		generateExpressionCode(node.condExpr.condition.leftExpr);
		generateExpressionCode(node.condExpr.condition.rightExpr);
		System.out.print("if ( ");
		node.condExpr.accept(this);
		System.out.print(" ) goto L" + bodyLabel + ";\n");
		System.out.print("    goto L" + label + ";\n");
		System.out.print("L" + bodyLabel + ":\n    ;\n");
		node.blockStmts.accept(this);
		System.out.print("L" + label + ":\n    ;\n");
		label++;
		node.ifStmtTail.accept(this);
		
	}

	@Override
	public void visitIfStmtTail(IfStmtTail node) {

		if(node.nodeType == NodeType.EMPTY){
			//System.out.print("    L" + thisLabel + ":\n    ;\n");
			return;
		}
		node.blockStmts.accept(this);
		label++;
	}

	@Override
	public void visitNonEmptyExprList(NonEmptyExprList node) {
		// TODO Auto-generated method stub
		for(Expression expr: node.exprList){
			expr.accept(this);
			System.out.print(",");
		}
	}

	@Override
	public void visitNonEmptyExprListB(NonEmptyExprListB node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitPrintFCall(PrintFCall node) {
		if(node.tail.nodeType != NodeType.EMPTY){
			generateExpressionCode(node.tail.expr);
		}
		
		System.out.print("printf(" + node.string);
		node.tail.accept(this);
	}

	@Override
	public void visitPrintFTail(PrintFTail node) {
		if(node.nodeType == NodeType.EMPTY){
			System.out.print(");\n");
		}else{
			System.out.print(", ");
			node.expr.accept(this);
			System.out.print(");\n");
		}
	}

	@Override
	public void visitProgram(Program node) {
		int offset = 2;
		String varName;
		GlobalVariable gVar;
		Identifier gVarID;
		int k = 0;
		
		
		for(MetaStatement stmt: node.metaStatements){
			System.out.print(stmt.string);
		}
		
		for(int i = 0; i < node.globalVars.size(); i ++){
			gVar = node.globalVars.get(i);
			for(int j = 0; j < gVar.idList.size(); j++){
				varName = "mem" + "[" + (Integer.toString(offset)) + " + ";
				gVarID = gVar.idList.get(j);
				if(gVarID.idB != null){
					offset += gVarID.idB.expr.term.factor.value;
					//System.out.println("GVAR SPACE: " + globalVarSpace);
				}
				node.map.put(gVarID.string, varName);
				k++;
			}
			//gVar.accept(this);
			//node.map.put(gVar.id.string, varName);
			//System.out.print(varName + "")
		}
		System.out.print("long mem[2000];\n\n");
		System.out.print("int main(void) {\n");
		
		System.out.print("    mem[0] = "+ offset +";\n");
		System.out.print("    mem[1] = "+ (offset + 4) +";\n");
		
		System.out.print("    mem[(mem[0] + 1)] = mem[1];\n");
		System.out.print("    mem[(mem[0] + 2)] = mem[0];\n");
		System.out.print("    mem[(mem[0] + 3)] = 0;\n");
		System.out.print("    mem[mem[0]] = (long) &&_exit;\n");
		System.out.print("    goto main;\n");



		for(Function function: node.functions){
			if(function.body.nodeType != NodeType.EMPTY){
				function.accept(this);
			}
			
		}

		System.out.print("_exit:\n    ;\n    return mem[(mem[0] + 3)];");
		System.out.print("\n}\n");
	}

	@Override
	public void visitReturnStmt(ReturnStmt node) {
		
		// first check if its just an empty return stmt
		if(node.returnStmtTail.nodeType == NodeType.EMPTY){
		    System.out.print("mem[0] = mem[(mem[1] - 2)];\n");
		    System.out.print("    mem[1] = mem[(mem[1] - 3)];\n");
		    System.out.print("    goto *(void *) mem[mem[0]];\n");
			return;
		}
		generateExpressionCode(node.returnStmtTail.expr);
		//System.out.print("return ");
		node.returnStmtTail.accept(this);
	}
	
	public void visitReturnStmtTail(ReturnStmtTail node){
		if(node.nodeType == NodeType.EMPTY){
		    System.out.print("mem[(mem[1] - 1)] = ");
		    node.expr.accept(this);
		    System.out.print(";\n");
		    System.out.print("    mem[0] = mem[(mem[1] - 2)];\n");
		    System.out.print("    mem[1] = mem[(mem[1] - 3)];\n");
		    System.out.print("    goto *(void *) mem[mem[0]];\n");
			//System.out.print(";\n");
		}else{
			//node.expr.accept(this);
		    System.out.print("mem[(mem[1] - 1)] = ");
		    node.expr.accept(this);
		    System.out.print(";\n");
		    System.out.print("    mem[0] = mem[(mem[1] - 2)];\n");
		    System.out.print("    mem[1] = mem[(mem[1] - 3)];\n");
		    System.out.print("    goto *(void *) mem[mem[0]];\n");
			//System.out.print(";\n");
		}

	}
	
	@Override
	public void visitScanFCall(ScanFCall node) {
		System.out.print("scanf(");
		System.out.print("'" + node.string + "'" + ",&");
		node.expr.accept(this);
		
	}

	public void visitStmt(Stmt node) {
		System.out.print("    ");
		switch(node.stmtType){
		case PRINTF_CALL:
			node.printCall.accept(this);
			break;
		case SCANF_CALL:
			node.scanCall.accept(this);
			break;
		case IF:
			node.ifStmt.accept(this);
			break;
		case WHILE:
			node.whileStmt.accept(this);
			break;
		case RETURN:
			node.returnStmt.accept(this);
			break;
		case BREAK:
			node.breakStmt.accept(this);
			break;
		case CONTINUE:
			node.continueStmt.accept(this);
			break;
		case ARRAY_ACCESS:
			node.tail.assignment.id = node.identifier;
			node.tail.accept(this);
			break;
		case FUN_CALL:
			node.tail.accept(this);
			break;
		default:
			node.tail.accept(this);
			break;
		}
	}

	@Override
	public void visitStmts(Stmts node) {
		for(Stmt stmt: node.stmtList){
			stmt.accept(this);
		}
		
	}

	@Override
	public void visitStmtTail(StmtTail node) {
		if(node.assignment != null){
			generateExpressionCode(node.assignment.expr);
			node.assignment.accept(this);
			
		}else if(node.funCall != null){
			node.funCall.accept(this);
		}
		
	}

	@Override
	public void visitTerm(Term node) {
		node.factor.accept(this);
		node.termB.accept(this);
	}

	@Override
	public void visitTermB(TermB node) {
		if(node.nodeType == NodeType.EMPTY){
			return;
		}
		switch(node.multOp){
		case MULTIPLY:
			System.out.print(" * ");
			break;
		case DIVIDE:
			System.out.print(" / ");
		}
		node.factor.accept(this);
		node.termB.accept(this);
		
	}

	@Override
	public void visitWhileStmts(WhileStmt node) {
		int conditionLabel = label;
		label ++;
		int bodyLabel = label;
		label++;
		System.out.print("L" + conditionLabel +":\n    ;\n");
		System.out.print("    if ( ");
		node.conditionExpr.accept(this);
		System.out.print(" ) goto L" + bodyLabel + ";\n\n");
		int outsideLoop = bodyLabel + 1;
		System.out.print("    goto L" + outsideLoop +";\n\n");
		System.out.print("L" + bodyLabel + ":\n    ;\n");
		label++;
		node.blockStmts.accept(this);
		System.out.print("    goto L" + conditionLabel + ";\n\n");
		//System.out.print("\n\n");
		System.out.print("L" + outsideLoop + ":\n    ;\n");
	}

	public void visitGlobalVariable(GlobalVariable node) {
		Identifier identifier;
		if(node.dataType == DataType.INT){
			System.out.print("int ");
		}else if(node.dataType == DataType.VOID){
			System.out.print("void ");
		}
		for(int i = 0; i < node.idList.size() - 1; i ++){
			identifier = node.idList.get(i);
			identifier.accept(this);
			identifier.idB.accept(this);
			System.out.print(", ");
		}
		System.out.print(node.idList.getLast().string);
		System.out.print(";\n");
		
		
	}

	public void visitMetaStatement(MetaStatement metaStatement) {
		System.out.print(metaStatement.string);
		
	}


}
