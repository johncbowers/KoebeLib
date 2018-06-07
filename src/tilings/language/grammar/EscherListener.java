package tilings.language.grammar;

// Generated from Escher.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link EscherParser}.
 */
public interface EscherListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link EscherParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(EscherParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(EscherParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#mainline}.
	 * @param ctx the parse tree
	 */
	void enterMainline(EscherParser.MainlineContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#mainline}.
	 * @param ctx the parse tree
	 */
	void exitMainline(EscherParser.MainlineContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#subline}.
	 * @param ctx the parse tree
	 */
	void enterSubline(EscherParser.SublineContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#subline}.
	 * @param ctx the parse tree
	 */
	void exitSubline(EscherParser.SublineContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#phrase}.
	 * @param ctx the parse tree
	 */
	void enterPhrase(EscherParser.PhraseContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#phrase}.
	 * @param ctx the parse tree
	 */
	void exitPhrase(EscherParser.PhraseContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#definition}.
	 * @param ctx the parse tree
	 */
	void enterDefinition(EscherParser.DefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#definition}.
	 * @param ctx the parse tree
	 */
	void exitDefinition(EscherParser.DefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#tileDefinition}.
	 * @param ctx the parse tree
	 */
	void enterTileDefinition(EscherParser.TileDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#tileDefinition}.
	 * @param ctx the parse tree
	 */
	void exitTileDefinition(EscherParser.TileDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#subdivisionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterSubdivisionDefinition(EscherParser.SubdivisionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#subdivisionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitSubdivisionDefinition(EscherParser.SubdivisionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(EscherParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(EscherParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(EscherParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(EscherParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#tileFunction}.
	 * @param ctx the parse tree
	 */
	void enterTileFunction(EscherParser.TileFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#tileFunction}.
	 * @param ctx the parse tree
	 */
	void exitTileFunction(EscherParser.TileFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#splitFunction}.
	 * @param ctx the parse tree
	 */
	void enterSplitFunction(EscherParser.SplitFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#splitFunction}.
	 * @param ctx the parse tree
	 */
	void exitSplitFunction(EscherParser.SplitFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#connectFunction}.
	 * @param ctx the parse tree
	 */
	void enterConnectFunction(EscherParser.ConnectFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#connectFunction}.
	 * @param ctx the parse tree
	 */
	void exitConnectFunction(EscherParser.ConnectFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(EscherParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(EscherParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#vertexAssignment}.
	 * @param ctx the parse tree
	 */
	void enterVertexAssignment(EscherParser.VertexAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#vertexAssignment}.
	 * @param ctx the parse tree
	 */
	void exitVertexAssignment(EscherParser.VertexAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#edgeAssignment}.
	 * @param ctx the parse tree
	 */
	void enterEdgeAssignment(EscherParser.EdgeAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#edgeAssignment}.
	 * @param ctx the parse tree
	 */
	void exitEdgeAssignment(EscherParser.EdgeAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#childAssignment}.
	 * @param ctx the parse tree
	 */
	void enterChildAssignment(EscherParser.ChildAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#childAssignment}.
	 * @param ctx the parse tree
	 */
	void exitChildAssignment(EscherParser.ChildAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link EscherParser#node}.
	 * @param ctx the parse tree
	 */
	void enterNode(EscherParser.NodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link EscherParser#node}.
	 * @param ctx the parse tree
	 */
	void exitNode(EscherParser.NodeContext ctx);
}
