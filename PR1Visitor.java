// Generated from /home/ugrads/majors/jayantd11/CompLang/PR1/PR1.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PR1Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PR1Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PR1Parser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(PR1Parser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(PR1Parser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(PR1Parser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(PR1Parser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(PR1Parser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#writeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteStatement(PR1Parser.WriteStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(PR1Parser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#variableList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableList(PR1Parser.VariableListContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(PR1Parser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(PR1Parser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(PR1Parser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(PR1Parser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(PR1Parser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(PR1Parser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(PR1Parser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(PR1Parser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(PR1Parser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(PR1Parser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#logicalOrExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOrExpr(PR1Parser.LogicalOrExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#logicalAndExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpr(PR1Parser.LogicalAndExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(PR1Parser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#relationalExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpr(PR1Parser.RelationalExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#additiveExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpr(PR1Parser.AdditiveExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpr(PR1Parser.MultiplicativeExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(PR1Parser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(PR1Parser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link PR1Parser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(PR1Parser.ConstantContext ctx);
}