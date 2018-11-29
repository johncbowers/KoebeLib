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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, SUBTILE=7, TYPE=8, VERTEX=9, 
		TILE=10, SPLIT=11, CONNECT=12, LETTER=13, END=14, WHITESPACE=15, NUMBER=16, 
		ID=17;
	public static final int
		RULE_file = 0, RULE_mainline = 1, RULE_phrase = 2, RULE_definition = 3, 
		RULE_tileDefinition = 4, RULE_vertexSetDefinition = 5, RULE_subdivisionDefinition = 6, 
		RULE_expression = 7, RULE_function = 8, RULE_tileFunction = 9, RULE_splitFunction = 10, 
		RULE_assignment = 11, RULE_vertexAssignment = 12;
	public static final String[] ruleNames = {
		"file", "mainline", "phrase", "definition", "tileDefinition", "vertexSetDefinition", 
		"subdivisionDefinition", "expression", "function", "tileFunction", "splitFunction", 
		"assignment", "vertexAssignment"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "','", "')'", "'{'", "'}'", "'='", null, "'Type'", null, 
		"'tile'", "'split'", "'connect'", null, "'\n'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, "SUBTILE", "TYPE", "VERTEX", 
		"TILE", "SPLIT", "CONNECT", "LETTER", "END", "WHITESPACE", "NUMBER", "ID"
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
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(26);
				mainline();
				}
				}
				setState(29); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TYPE) | (1L << TILE) | (1L << ID))) != 0) );
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
	}

	public final MainlineContext mainline() throws RecognitionException {
		MainlineContext _localctx = new MainlineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_mainline);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(31);
			phrase();
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
	}

	public final PhraseContext phrase() throws RecognitionException {
		PhraseContext _localctx = new PhraseContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_phrase);
		try {
			setState(35);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TYPE:
				enterOuterAlt(_localctx, 1);
				{
				setState(33);
				definition();
				}
				break;
			case TILE:
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(34);
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
	}

	public final DefinitionContext definition() throws RecognitionException {
		DefinitionContext _localctx = new DefinitionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_definition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			tileDefinition();
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
		public TerminalNode TYPE() { return getToken(EscherParser.TYPE, 0); }
		public List<TerminalNode> ID() { return getTokens(EscherParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(EscherParser.ID, i);
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
	}

	public final TileDefinitionContext tileDefinition() throws RecognitionException {
		TileDefinitionContext _localctx = new TileDefinitionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_tileDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			match(TYPE);
			setState(40);
			match(ID);
			setState(41);
			match(T__0);
			setState(42);
			match(ID);
			setState(47);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(43);
				match(T__1);
				{
				setState(44);
				match(ID);
				}
				}
				}
				setState(49);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(50);
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

	public static class VertexSetDefinitionContext extends ParserRuleContext {
		public TerminalNode VERTEX() { return getToken(EscherParser.VERTEX, 0); }
		public List<TileFunctionContext> tileFunction() {
			return getRuleContexts(TileFunctionContext.class);
		}
		public TileFunctionContext tileFunction(int i) {
			return getRuleContext(TileFunctionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public VertexSetDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vertexSetDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).enterVertexSetDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EscherListener ) ((EscherListener)listener).exitVertexSetDefinition(this);
		}
	}

	public final VertexSetDefinitionContext vertexSetDefinition() throws RecognitionException {
		VertexSetDefinitionContext _localctx = new VertexSetDefinitionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_vertexSetDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			match(VERTEX);
			setState(53);
			match(T__3);
			setState(55); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(54);
				tileFunction();
				}
				}
				setState(57); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TILE );
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(59);
				match(T__1);
				setState(60);
				expression();
				}
				}
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(66);
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

	public static class SubdivisionDefinitionContext extends ParserRuleContext {
		public TerminalNode SUBTILE() { return getToken(EscherParser.SUBTILE, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
	}

	public final SubdivisionDefinitionContext subdivisionDefinition() throws RecognitionException {
		SubdivisionDefinitionContext _localctx = new SubdivisionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_subdivisionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			match(SUBTILE);
			setState(69);
			match(T__3);
			setState(70);
			expression();
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(71);
				match(T__1);
				setState(72);
				expression();
				}
				}
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(78);
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
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_expression);
		try {
			setState(82);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(80);
				function();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				assignment();
				}
				break;
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
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_function);
		try {
			setState(86);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TILE:
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				tileFunction();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				splitFunction();
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
	}

	public final TileFunctionContext tileFunction() throws RecognitionException {
		TileFunctionContext _localctx = new TileFunctionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_tileFunction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(TILE);
			setState(89);
			match(T__0);
			setState(90);
			match(ID);
			setState(91);
			match(T__1);
			setState(92);
			match(NUMBER);
			setState(93);
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

	public static class SplitFunctionContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(EscherParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(EscherParser.ID, i);
		}
		public TerminalNode SPLIT() { return getToken(EscherParser.SPLIT, 0); }
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
	}

	public final SplitFunctionContext splitFunction() throws RecognitionException {
		SplitFunctionContext _localctx = new SplitFunctionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_splitFunction);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(ID);
			setState(98); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(96);
				match(T__1);
				setState(97);
				match(ID);
				}
				}
				setState(100); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__1 );
			setState(102);
			match(T__5);
			setState(103);
			match(SPLIT);
			setState(104);
			match(T__0);
			setState(105);
			match(ID);
			setState(106);
			match(T__1);
			setState(107);
			match(ID);
			setState(108);
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

	public static class AssignmentContext extends ParserRuleContext {
		public VertexAssignmentContext vertexAssignment() {
			return getRuleContext(VertexAssignmentContext.class,0);
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
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			vertexAssignment();
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
		public List<TerminalNode> ID() { return getTokens(EscherParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(EscherParser.ID, i);
		}
		public SplitFunctionContext splitFunction() {
			return getRuleContext(SplitFunctionContext.class,0);
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
	}

	public final VertexAssignmentContext vertexAssignment() throws RecognitionException {
		VertexAssignmentContext _localctx = new VertexAssignmentContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_vertexAssignment);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			match(ID);
			setState(117);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(113);
					match(T__1);
					setState(114);
					match(ID);
					}
					} 
				}
				setState(119);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(120);
				match(T__5);
				setState(123);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
				case 1:
					{
					setState(121);
					splitFunction();
					}
					break;
				case 2:
					{
					setState(122);
					match(ID);
					}
					break;
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\23\u0082\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\6\2\36\n\2\r\2\16\2\37\3\3\3\3\3\4"+
		"\3\4\5\4&\n\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\7\6\60\n\6\f\6\16\6\63\13"+
		"\6\3\6\3\6\3\7\3\7\3\7\6\7:\n\7\r\7\16\7;\3\7\3\7\7\7@\n\7\f\7\16\7C\13"+
		"\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\7\bL\n\b\f\b\16\bO\13\b\3\b\3\b\3\t\3\t"+
		"\5\tU\n\t\3\n\3\n\5\nY\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f"+
		"\3\f\6\fe\n\f\r\f\16\ff\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\16\3"+
		"\16\3\16\7\16v\n\16\f\16\16\16y\13\16\3\16\3\16\3\16\5\16~\n\16\5\16\u0080"+
		"\n\16\3\16\2\2\17\2\4\6\b\n\f\16\20\22\24\26\30\32\2\2\2\u0080\2\35\3"+
		"\2\2\2\4!\3\2\2\2\6%\3\2\2\2\b\'\3\2\2\2\n)\3\2\2\2\f\66\3\2\2\2\16F\3"+
		"\2\2\2\20T\3\2\2\2\22X\3\2\2\2\24Z\3\2\2\2\26a\3\2\2\2\30p\3\2\2\2\32"+
		"r\3\2\2\2\34\36\5\4\3\2\35\34\3\2\2\2\36\37\3\2\2\2\37\35\3\2\2\2\37 "+
		"\3\2\2\2 \3\3\2\2\2!\"\5\6\4\2\"\5\3\2\2\2#&\5\b\5\2$&\5\20\t\2%#\3\2"+
		"\2\2%$\3\2\2\2&\7\3\2\2\2\'(\5\n\6\2(\t\3\2\2\2)*\7\n\2\2*+\7\23\2\2+"+
		",\7\3\2\2,\61\7\23\2\2-.\7\4\2\2.\60\7\23\2\2/-\3\2\2\2\60\63\3\2\2\2"+
		"\61/\3\2\2\2\61\62\3\2\2\2\62\64\3\2\2\2\63\61\3\2\2\2\64\65\7\5\2\2\65"+
		"\13\3\2\2\2\66\67\7\13\2\2\679\7\6\2\28:\5\24\13\298\3\2\2\2:;\3\2\2\2"+
		";9\3\2\2\2;<\3\2\2\2<A\3\2\2\2=>\7\4\2\2>@\5\20\t\2?=\3\2\2\2@C\3\2\2"+
		"\2A?\3\2\2\2AB\3\2\2\2BD\3\2\2\2CA\3\2\2\2DE\7\7\2\2E\r\3\2\2\2FG\7\t"+
		"\2\2GH\7\6\2\2HM\5\20\t\2IJ\7\4\2\2JL\5\20\t\2KI\3\2\2\2LO\3\2\2\2MK\3"+
		"\2\2\2MN\3\2\2\2NP\3\2\2\2OM\3\2\2\2PQ\7\7\2\2Q\17\3\2\2\2RU\5\22\n\2"+
		"SU\5\30\r\2TR\3\2\2\2TS\3\2\2\2U\21\3\2\2\2VY\5\24\13\2WY\5\26\f\2XV\3"+
		"\2\2\2XW\3\2\2\2Y\23\3\2\2\2Z[\7\f\2\2[\\\7\3\2\2\\]\7\23\2\2]^\7\4\2"+
		"\2^_\7\22\2\2_`\7\5\2\2`\25\3\2\2\2ad\7\23\2\2bc\7\4\2\2ce\7\23\2\2db"+
		"\3\2\2\2ef\3\2\2\2fd\3\2\2\2fg\3\2\2\2gh\3\2\2\2hi\7\b\2\2ij\7\r\2\2j"+
		"k\7\3\2\2kl\7\23\2\2lm\7\4\2\2mn\7\23\2\2no\7\5\2\2o\27\3\2\2\2pq\5\32"+
		"\16\2q\31\3\2\2\2rw\7\23\2\2st\7\4\2\2tv\7\23\2\2us\3\2\2\2vy\3\2\2\2"+
		"wu\3\2\2\2wx\3\2\2\2x\177\3\2\2\2yw\3\2\2\2z}\7\b\2\2{~\5\26\f\2|~\7\23"+
		"\2\2}{\3\2\2\2}|\3\2\2\2~\u0080\3\2\2\2\177z\3\2\2\2\177\u0080\3\2\2\2"+
		"\u0080\33\3\2\2\2\16\37%\61;AMTXfw}\177";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}