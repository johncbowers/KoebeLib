package tilings.language.grammar;

// Generated from Escher.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class EscherLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, GRAPH=12, CHILD=13, SUBDIVISION=14, TILETYPE=15, VERTEX=16, 
		EDGE=17, TILE=18, SPLIT=19, CONNECT=20, END=21, WHITESPACE=22, NUMBER=23, 
		ID=24;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "LETTER", "DIGIT", "GRAPH", "CHILD", "SUBDIVISION", "TILETYPE", 
		"VERTEX", "EDGE", "TILE", "SPLIT", "CONNECT", "END", "WHITESPACE", "NUMBER", 
		"ID"
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


	public EscherLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Escher.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\32\u00b2\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7"+
		"\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3"+
		"\f\3\f\3\f\3\f\3\r\6\rW\n\r\r\r\16\rX\3\16\3\16\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3"+
		"\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\30\3\30\3\31\6\31\u00a0\n\31\r\31\16\31\u00a1\3\31"+
		"\3\31\3\32\6\32\u00a7\n\32\r\32\16\32\u00a8\3\33\3\33\3\33\7\33\u00ae"+
		"\n\33\f\33\16\33\u00b1\13\33\2\2\34\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n"+
		"\23\13\25\f\27\r\31\2\33\2\35\16\37\17!\20#\21%\22\'\23)\24+\25-\26/\27"+
		"\61\30\63\31\65\32\3\2\4\4\2C\\c|\5\2\13\f\17\17\"\"\2\u00b4\2\3\3\2\2"+
		"\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3"+
		"\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\35\3\2\2"+
		"\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2"+
		"\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2"+
		"\2\2\3\67\3\2\2\2\59\3\2\2\2\7;\3\2\2\2\t=\3\2\2\2\13?\3\2\2\2\rA\3\2"+
		"\2\2\17C\3\2\2\2\21E\3\2\2\2\23G\3\2\2\2\25I\3\2\2\2\27P\3\2\2\2\31V\3"+
		"\2\2\2\33Z\3\2\2\2\35\\\3\2\2\2\37b\3\2\2\2!h\3\2\2\2#t\3\2\2\2%}\3\2"+
		"\2\2\'\u0084\3\2\2\2)\u0089\3\2\2\2+\u008e\3\2\2\2-\u0094\3\2\2\2/\u009c"+
		"\3\2\2\2\61\u009f\3\2\2\2\63\u00a6\3\2\2\2\65\u00aa\3\2\2\2\678\7}\2\2"+
		"8\4\3\2\2\29:\7.\2\2:\6\3\2\2\2;<\7\177\2\2<\b\3\2\2\2=>\7*\2\2>\n\3\2"+
		"\2\2?@\7+\2\2@\f\3\2\2\2AB\7?\2\2B\16\3\2\2\2CD\7]\2\2D\20\3\2\2\2EF\7"+
		"_\2\2F\22\3\2\2\2GH\7\60\2\2H\24\3\2\2\2IJ\7x\2\2JK\7g\2\2KL\7t\2\2LM"+
		"\7v\2\2MN\7g\2\2NO\7z\2\2O\26\3\2\2\2PQ\7h\2\2QR\7c\2\2RS\7e\2\2ST\7g"+
		"\2\2T\30\3\2\2\2UW\t\2\2\2VU\3\2\2\2WX\3\2\2\2XV\3\2\2\2XY\3\2\2\2Y\32"+
		"\3\2\2\2Z[\4\62;\2[\34\3\2\2\2\\]\7I\2\2]^\7T\2\2^_\7C\2\2_`\7R\2\2`a"+
		"\7J\2\2a\36\3\2\2\2bc\7E\2\2cd\7J\2\2de\7K\2\2ef\7N\2\2fg\7F\2\2g \3\2"+
		"\2\2hi\7U\2\2ij\7W\2\2jk\7D\2\2kl\7F\2\2lm\7K\2\2mn\7X\2\2no\7K\2\2op"+
		"\7U\2\2pq\7K\2\2qr\7Q\2\2rs\7P\2\2s\"\3\2\2\2tu\7V\2\2uv\7K\2\2vw\7N\2"+
		"\2wx\7G\2\2xy\7V\2\2yz\7[\2\2z{\7R\2\2{|\7G\2\2|$\3\2\2\2}~\7X\2\2~\177"+
		"\7G\2\2\177\u0080\7T\2\2\u0080\u0081\7V\2\2\u0081\u0082\7G\2\2\u0082\u0083"+
		"\7Z\2\2\u0083&\3\2\2\2\u0084\u0085\7G\2\2\u0085\u0086\7F\2\2\u0086\u0087"+
		"\7I\2\2\u0087\u0088\7G\2\2\u0088(\3\2\2\2\u0089\u008a\7v\2\2\u008a\u008b"+
		"\7k\2\2\u008b\u008c\7n\2\2\u008c\u008d\7g\2\2\u008d*\3\2\2\2\u008e\u008f"+
		"\7u\2\2\u008f\u0090\7r\2\2\u0090\u0091\7n\2\2\u0091\u0092\7k\2\2\u0092"+
		"\u0093\7v\2\2\u0093,\3\2\2\2\u0094\u0095\7e\2\2\u0095\u0096\7q\2\2\u0096"+
		"\u0097\7p\2\2\u0097\u0098\7p\2\2\u0098\u0099\7g\2\2\u0099\u009a\7e\2\2"+
		"\u009a\u009b\7v\2\2\u009b.\3\2\2\2\u009c\u009d\7=\2\2\u009d\60\3\2\2\2"+
		"\u009e\u00a0\t\3\2\2\u009f\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u009f"+
		"\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a4\b\31\2\2"+
		"\u00a4\62\3\2\2\2\u00a5\u00a7\5\33\16\2\u00a6\u00a5\3\2\2\2\u00a7\u00a8"+
		"\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\64\3\2\2\2\u00aa"+
		"\u00af\5\31\r\2\u00ab\u00ae\5\31\r\2\u00ac\u00ae\5\33\16\2\u00ad\u00ab"+
		"\3\2\2\2\u00ad\u00ac\3\2\2\2\u00ae\u00b1\3\2\2\2\u00af\u00ad\3\2\2\2\u00af"+
		"\u00b0\3\2\2\2\u00b0\66\3\2\2\2\u00b1\u00af\3\2\2\2\b\2X\u00a1\u00a8\u00ad"+
		"\u00af\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}