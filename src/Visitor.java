
public abstract class Visitor {
	
	public abstract void visitAssignment(Assignment node);
	public abstract void visitBlockStmts(BlockStmts node);
	public abstract void visitBreakStmt(BreakStmt node);
	public abstract void visitCondition(Condition node);
	public abstract void visitConditionExpression(ConditionExpression node);
	public abstract void visitConditionExpressionTail(ConditionExpressionTail node);
	public abstract void visitConditionOp(ConditionOp node);
	public abstract void visitConditionOperator(ConditionOperator node);
	public abstract void visitContinueStmt(ContinueStmt node);
	public abstract void visitDataDecl(DataDecl node);
	public abstract void visitExpression(Expression node);
	public abstract void visitExpressionB(ExpressionB node);
	public abstract void visitExprList(ExprList node);
	public abstract void visitFactor(Factor node);
	public abstract void visitFactorTail(FactorTail node);
	public abstract void visitFunction(Function node);
	public abstract void visitFunctionBody(FunctionBody node);
	public abstract void visitFunctionSignature(FunctionSignature node);
	public abstract void visitGenFunCall(GenFunCall node);
	public abstract void visitIdentifier(Identifier node);
	public abstract void visitIfStmt(IfStmt node);
	public abstract void visitIfStmtTail(IfStmtTail node);
	public abstract void visitNonEmptyExprList(NonEmptyExprList node);
	public abstract void visitNonEmptyExprListB(NonEmptyExprListB node);
	public abstract void visitPrintFCall(PrintFCall node);
	public abstract void visitPrintFTail(PrintFTail node);
	public abstract void visitProgram(Program node);
	public abstract void visitReturnStmt(ReturnStmt node);
	public abstract void visitReturnStmtTail(ReturnStmtTail node);
	public abstract void visitScanFCall(ScanFCall node);
	public abstract void visitStmt(Stmt node);
	public abstract void visitStmts(Stmts node);
	public abstract void visitStmtTail(StmtTail node);
	public abstract void visitTerm(Term node);
	public abstract void visitTermB(TermB node);
	public abstract void visitWhileStmts(WhileStmt node);
	public abstract void visitGlobalVariable(GlobalVariable node);
	
}
