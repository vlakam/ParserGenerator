// Generated from /home/petrovich/autoAntlr/src/Grammar.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarParser#grandma}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrandma(GrammarParser.GrandmaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code headerLabel}
	 * labeled alternative in {@link GrammarParser#header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeaderLabel(GrammarParser.HeaderLabelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code membersLabel}
	 * labeled alternative in {@link GrammarParser#members}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMembersLabel(GrammarParser.MembersLabelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nonTerminalLabel}
	 * labeled alternative in {@link GrammarParser#rule1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonTerminalLabel(GrammarParser.NonTerminalLabelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code terminalLabel}
	 * labeled alternative in {@link GrammarParser#rule1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminalLabel(GrammarParser.TerminalLabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#inherited}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInherited(GrammarParser.InheritedContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#declAttrs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclAttrs(GrammarParser.DeclAttrsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#callAttrs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallAttrs(GrammarParser.CallAttrsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg(GrammarParser.ArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#argType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgType(GrammarParser.ArgTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#argName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgName(GrammarParser.ArgNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#synthesized}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSynthesized(GrammarParser.SynthesizedContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#nonterminalVariant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonterminalVariant(GrammarParser.NonterminalVariantContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#nonterminalProduction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonterminalProduction(GrammarParser.NonterminalProductionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#terminalProduction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminalProduction(GrammarParser.TerminalProductionContext ctx);
}