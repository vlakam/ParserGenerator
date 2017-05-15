// Generated from /home/petrovich/autoAntlr/src/Grammar.g4 by ANTLR 4.7
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		NON_TERM_NAME=10, TERM_NAME=11, MIXED_CASE=12, CODE=13, STRING=14, WS=15;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"NON_TERM_NAME", "TERM_NAME", "MIXED_CASE", "CODE", "STRING", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'@header'", "'@members'", "'->'", "':'", "'|'", "';'", "'['", "','", 
		"']'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, "NON_TERM_NAME", 
		"TERM_NAME", "MIXED_CASE", "CODE", "STRING", "WS"
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


	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\21x\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\7\13D\n\13\f\13\16\13"+
		"G\13\13\3\f\3\f\7\fK\n\f\f\f\16\fN\13\f\3\r\3\r\7\rR\n\r\f\r\16\rU\13"+
		"\r\3\16\3\16\6\16Y\n\16\r\16\16\16Z\3\16\5\16^\n\16\7\16`\n\16\f\16\16"+
		"\16c\13\16\3\16\3\16\3\17\3\17\3\17\3\17\7\17k\n\17\f\17\16\17n\13\17"+
		"\3\17\3\17\3\20\6\20s\n\20\r\20\16\20t\3\20\3\20\2\2\21\3\3\5\4\7\5\t"+
		"\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21\3\2\n\3"+
		"\2c|\6\2\62;C\\aac|\3\2C\\\5\2\62;C\\aa\4\2C\\c|\4\2}}\177\177\3\2))\5"+
		"\2\13\f\17\17\"\"\2\u0080\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2"+
		"\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2"+
		"\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3"+
		"\2\2\2\3!\3\2\2\2\5)\3\2\2\2\7\62\3\2\2\2\t\65\3\2\2\2\13\67\3\2\2\2\r"+
		"9\3\2\2\2\17;\3\2\2\2\21=\3\2\2\2\23?\3\2\2\2\25A\3\2\2\2\27H\3\2\2\2"+
		"\31O\3\2\2\2\33V\3\2\2\2\35f\3\2\2\2\37r\3\2\2\2!\"\7B\2\2\"#\7j\2\2#"+
		"$\7g\2\2$%\7c\2\2%&\7f\2\2&\'\7g\2\2\'(\7t\2\2(\4\3\2\2\2)*\7B\2\2*+\7"+
		"o\2\2+,\7g\2\2,-\7o\2\2-.\7d\2\2./\7g\2\2/\60\7t\2\2\60\61\7u\2\2\61\6"+
		"\3\2\2\2\62\63\7/\2\2\63\64\7@\2\2\64\b\3\2\2\2\65\66\7<\2\2\66\n\3\2"+
		"\2\2\678\7~\2\28\f\3\2\2\29:\7=\2\2:\16\3\2\2\2;<\7]\2\2<\20\3\2\2\2="+
		">\7.\2\2>\22\3\2\2\2?@\7_\2\2@\24\3\2\2\2AE\t\2\2\2BD\t\3\2\2CB\3\2\2"+
		"\2DG\3\2\2\2EC\3\2\2\2EF\3\2\2\2F\26\3\2\2\2GE\3\2\2\2HL\t\4\2\2IK\t\5"+
		"\2\2JI\3\2\2\2KN\3\2\2\2LJ\3\2\2\2LM\3\2\2\2M\30\3\2\2\2NL\3\2\2\2OS\t"+
		"\6\2\2PR\t\3\2\2QP\3\2\2\2RU\3\2\2\2SQ\3\2\2\2ST\3\2\2\2T\32\3\2\2\2U"+
		"S\3\2\2\2Va\7}\2\2WY\n\7\2\2XW\3\2\2\2YZ\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2["+
		"]\3\2\2\2\\^\5\33\16\2]\\\3\2\2\2]^\3\2\2\2^`\3\2\2\2_X\3\2\2\2`c\3\2"+
		"\2\2a_\3\2\2\2ab\3\2\2\2bd\3\2\2\2ca\3\2\2\2de\7\177\2\2e\34\3\2\2\2f"+
		"l\7)\2\2gk\n\b\2\2hi\7^\2\2ik\7)\2\2jg\3\2\2\2jh\3\2\2\2kn\3\2\2\2lj\3"+
		"\2\2\2lm\3\2\2\2mo\3\2\2\2nl\3\2\2\2op\7)\2\2p\36\3\2\2\2qs\t\t\2\2rq"+
		"\3\2\2\2st\3\2\2\2tr\3\2\2\2tu\3\2\2\2uv\3\2\2\2vw\b\20\2\2w \3\2\2\2"+
		"\f\2ELSZ]ajlt\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}