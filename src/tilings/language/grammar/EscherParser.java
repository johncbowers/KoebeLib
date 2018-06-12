package tilings.language.grammar;

// Generated from Escher.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class EscherParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, CHILD=11, SUBDIVISION=12, TILETYPE=13, VERTEX=14, EDGE=15, TILE=16, 
		SPLIT=17, CONNECT=18, END=19, WHITESPACE=20, NUMBER=21, ID=22;
	public static final int
		RULE_file = 0, RULE_mainline = 1, RULE_subline = 2, RULE_phrase = 3, RULE_definition = 4, 
		RULE_tileDefinition = 5, RULE_subdivisionDefinition = 6, RULE_expression = 7, 
		RULE_function = 8, RULE_tileFunction = 9, RULE_splitFunction = 10, RULE_connectFunction = 11, 
		RULE_assignment = 12, RULE_vertexAssignment = 13, RULE_edgeAssignment = 14, 
		RULE_childAssignment = 15, RULE_node = 16;
	public static final String[] ruleNames = {
		"file", "mainline", "subline", "phrase", "definition", "tileDefinition", 
		"subdivisionDefinition", "expression", "function", "tileFunction", "splitFunction", 
		"connectFunction", "assignment", "vertexAssignment", "edgeAssignment", 
		"childAssignment", "node"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "'('", "','", "')'", "'='", "'.'", "'v'", "'['", "']'", 
		"'CHILD'", "'SUBDIVISION'", "'TILETYPE'", "'VERTEX'", "'EDGE'", "'tile'", 
		"'split'", "'connect'", "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, "CHILD", 
		"SUBDIVISION", "TILETYPE", "VERTEX", "EDGE", "TILE", "SPLIT", "CONNECT", 
		"END", "WHITESPACE", "NUMBER", "ID"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Escher.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public EscherParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class FileContext extends ParserRuleContext {
		public List<MainlineContext> mainline() {
			return getRuleContexts(MainlineContext.class);
		}
		public MainlineContext mainline(int i) {
			return getRuleContext(MainlineContext.class,i);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(34);
				mainline();
				}
				}
				setState(37); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CHILD) | (1L << SUBDIVISION) | (1L << TILETYPE) | (1L << VERTEX) | (1L << EDGE) | (1L << TILE) | (1L << SPLIT) | (1L << CONNECT))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MainlineContext extends ParserRuleContext {
		public PhraseContext phrase() {
			return getRuleContext(PhraseContext.class,0);
		}
		public TerminalNode END() { return getToken(EscherParser.END, 0); }
		public MainlineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainline; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterMainline(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitMainline(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitMainline(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MainlineContext mainline() throws RecognitionException {
		MainlineContext _localctx = new MainlineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_mainline);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			phrase();
			setState(40);
			match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SublineContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode END() { return getToken(EscherParser.END, 0); }
		public SublineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subline; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterSubline(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitSubline(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitSubline(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SublineContext subline() throws RecognitionException {
		SublineContext _localctx = new SublineContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_subline);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			expression();
			setState(43);
			match(END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PhraseContext extends ParserRuleContext {
		public DefinitionContext definition() {
			return getRuleContext(DefinitionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PhraseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_phrase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterPhrase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitPhrase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitPhrase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PhraseContext phrase() throws RecognitionException {
		PhraseContext _localctx = new PhraseContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_phrase);
		try {
			setState(47);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SUBDIVISION:
			case TILETYPE:
				enterOuterAlt(_localctx, 1);
				{
				setState(45);
				definition();
				}
				break;
			case CHILD:
			case VERTEX:
			case EDGE:
			case TILE:
			case SPLIT:
			case CONNECT:
				enterOuterAlt(_localctx, 2);
				{
				setState(46);
				expression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefinitionContext extends ParserRuleContext {
		public TileDefinitionContext tileDefinition() {
			return getRuleContext(TileDefinitionContext.class,0);
		}
		public SubdivisionDefinitionContext subdivisionDefinition() {
			return getRuleContext(SubdivisionDefinitionContext.class,0);
		}
		public DefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefinitionContext definition() throws RecognitionException {
		DefinitionContext _localctx = new DefinitionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_definition);
		try {
			setState(51);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TILETYPE:
				enterOuterAlt(_localctx, 1);
				{
				setState(49);
				tileDefinition();
				}
				break;
			case SUBDIVISION:
				enterOuterAlt(_localctx, 2);
				{
				setState(50);
				subdivisionDefinition();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TileDefinitionContext extends ParserRuleContext {
		public TerminalNode TILETYPE() { return getToken(EscherParser.TILETYPE, 0); }
		public TerminalNode ID() { return getToken(EscherParser.ID, 0); }
		public TerminalNode NUMBER() { return getToken(EscherParser.NUMBER, 0); }
		public TileDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tileDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterTileDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitTileDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitTileDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TileDefinitionContext tileDefinition() throws RecognitionException {
		TileDefinitionContext _localctx = new TileDefinitionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_tileDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(TILETYPE);
			setState(54);
			match(ID);
			setState(55);
			match(T__0);
			setState(56);
			match(NUMBER);
			setState(57);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubdivisionDefinitionContext extends ParserRuleContext {
		public TerminalNode SUBDIVISION() { return getToken(EscherParser.SUBDIVISION, 0); }
		public TerminalNode ID() { return getToken(EscherParser.ID, 0); }
		public List<SublineContext> subline() {
			return getRuleContexts(SublineContext.class);
		}
		public SublineContext subline(int i) {
			return getRuleContext(SublineContext.class,i);
		}
		public SubdivisionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subdivisionDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterSubdivisionDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitSubdivisionDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitSubdivisionDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubdivisionDefinitionContext subdivisionDefinition() throws RecognitionException {
		SubdivisionDefinitionContext _localctx = new SubdivisionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_subdivisionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(59);
			match(SUBDIVISION);
			setState(60);
			match(ID);
			setState(61);
			match(T__0);
			setState(63); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(62);
				subline();
				}
				}
				setState(65); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CHILD) | (1L << VERTEX) | (1L << EDGE) | (1L << TILE) | (1L << SPLIT) | (1L << CONNECT))) != 0) );
			setState(67);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_expression);
		try {
			setState(71);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TILE:
			case SPLIT:
			case CONNECT:
				enterOuterAlt(_localctx, 1);
				{
				setState(69);
				function();
				}
				break;
			case CHILD:
			case VERTEX:
			case EDGE:
				enterOuterAlt(_localctx, 2);
				{
				setState(70);
				assignment();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionContext extends ParserRuleContext {
		public TileFunctionContext tileFunction() {
			return getRuleContext(TileFunctionContext.class,0);
		}
		public SplitFunctionContext splitFunction() {
			return getRuleContext(SplitFunctionContext.class,0);
		}
		public ConnectFunctionContext connectFunction() {
			return getRuleContext(ConnectFunctionContext.class,0);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_function);
		try {
			setState(76);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TILE:
				enterOuterAlt(_localctx, 1);
				{
				setState(73);
				tileFunction();
				}
				break;
			case SPLIT:
				enterOuterAlt(_localctx, 2);
				{
				setState(74);
				splitFunction();
				}
				break;
			case CONNECT:
				enterOuterAlt(_localctx, 3);
				{
				setState(75);
				connectFunction();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TileFunctionContext extends ParserRuleContext {
		public TerminalNode TILE() { return getToken(EscherParser.TILE, 0); }
		public TerminalNode ID() { return getToken(EscherParser.ID, 0); }
		public TerminalNode NUMBER() { return getToken(EscherParser.NUMBER, 0); }
		public TileFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tileFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterTileFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitTileFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitTileFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TileFunctionContext tileFunction() throws RecognitionException {
		TileFunctionContext _localctx = new TileFunctionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_tileFunction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(TILE);
			setState(79);
			match(T__2);
			setState(80);
			match(ID);
			setState(81);
			match(T__3);
			setState(82);
			match(NUMBER);
			setState(83);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SplitFunctionContext extends ParserRuleContext {
		public TerminalNode SPLIT() { return getToken(EscherParser.SPLIT, 0); }
		public List<NodeContext> node() {
			return getRuleContexts(NodeContext.class);
		}
		public NodeContext node(int i) {
			return getRuleContext(NodeContext.class,i);
		}
		public TerminalNode NUMBER() { return getToken(EscherParser.NUMBER, 0); }
		public SplitFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_splitFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterSplitFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitSplitFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitSplitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SplitFunctionContext splitFunction() throws RecognitionException {
		SplitFunctionContext _localctx = new SplitFunctionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_splitFunction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(SPLIT);
			setState(86);
			match(T__2);
			setState(87);
			node();
			setState(88);
			match(T__3);
			setState(89);
			node();
			setState(90);
			match(T__3);
			setState(91);
			match(NUMBER);
			setState(92);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConnectFunctionContext extends ParserRuleContext {
		public TerminalNode CONNECT() { return getToken(EscherParser.CONNECT, 0); }
		public List<NodeContext> node() {
			return getRuleContexts(NodeContext.class);
		}
		public NodeContext node(int i) {
			return getRuleContext(NodeContext.class,i);
		}
		public ConnectFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_connectFunction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterConnectFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitConnectFunction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitConnectFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConnectFunctionContext connectFunction() throws RecognitionException {
		ConnectFunctionContext _localctx = new ConnectFunctionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_connectFunction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(CONNECT);
			setState(95);
			match(T__2);
			setState(96);
			node();
			setState(97);
			match(T__3);
			setState(98);
			node();
			setState(99);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public VertexAssignmentContext vertexAssignment() {
			return getRuleContext(VertexAssignmentContext.class,0);
		}
		public EdgeAssignmentContext edgeAssignment() {
			return getRuleContext(EdgeAssignmentContext.class,0);
		}
		public ChildAssignmentContext childAssignment() {
			return getRuleContext(ChildAssignmentContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_assignment);
		try {
			setState(104);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VERTEX:
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
				vertexAssignment();
				}
				break;
			case EDGE:
				enterOuterAlt(_localctx, 2);
				{
				setState(102);
				edgeAssignment();
				}
				break;
			case CHILD:
				enterOuterAlt(_localctx, 3);
				{
				setState(103);
				childAssignment();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VertexAssignmentContext extends ParserRuleContext {
		public TerminalNode VERTEX() { return getToken(EscherParser.VERTEX, 0); }
		public List<TerminalNode> ID() { return getTokens(EscherParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(EscherParser.ID, i);
		}
		public SplitFunctionContext splitFunction() {
			return getRuleContext(SplitFunctionContext.class,0);
		}
		public NodeContext node() {
			return getRuleContext(NodeContext.class,0);
		}
		public VertexAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vertexAssignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterVertexAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitVertexAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitVertexAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VertexAssignmentContext vertexAssignment() throws RecognitionException {
		VertexAssignmentContext _localctx = new VertexAssignmentContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_vertexAssignment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(VERTEX);
			setState(107);
			match(ID);
			setState(112);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(108);
				match(T__3);
				setState(109);
				match(ID);
				}
				}
				setState(114);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(120);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(115);
				match(T__5);
				setState(118);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case SPLIT:
					{
					setState(116);
					splitFunction();
					}
					break;
				case ID:
					{
					setState(117);
					node();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EdgeAssignmentContext extends ParserRuleContext {
		public TerminalNode EDGE() { return getToken(EscherParser.EDGE, 0); }
		public TerminalNode ID() { return getToken(EscherParser.ID, 0); }
		public ConnectFunctionContext connectFunction() {
			return getRuleContext(ConnectFunctionContext.class,0);
		}
		public List<NodeContext> node() {
			return getRuleContexts(NodeContext.class);
		}
		public NodeContext node(int i) {
			return getRuleContext(NodeContext.class,i);
		}
		public EdgeAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edgeAssignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterEdgeAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitEdgeAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitEdgeAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EdgeAssignmentContext edgeAssignment() throws RecognitionException {
		EdgeAssignmentContext _localctx = new EdgeAssignmentContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_edgeAssignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(EDGE);
			setState(123);
			match(ID);
			setState(132);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				{
				{
				setState(124);
				match(T__5);
				setState(125);
				connectFunction();
				}
				}
				break;
			case T__2:
				{
				{
				setState(126);
				match(T__2);
				setState(127);
				node();
				setState(128);
				match(T__3);
				setState(129);
				node();
				setState(130);
				match(T__4);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ChildAssignmentContext extends ParserRuleContext {
		public TerminalNode CHILD() { return getToken(EscherParser.CHILD, 0); }
		public List<TerminalNode> ID() { return getTokens(EscherParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(EscherParser.ID, i);
		}
		public List<NodeContext> node() {
			return getRuleContexts(NodeContext.class);
		}
		public NodeContext node(int i) {
			return getRuleContext(NodeContext.class,i);
		}
		public ChildAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_childAssignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterChildAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitChildAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitChildAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChildAssignmentContext childAssignment() throws RecognitionException {
		ChildAssignmentContext _localctx = new ChildAssignmentContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_childAssignment);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			match(CHILD);
			setState(135);
			match(ID);
			setState(136);
			match(T__5);
			setState(137);
			match(ID);
			setState(138);
			match(T__2);
			setState(139);
			node();
			setState(144);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(140);
				match(T__3);
				setState(141);
				node();
				}
				}
				setState(146);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(147);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NodeContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(EscherParser.ID, 0); }
		public TerminalNode NUMBER() { return getToken(EscherParser.NUMBER, 0); }
		public NodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_node; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterNode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitNode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitNode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NodeContext node() throws RecognitionException {
		NodeContext _localctx = new NodeContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_node);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			match(ID);
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(150);
				match(T__6);
				setState(151);
				match(T__7);
				setState(152);
				match(T__8);
				setState(153);
				match(NUMBER);
				setState(154);
				match(T__9);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\30\u00a0\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\6\2&\n\2\r\2\16\2\'\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\5\5\62\n\5\3"+
		"\6\3\6\5\6\66\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\6\bB\n\b\r\b"+
		"\16\bC\3\b\3\b\3\t\3\t\5\tJ\n\t\3\n\3\n\3\n\5\nO\n\n\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\16\3\16\3\16\5\16k\n\16\3\17\3\17\3\17\3\17\7\17q\n\17"+
		"\f\17\16\17t\13\17\3\17\3\17\3\17\5\17y\n\17\5\17{\n\17\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u0087\n\20\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\7\21\u0091\n\21\f\21\16\21\u0094\13\21\3\21\3\21"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u009e\n\22\3\22\2\2\23\2\4\6\b\n\f"+
		"\16\20\22\24\26\30\32\34\36 \"\2\2\2\u009d\2%\3\2\2\2\4)\3\2\2\2\6,\3"+
		"\2\2\2\b\61\3\2\2\2\n\65\3\2\2\2\f\67\3\2\2\2\16=\3\2\2\2\20I\3\2\2\2"+
		"\22N\3\2\2\2\24P\3\2\2\2\26W\3\2\2\2\30`\3\2\2\2\32j\3\2\2\2\34l\3\2\2"+
		"\2\36|\3\2\2\2 \u0088\3\2\2\2\"\u0097\3\2\2\2$&\5\4\3\2%$\3\2\2\2&\'\3"+
		"\2\2\2\'%\3\2\2\2\'(\3\2\2\2(\3\3\2\2\2)*\5\b\5\2*+\7\25\2\2+\5\3\2\2"+
		"\2,-\5\20\t\2-.\7\25\2\2.\7\3\2\2\2/\62\5\n\6\2\60\62\5\20\t\2\61/\3\2"+
		"\2\2\61\60\3\2\2\2\62\t\3\2\2\2\63\66\5\f\7\2\64\66\5\16\b\2\65\63\3\2"+
		"\2\2\65\64\3\2\2\2\66\13\3\2\2\2\678\7\17\2\289\7\30\2\29:\7\3\2\2:;\7"+
		"\27\2\2;<\7\4\2\2<\r\3\2\2\2=>\7\16\2\2>?\7\30\2\2?A\7\3\2\2@B\5\6\4\2"+
		"A@\3\2\2\2BC\3\2\2\2CA\3\2\2\2CD\3\2\2\2DE\3\2\2\2EF\7\4\2\2F\17\3\2\2"+
		"\2GJ\5\22\n\2HJ\5\32\16\2IG\3\2\2\2IH\3\2\2\2J\21\3\2\2\2KO\5\24\13\2"+
		"LO\5\26\f\2MO\5\30\r\2NK\3\2\2\2NL\3\2\2\2NM\3\2\2\2O\23\3\2\2\2PQ\7\22"+
		"\2\2QR\7\5\2\2RS\7\30\2\2ST\7\6\2\2TU\7\27\2\2UV\7\7\2\2V\25\3\2\2\2W"+
		"X\7\23\2\2XY\7\5\2\2YZ\5\"\22\2Z[\7\6\2\2[\\\5\"\22\2\\]\7\6\2\2]^\7\27"+
		"\2\2^_\7\7\2\2_\27\3\2\2\2`a\7\24\2\2ab\7\5\2\2bc\5\"\22\2cd\7\6\2\2d"+
		"e\5\"\22\2ef\7\7\2\2f\31\3\2\2\2gk\5\34\17\2hk\5\36\20\2ik\5 \21\2jg\3"+
		"\2\2\2jh\3\2\2\2ji\3\2\2\2k\33\3\2\2\2lm\7\20\2\2mr\7\30\2\2no\7\6\2\2"+
		"oq\7\30\2\2pn\3\2\2\2qt\3\2\2\2rp\3\2\2\2rs\3\2\2\2sz\3\2\2\2tr\3\2\2"+
		"\2ux\7\b\2\2vy\5\26\f\2wy\5\"\22\2xv\3\2\2\2xw\3\2\2\2y{\3\2\2\2zu\3\2"+
		"\2\2z{\3\2\2\2{\35\3\2\2\2|}\7\21\2\2}\u0086\7\30\2\2~\177\7\b\2\2\177"+
		"\u0087\5\30\r\2\u0080\u0081\7\5\2\2\u0081\u0082\5\"\22\2\u0082\u0083\7"+
		"\6\2\2\u0083\u0084\5\"\22\2\u0084\u0085\7\7\2\2\u0085\u0087\3\2\2\2\u0086"+
		"~\3\2\2\2\u0086\u0080\3\2\2\2\u0087\37\3\2\2\2\u0088\u0089\7\r\2\2\u0089"+
		"\u008a\7\30\2\2\u008a\u008b\7\b\2\2\u008b\u008c\7\30\2\2\u008c\u008d\7"+
		"\5\2\2\u008d\u0092\5\"\22\2\u008e\u008f\7\6\2\2\u008f\u0091\5\"\22\2\u0090"+
		"\u008e\3\2\2\2\u0091\u0094\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2"+
		"\2\2\u0093\u0095\3\2\2\2\u0094\u0092\3\2\2\2\u0095\u0096\7\7\2\2\u0096"+
		"!\3\2\2\2\u0097\u009d\7\30\2\2\u0098\u0099\7\t\2\2\u0099\u009a\7\n\2\2"+
		"\u009a\u009b\7\13\2\2\u009b\u009c\7\27\2\2\u009c\u009e\7\f\2\2\u009d\u0098"+
		"\3\2\2\2\u009d\u009e\3\2\2\2\u009e#\3\2\2\2\17\'\61\65CINjrxz\u0086\u0092"+
		"\u009d";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
