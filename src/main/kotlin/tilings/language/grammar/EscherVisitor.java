package tilings.language.grammar;

// Generated from Escher.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link EscherParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface EscherVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link EscherParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(EscherParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#mainline}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainline(EscherParser.MainlineContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#subline}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubline(EscherParser.SublineContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#phrase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPhrase(EscherParser.PhraseContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinition(EscherParser.DefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#tileDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTileDefinition(EscherParser.TileDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#subdivisionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubdivisionDefinition(EscherParser.SubdivisionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(EscherParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(EscherParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#tileFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTileFunction(EscherParser.TileFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#splitFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSplitFunction(EscherParser.SplitFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#connectFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConnectFunction(EscherParser.ConnectFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(EscherParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#vertexAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVertexAssignment(EscherParser.VertexAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#edgeAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEdgeAssignment(EscherParser.EdgeAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#childAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChildAssignment(EscherParser.ChildAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#graphAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphAssignment(EscherParser.GraphAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#graphDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphDeclaration(EscherParser.GraphDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#childList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChildList(EscherParser.ChildListContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#node}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNode(EscherParser.NodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link EscherParser#face}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFace(EscherParser.FaceContext ctx);
}