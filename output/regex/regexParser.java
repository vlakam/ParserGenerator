package regex;

import java.io.InputStream;
import java.text.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import laba4.Tree;

public class regexParser {
	private regexLexer lex;
	
	public void parse(InputStream is) throws ParseException, IOException {
		lex = new regexLexer(is);
		lex.nextToken();
		start();
	}

	private void t() throws ParseException, IOException {
		switch (lex.curToken()) {
			case NUMBER: {
				f();
				t1();
				return;
			}
			case OPEN_BRACKET: {
				f();
				t1();
				return;
			}
			default:
				throw new AssertionError();
		}
	}

	private void f() throws ParseException, IOException {
		switch (lex.curToken()) {
			case NUMBER: {
				List<String> NUMBER = new ArrayList<>();
				if (lex.curToken().toString().equals("NUMBER")) {
					NUMBER.add(lex.curString());
				} else {
					throw new AssertionError("NUMBER expected, instead of " + lex.curToken().toString());
				}
				lex.nextToken();
				return;
			}
			case OPEN_BRACKET: {
				List<String> OPEN_BRACKET = new ArrayList<>();
				List<String> CLOSE_BRACKET = new ArrayList<>();
				if (lex.curToken().toString().equals("OPEN_BRACKET")) {
					OPEN_BRACKET.add(lex.curString());
				} else {
					throw new AssertionError("OPEN_BRACKET expected, instead of " + lex.curToken().toString());
				}
				lex.nextToken();
				start();
				if (lex.curToken().toString().equals("CLOSE_BRACKET")) {
					CLOSE_BRACKET.add(lex.curString());
				} else {
					throw new AssertionError("CLOSE_BRACKET expected, instead of " + lex.curToken().toString());
				}
				lex.nextToken();
				return;
			}
			default:
				throw new AssertionError();
		}
	}

	private void start() throws ParseException, IOException {
		switch (lex.curToken()) {
			case NUMBER: {
				t();
				e1();
				return;
			}
			case OPEN_BRACKET: {
				t();
				e1();
				return;
			}
			default:
				throw new AssertionError();
		}
	}

	private void e1() throws ParseException, IOException {
		switch (lex.curToken()) {
			case CLOSE_BRACKET: {
				return;
			}
			case EOF: {
				return;
			}
			case PLUS: {
				List<String> PLUS = new ArrayList<>();
				if (lex.curToken().toString().equals("PLUS")) {
					PLUS.add(lex.curString());
				} else {
					throw new AssertionError("PLUS expected, instead of " + lex.curToken().toString());
				}
				lex.nextToken();
				t();
				e1();
				return;
			}
			default:
				throw new AssertionError();
		}
	}

	private void t1() throws ParseException, IOException {
		switch (lex.curToken()) {
			case MUL: {
				List<String> MUL = new ArrayList<>();
				if (lex.curToken().toString().equals("MUL")) {
					MUL.add(lex.curString());
				} else {
					throw new AssertionError("MUL expected, instead of " + lex.curToken().toString());
				}
				lex.nextToken();
				f();
				t1();
				return;
			}
			case CLOSE_BRACKET: {
				return;
			}
			case EOF: {
				return;
			}
			case PLUS: {
				return;
			}
			default:
				throw new AssertionError();
		}
	}

}
