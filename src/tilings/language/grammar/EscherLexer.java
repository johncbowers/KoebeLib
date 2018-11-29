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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, SUBTILE=7, TYPE=8, VERTEX=9, 
		TILE=10, SPLIT=11, CONNECT=12, LETTER=13, END=14, WHITESPACE=15, NUMBER=16, 
		ID=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "DIGIT", "SUBTILE", "TYPE", 
		"VERTEX", "TILE", "SPLIT", "CONNECT", "LETTER", "END", "WHITESPACE", "NUMBER", 
		"ID"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23\u008c\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3"+
		"\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\tE\n"+
		"\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\5\13Z\n\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\6\17p\n\17\r\17"+
		"\16\17q\3\20\3\20\3\21\6\21w\n\21\r\21\16\21x\3\21\3\21\3\22\6\22~\n\22"+
		"\r\22\16\22\177\3\23\3\23\5\23\u0084\n\23\3\23\3\23\7\23\u0088\n\23\f"+
		"\23\16\23\u008b\13\23\2\2\24\3\3\5\4\7\5\t\6\13\7\r\b\17\2\21\t\23\n\25"+
		"\13\27\f\31\r\33\16\35\17\37\20!\21#\22%\23\3\2\4\4\2C\\c|\4\2\13\f\17"+
		"\17\2\u0092\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2"+
		"\2\2\r\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31"+
		"\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2"+
		"\2%\3\2\2\2\3\'\3\2\2\2\5)\3\2\2\2\7+\3\2\2\2\t-\3\2\2\2\13/\3\2\2\2\r"+
		"\61\3\2\2\2\17\63\3\2\2\2\21D\3\2\2\2\23F\3\2\2\2\25Y\3\2\2\2\27[\3\2"+
		"\2\2\31`\3\2\2\2\33f\3\2\2\2\35o\3\2\2\2\37s\3\2\2\2!v\3\2\2\2#}\3\2\2"+
		"\2%\u0083\3\2\2\2\'(\7*\2\2(\4\3\2\2\2)*\7.\2\2*\6\3\2\2\2+,\7+\2\2,\b"+
		"\3\2\2\2-.\7}\2\2.\n\3\2\2\2/\60\7\177\2\2\60\f\3\2\2\2\61\62\7?\2\2\62"+
		"\16\3\2\2\2\63\64\4\62;\2\64\20\3\2\2\2\65\66\7u\2\2\66\67\7w\2\2\678"+
		"\7d\2\289\7v\2\29:\7k\2\2:;\7n\2\2;<\7g\2\2<E\7u\2\2=>\7u\2\2>?\7w\2\2"+
		"?@\7d\2\2@A\7v\2\2AB\7k\2\2BC\7n\2\2CE\7g\2\2D\65\3\2\2\2D=\3\2\2\2E\22"+
		"\3\2\2\2FG\7V\2\2GH\7{\2\2HI\7r\2\2IJ\7g\2\2J\24\3\2\2\2KL\7x\2\2LM\7"+
		"g\2\2MN\7t\2\2NO\7v\2\2OP\7g\2\2PZ\7z\2\2QR\7x\2\2RS\7g\2\2ST\7t\2\2T"+
		"U\7v\2\2UV\7k\2\2VW\7e\2\2WX\7g\2\2XZ\7u\2\2YK\3\2\2\2YQ\3\2\2\2Z\26\3"+
		"\2\2\2[\\\7v\2\2\\]\7k\2\2]^\7n\2\2^_\7g\2\2_\30\3\2\2\2`a\7u\2\2ab\7"+
		"r\2\2bc\7n\2\2cd\7k\2\2de\7v\2\2e\32\3\2\2\2fg\7e\2\2gh\7q\2\2hi\7p\2"+
		"\2ij\7p\2\2jk\7g\2\2kl\7e\2\2lm\7v\2\2m\34\3\2\2\2np\t\2\2\2on\3\2\2\2"+
		"pq\3\2\2\2qo\3\2\2\2qr\3\2\2\2r\36\3\2\2\2st\7\f\2\2t \3\2\2\2uw\t\3\2"+
		"\2vu\3\2\2\2wx\3\2\2\2xv\3\2\2\2xy\3\2\2\2yz\3\2\2\2z{\b\21\2\2{\"\3\2"+
		"\2\2|~\5\17\b\2}|\3\2\2\2~\177\3\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2"+
		"\u0080$\3\2\2\2\u0081\u0084\5\35\17\2\u0082\u0084\5\17\b\2\u0083\u0081"+
		"\3\2\2\2\u0083\u0082\3\2\2\2\u0084\u0089\3\2\2\2\u0085\u0088\5\35\17\2"+
		"\u0086\u0088\5\17\b\2\u0087\u0085\3\2\2\2\u0087\u0086\3\2\2\2\u0088\u008b"+
		"\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a&\3\2\2\2\u008b"+
		"\u0089\3\2\2\2\13\2DYqx\177\u0083\u0087\u0089\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}