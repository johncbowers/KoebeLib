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
		T__9=10, CHILD=11, SUBDIVISION=12, TILETYPE=13, VERTEX=14, EDGE=15, TILE=16, 
		SPLIT=17, CONNECT=18, END=19, WHITESPACE=20, NUMBER=21, ID=22;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "LETTER", "DIGIT", "CHILD", "SUBDIVISION", "TILETYPE", "VERTEX", 
		"EDGE", "TILE", "SPLIT", "CONNECT", "END", "WHITESPACE", "NUMBER", "ID"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\30\u009e\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t"+
		"\3\n\3\n\3\13\3\13\3\f\6\fI\n\f\r\f\16\fJ\3\r\3\r\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26"+
		"\3\27\6\27\u008c\n\27\r\27\16\27\u008d\3\27\3\27\3\30\6\30\u0093\n\30"+
		"\r\30\16\30\u0094\3\31\3\31\3\31\7\31\u009a\n\31\f\31\16\31\u009d\13\31"+
		"\2\2\32\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\2\31\2\33\r\35"+
		"\16\37\17!\20#\21%\22\'\23)\24+\25-\26/\27\61\30\3\2\4\4\2C\\c|\5\2\13"+
		"\f\17\17\"\"\2\u00a0\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"+
		"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3"+
		"\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2"+
		"%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61"+
		"\3\2\2\2\3\63\3\2\2\2\5\65\3\2\2\2\7\67\3\2\2\2\t9\3\2\2\2\13;\3\2\2\2"+
		"\r=\3\2\2\2\17?\3\2\2\2\21A\3\2\2\2\23C\3\2\2\2\25E\3\2\2\2\27H\3\2\2"+
		"\2\31L\3\2\2\2\33N\3\2\2\2\35T\3\2\2\2\37`\3\2\2\2!i\3\2\2\2#p\3\2\2\2"+
		"%u\3\2\2\2\'z\3\2\2\2)\u0080\3\2\2\2+\u0088\3\2\2\2-\u008b\3\2\2\2/\u0092"+
		"\3\2\2\2\61\u0096\3\2\2\2\63\64\7}\2\2\64\4\3\2\2\2\65\66\7\177\2\2\66"+
		"\6\3\2\2\2\678\7*\2\28\b\3\2\2\29:\7.\2\2:\n\3\2\2\2;<\7+\2\2<\f\3\2\2"+
		"\2=>\7?\2\2>\16\3\2\2\2?@\7\60\2\2@\20\3\2\2\2AB\7x\2\2B\22\3\2\2\2CD"+
		"\7]\2\2D\24\3\2\2\2EF\7_\2\2F\26\3\2\2\2GI\t\2\2\2HG\3\2\2\2IJ\3\2\2\2"+
		"JH\3\2\2\2JK\3\2\2\2K\30\3\2\2\2LM\4\62;\2M\32\3\2\2\2NO\7E\2\2OP\7J\2"+
		"\2PQ\7K\2\2QR\7N\2\2RS\7F\2\2S\34\3\2\2\2TU\7U\2\2UV\7W\2\2VW\7D\2\2W"+
		"X\7F\2\2XY\7K\2\2YZ\7X\2\2Z[\7K\2\2[\\\7U\2\2\\]\7K\2\2]^\7Q\2\2^_\7P"+
		"\2\2_\36\3\2\2\2`a\7V\2\2ab\7K\2\2bc\7N\2\2cd\7G\2\2de\7V\2\2ef\7[\2\2"+
		"fg\7R\2\2gh\7G\2\2h \3\2\2\2ij\7X\2\2jk\7G\2\2kl\7T\2\2lm\7V\2\2mn\7G"+
		"\2\2no\7Z\2\2o\"\3\2\2\2pq\7G\2\2qr\7F\2\2rs\7I\2\2st\7G\2\2t$\3\2\2\2"+
		"uv\7v\2\2vw\7k\2\2wx\7n\2\2xy\7g\2\2y&\3\2\2\2z{\7u\2\2{|\7r\2\2|}\7n"+
		"\2\2}~\7k\2\2~\177\7v\2\2\177(\3\2\2\2\u0080\u0081\7e\2\2\u0081\u0082"+
		"\7q\2\2\u0082\u0083\7p\2\2\u0083\u0084\7p\2\2\u0084\u0085\7g\2\2\u0085"+
		"\u0086\7e\2\2\u0086\u0087\7v\2\2\u0087*\3\2\2\2\u0088\u0089\7=\2\2\u0089"+
		",\3\2\2\2\u008a\u008c\t\3\2\2\u008b\u008a\3\2\2\2\u008c\u008d\3\2\2\2"+
		"\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u008f\3\2\2\2\u008f\u0090"+
		"\b\27\2\2\u0090.\3\2\2\2\u0091\u0093\5\31\r\2\u0092\u0091\3\2\2\2\u0093"+
		"\u0094\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095\60\3\2\2"+
		"\2\u0096\u009b\5\27\f\2\u0097\u009a\5\27\f\2\u0098\u009a\5\31\r\2\u0099"+
		"\u0097\3\2\2\2\u0099\u0098\3\2\2\2\u009a\u009d\3\2\2\2\u009b\u0099\3\2"+
		"\2\2\u009b\u009c\3\2\2\2\u009c\62\3\2\2\2\u009d\u009b\3\2\2\2\b\2J\u008d"+
		"\u0094\u0099\u009b\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
