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
		T__9=10, T__10=11, GRAPH=12, CHILD=13, SUBDIVISION=14, TILETYPE=15, VERTEX=16, 
		EDGE=17, TILE=18, SPLIT=19, CONNECT=20, END=21, WHITESPACE=22, NUMBER=23, 
		ID=24;
	public static final int
		RULE_file = 0, RULE_mainline = 1, RULE_subline = 2, RULE_phrase = 3, RULE_definition = 4, 
		RULE_tileDefinition = 5, RULE_subdivisionDefinition = 6, RULE_expression = 7, 
		RULE_function = 8, RULE_tileFunction = 9, RULE_splitFunction = 10, RULE_connectFunction = 11, 
		RULE_assignment = 12, RULE_vertexAssignment = 13, RULE_edgeAssignment = 14, 
		RULE_childAssignment = 15, RULE_graphAssignment = 16, RULE_graphDeclaration = 17, 
		RULE_childList = 18, RULE_node = 19, RULE_face = 20;
	public static final String[] ruleNames = {
		"file", "mainline", "subline", "phrase", "definition", "tileDefinition", 
		"subdivisionDefinition", "expression", "function", "tileFunction", "splitFunction", 
		"connectFunction", "assignment", "vertexAssignment", "edgeAssignment", 
		"childAssignment", "graphAssignment", "graphDeclaration", "childList", 
		"node", "face"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "','", "'}'", "'('", "')'", "'='", "'['", "']'", "'.'", "'vertex'", 
		"'face'", "'GRAPH'", "'CHILD'", "'SUBDIVISION'", "'TILETYPE'", "'VERTEX'", 
		"'EDGE'", "'tile'", "'split'", "'connect'", "';'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		"GRAPH", "CHILD", "SUBDIVISION", "TILETYPE", "VERTEX", "EDGE", "TILE", 
		"SPLIT", "CONNECT", "END", "WHITESPACE", "NUMBER", "ID"
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
			setState(43); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(42);
				mainline();
				}
				}
				setState(45); 
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
			setState(47);
			phrase();
			setState(48);
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
			setState(50);
			expression();
			setState(51);
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
			setState(55);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SUBDIVISION:
			case TILETYPE:
				enterOuterAlt(_localctx, 1);
				{
				setState(53);
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
				setState(54);
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
			setState(59);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TILETYPE:
				enterOuterAlt(_localctx, 1);
				{
				setState(57);
				tileDefinition();
				}
				break;
			case SUBDIVISION:
				enterOuterAlt(_localctx, 2);
				{
				setState(58);
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
		public List<TerminalNode> NUMBER() { return getTokens(EscherParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(EscherParser.NUMBER, i);
		}
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			match(TILETYPE);
			setState(62);
			match(ID);
			setState(63);
			match(T__0);
			setState(64);
			match(NUMBER);
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(65);
				match(T__1);
				setState(66);
				match(NUMBER);
				}
				}
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(72);
			match(T__2);
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
			setState(74);
			match(SUBDIVISION);
			setState(75);
			match(ID);
			setState(76);
			match(T__0);
			setState(78); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(77);
				subline();
				}
				}
				setState(80); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CHILD) | (1L << VERTEX) | (1L << EDGE) | (1L << TILE) | (1L << SPLIT) | (1L << CONNECT))) != 0) );
			setState(82);
			match(T__2);
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
			setState(86);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TILE:
			case SPLIT:
			case CONNECT:
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				function();
				}
				break;
			case CHILD:
			case VERTEX:
			case EDGE:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
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
			setState(91);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TILE:
				enterOuterAlt(_localctx, 1);
				{
				setState(88);
				tileFunction();
				}
				break;
			case SPLIT:
				enterOuterAlt(_localctx, 2);
				{
				setState(89);
				splitFunction();
				}
				break;
			case CONNECT:
				enterOuterAlt(_localctx, 3);
				{
				setState(90);
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
			setState(93);
			match(TILE);
			setState(94);
			match(T__3);
			setState(95);
			match(ID);
			setState(96);
			match(T__1);
			setState(97);
			match(NUMBER);
			setState(98);
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
			setState(100);
			match(SPLIT);
			setState(101);
			match(T__3);
			setState(102);
			node();
			setState(103);
			match(T__1);
			setState(104);
			node();
			setState(105);
			match(T__1);
			setState(106);
			match(NUMBER);
			setState(107);
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
			setState(109);
			match(CONNECT);
			setState(110);
			match(T__3);
			setState(111);
			node();
			setState(112);
			match(T__1);
			setState(113);
			node();
			setState(114);
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
			setState(119);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VERTEX:
				enterOuterAlt(_localctx, 1);
				{
				setState(116);
				vertexAssignment();
				}
				break;
			case EDGE:
				enterOuterAlt(_localctx, 2);
				{
				setState(117);
				edgeAssignment();
				}
				break;
			case CHILD:
				enterOuterAlt(_localctx, 3);
				{
				setState(118);
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
			setState(121);
			match(VERTEX);
			setState(122);
			match(ID);
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(123);
				match(T__1);
				setState(124);
				match(ID);
				}
				}
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(135);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(130);
				match(T__5);
				setState(133);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case SPLIT:
					{
					setState(131);
					splitFunction();
					}
					break;
				case ID:
					{
					setState(132);
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
			setState(137);
			match(EDGE);
			setState(138);
			match(ID);
			setState(147);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				{
				{
				setState(139);
				match(T__5);
				setState(140);
				connectFunction();
				}
				}
				break;
			case T__3:
				{
				{
				setState(141);
				match(T__3);
				setState(142);
				node();
				setState(143);
				match(T__1);
				setState(144);
				node();
				setState(145);
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
		public List<ChildListContext> childList() {
			return getRuleContexts(ChildListContext.class);
		}
		public ChildListContext childList(int i) {
			return getRuleContext(ChildListContext.class,i);
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
			setState(149);
			match(CHILD);
			setState(150);
			match(ID);
			setState(151);
			match(T__5);
			setState(152);
			match(ID);
			setState(153);
			match(T__3);
			setState(155); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(154);
				childList();
				}
				}
				setState(157); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__6 );
			setState(159);
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

	public static class GraphAssignmentContext extends ParserRuleContext {
		public TerminalNode GRAPH() { return getToken(EscherParser.GRAPH, 0); }
		public TerminalNode ID() { return getToken(EscherParser.ID, 0); }
		public GraphDeclarationContext graphDeclaration() {
			return getRuleContext(GraphDeclarationContext.class,0);
		}
		public GraphAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphAssignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterGraphAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitGraphAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitGraphAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GraphAssignmentContext graphAssignment() throws RecognitionException {
		GraphAssignmentContext _localctx = new GraphAssignmentContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_graphAssignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			match(GRAPH);
			setState(162);
			match(ID);
			setState(163);
			match(T__5);
			setState(164);
			graphDeclaration();
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

	public static class GraphDeclarationContext extends ParserRuleContext {
		public TileFunctionContext tileFunction() {
			return getRuleContext(TileFunctionContext.class,0);
		}
		public GraphDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterGraphDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitGraphDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitGraphDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GraphDeclarationContext graphDeclaration() throws RecognitionException {
		GraphDeclarationContext _localctx = new GraphDeclarationContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_graphDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			tileFunction();
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

	public static class ChildListContext extends ParserRuleContext {
		public List<NodeContext> node() {
			return getRuleContexts(NodeContext.class);
		}
		public NodeContext node(int i) {
			return getRuleContext(NodeContext.class,i);
		}
		public ChildListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_childList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterChildList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitChildList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitChildList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChildListContext childList() throws RecognitionException {
		ChildListContext _localctx = new ChildListContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_childList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			match(T__6);
			setState(169);
			node();
			setState(174);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(170);
				match(T__1);
				setState(171);
				node();
				}
				}
				setState(176);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(177);
			match(T__7);
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
		enterRule(_localctx, 38, RULE_node);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			match(ID);
			setState(185);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(180);
				match(T__8);
				setState(181);
				match(T__9);
				setState(182);
				match(T__6);
				setState(183);
				match(NUMBER);
				setState(184);
				match(T__7);
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

	public static class FaceContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(EscherParser.ID, 0); }
		public TerminalNode NUMBER() { return getToken(EscherParser.NUMBER, 0); }
		public FaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_face; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterFace(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitFace(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof EscherVisitor ) return ((EscherVisitor<? extends T>)visitor).visitFace(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FaceContext face() throws RecognitionException {
		FaceContext _localctx = new FaceContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_face);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(ID);
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(188);
				match(T__8);
				setState(189);
				match(T__10);
				setState(190);
				match(T__6);
				setState(191);
				match(NUMBER);
				setState(192);
				match(T__7);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\32\u00c6\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\6\2.\n\2\r\2\16\2/\3\3\3"+
		"\3\3\3\3\4\3\4\3\4\3\5\3\5\5\5:\n\5\3\6\3\6\5\6>\n\6\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\7\7F\n\7\f\7\16\7I\13\7\3\7\3\7\3\b\3\b\3\b\3\b\6\bQ\n\b\r\b\16"+
		"\bR\3\b\3\b\3\t\3\t\5\tY\n\t\3\n\3\n\3\n\5\n^\n\n\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\16\3\16\3\16\5\16z\n\16\3\17\3\17\3\17\3\17\7\17\u0080\n"+
		"\17\f\17\16\17\u0083\13\17\3\17\3\17\3\17\5\17\u0088\n\17\5\17\u008a\n"+
		"\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u0096\n\20"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\6\21\u009e\n\21\r\21\16\21\u009f\3\21\3"+
		"\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\24\7\24\u00af"+
		"\n\24\f\24\16\24\u00b2\13\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\5"+
		"\25\u00bc\n\25\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u00c4\n\26\3\26\2\2"+
		"\27\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*\2\2\2\u00c2\2-\3\2\2"+
		"\2\4\61\3\2\2\2\6\64\3\2\2\2\b9\3\2\2\2\n=\3\2\2\2\f?\3\2\2\2\16L\3\2"+
		"\2\2\20X\3\2\2\2\22]\3\2\2\2\24_\3\2\2\2\26f\3\2\2\2\30o\3\2\2\2\32y\3"+
		"\2\2\2\34{\3\2\2\2\36\u008b\3\2\2\2 \u0097\3\2\2\2\"\u00a3\3\2\2\2$\u00a8"+
		"\3\2\2\2&\u00aa\3\2\2\2(\u00b5\3\2\2\2*\u00bd\3\2\2\2,.\5\4\3\2-,\3\2"+
		"\2\2./\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\3\3\2\2\2\61\62\5\b\5\2\62\63"+
		"\7\27\2\2\63\5\3\2\2\2\64\65\5\20\t\2\65\66\7\27\2\2\66\7\3\2\2\2\67:"+
		"\5\n\6\28:\5\20\t\29\67\3\2\2\298\3\2\2\2:\t\3\2\2\2;>\5\f\7\2<>\5\16"+
		"\b\2=;\3\2\2\2=<\3\2\2\2>\13\3\2\2\2?@\7\21\2\2@A\7\32\2\2AB\7\3\2\2B"+
		"G\7\31\2\2CD\7\4\2\2DF\7\31\2\2EC\3\2\2\2FI\3\2\2\2GE\3\2\2\2GH\3\2\2"+
		"\2HJ\3\2\2\2IG\3\2\2\2JK\7\5\2\2K\r\3\2\2\2LM\7\20\2\2MN\7\32\2\2NP\7"+
		"\3\2\2OQ\5\6\4\2PO\3\2\2\2QR\3\2\2\2RP\3\2\2\2RS\3\2\2\2ST\3\2\2\2TU\7"+
		"\5\2\2U\17\3\2\2\2VY\5\22\n\2WY\5\32\16\2XV\3\2\2\2XW\3\2\2\2Y\21\3\2"+
		"\2\2Z^\5\24\13\2[^\5\26\f\2\\^\5\30\r\2]Z\3\2\2\2][\3\2\2\2]\\\3\2\2\2"+
		"^\23\3\2\2\2_`\7\24\2\2`a\7\6\2\2ab\7\32\2\2bc\7\4\2\2cd\7\31\2\2de\7"+
		"\7\2\2e\25\3\2\2\2fg\7\25\2\2gh\7\6\2\2hi\5(\25\2ij\7\4\2\2jk\5(\25\2"+
		"kl\7\4\2\2lm\7\31\2\2mn\7\7\2\2n\27\3\2\2\2op\7\26\2\2pq\7\6\2\2qr\5("+
		"\25\2rs\7\4\2\2st\5(\25\2tu\7\7\2\2u\31\3\2\2\2vz\5\34\17\2wz\5\36\20"+
		"\2xz\5 \21\2yv\3\2\2\2yw\3\2\2\2yx\3\2\2\2z\33\3\2\2\2{|\7\22\2\2|\u0081"+
		"\7\32\2\2}~\7\4\2\2~\u0080\7\32\2\2\177}\3\2\2\2\u0080\u0083\3\2\2\2\u0081"+
		"\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0089\3\2\2\2\u0083\u0081\3\2\2"+
		"\2\u0084\u0087\7\b\2\2\u0085\u0088\5\26\f\2\u0086\u0088\5(\25\2\u0087"+
		"\u0085\3\2\2\2\u0087\u0086\3\2\2\2\u0088\u008a\3\2\2\2\u0089\u0084\3\2"+
		"\2\2\u0089\u008a\3\2\2\2\u008a\35\3\2\2\2\u008b\u008c\7\23\2\2\u008c\u0095"+
		"\7\32\2\2\u008d\u008e\7\b\2\2\u008e\u0096\5\30\r\2\u008f\u0090\7\6\2\2"+
		"\u0090\u0091\5(\25\2\u0091\u0092\7\4\2\2\u0092\u0093\5(\25\2\u0093\u0094"+
		"\7\7\2\2\u0094\u0096\3\2\2\2\u0095\u008d\3\2\2\2\u0095\u008f\3\2\2\2\u0096"+
		"\37\3\2\2\2\u0097\u0098\7\17\2\2\u0098\u0099\7\32\2\2\u0099\u009a\7\b"+
		"\2\2\u009a\u009b\7\32\2\2\u009b\u009d\7\6\2\2\u009c\u009e\5&\24\2\u009d"+
		"\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u009d\3\2\2\2\u009f\u00a0\3\2"+
		"\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a2\7\7\2\2\u00a2!\3\2\2\2\u00a3\u00a4"+
		"\7\16\2\2\u00a4\u00a5\7\32\2\2\u00a5\u00a6\7\b\2\2\u00a6\u00a7\5$\23\2"+
		"\u00a7#\3\2\2\2\u00a8\u00a9\5\24\13\2\u00a9%\3\2\2\2\u00aa\u00ab\7\t\2"+
		"\2\u00ab\u00b0\5(\25\2\u00ac\u00ad\7\4\2\2\u00ad\u00af\5(\25\2\u00ae\u00ac"+
		"\3\2\2\2\u00af\u00b2\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1"+
		"\u00b3\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3\u00b4\7\n\2\2\u00b4\'\3\2\2\2"+
		"\u00b5\u00bb\7\32\2\2\u00b6\u00b7\7\13\2\2\u00b7\u00b8\7\f\2\2\u00b8\u00b9"+
		"\7\t\2\2\u00b9\u00ba\7\31\2\2\u00ba\u00bc\7\n\2\2\u00bb\u00b6\3\2\2\2"+
		"\u00bb\u00bc\3\2\2\2\u00bc)\3\2\2\2\u00bd\u00c3\7\32\2\2\u00be\u00bf\7"+
		"\13\2\2\u00bf\u00c0\7\r\2\2\u00c0\u00c1\7\t\2\2\u00c1\u00c2\7\31\2\2\u00c2"+
		"\u00c4\7\n\2\2\u00c3\u00be\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4+\3\2\2\2"+
		"\22/9=GRX]y\u0081\u0087\u0089\u0095\u009f\u00b0\u00bb\u00c3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}