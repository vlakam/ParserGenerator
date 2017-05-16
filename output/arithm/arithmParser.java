package arithm;

import laba4.Tree;

import java.io.InputStream;
import java.text.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import laba4.Tree;

public class arithmParser {
	private arithmLexer lex;
	
	public Tree parse(InputStream is) throws ParseException, IOException {
		lex = new arithmLexer(is);
		lex.nextToken();
		return start();
	}

	private Tree t() throws ParseException, IOException {
		switch (lex.curToken()) {
			case NUMBER: {
				List<Tree> f = new ArrayList<>();
				List<Tree> t1 = new ArrayList<>();
				f.add(f());
				t1.add(t1());
				return new Tree("T", f.get(0), t1.get(0));
			}
			case OPEN_BRACKET: {
				List<Tree> f = new ArrayList<>();
				List<Tree> t1 = new ArrayList<>();
				f.add(f());
				t1.add(t1());
				return new Tree("T", f.get(0), t1.get(0));
			}
			default:
				throw new AssertionError();
		}
	}

	private Tree f() throws ParseException, IOException {
		switch (lex.curToken()) {
			case NUMBER: {
				List<String> NUMBER = new ArrayList<>();
				if (lex.curToken().toString().equals("NUMBER")) {
					NUMBER.add(lex.curString());
				} else {
					throw new AssertionError("NUMBER expected, instead of " + lex.curToken().toString());
				}
				lex.nextToken();
				return new Tree("F", new Tree(NUMBER.get(0)));
			}
			case OPEN_BRACKET: {
				List<String> OPEN_BRACKET = new ArrayList<>();
				List<Tree> start = new ArrayList<>();
				List<String> CLOSE_BRACKET = new ArrayList<>();
				if (lex.curToken().toString().equals("OPEN_BRACKET")) {
					OPEN_BRACKET.add(lex.curString());
				} else {
					throw new AssertionError("OPEN_BRACKET expected, instead of " + lex.curToken().toString());
				}
				lex.nextToken();
				start.add(start());
				if (lex.curToken().toString().equals("CLOSE_BRACKET")) {
					CLOSE_BRACKET.add(lex.curString());
				} else {
					throw new AssertionError("CLOSE_BRACKET expected, instead of " + lex.curToken().toString());
				}
				lex.nextToken();
				return new Tree("F", new Tree("("), start.get(0), new Tree(")"));
			}
			default:
				throw new AssertionError();
		}
	}

	private Tree start() throws ParseException, IOException {
		switch (lex.curToken()) {
			case NUMBER: {
				List<Tree> t = new ArrayList<>();
				List<Tree> e1 = new ArrayList<>();
				t.add(t());
				e1.add(e1());
				return new Tree("E", t.get(0), e1.get(0));
			}
			case OPEN_BRACKET: {
				List<Tree> t = new ArrayList<>();
				List<Tree> e1 = new ArrayList<>();
				t.add(t());
				e1.add(e1());
				return new Tree("E", t.get(0), e1.get(0));
			}
			default:
				throw new AssertionError();
		}
	}

	private Tree e1() throws ParseException, IOException {
		switch (lex.curToken()) {
			case CLOSE_BRACKET: {
				return new Tree("E1");
			}
			case EOF: {
				return new Tree("E1");
			}
			case PLUS: {
				List<String> PLUS = new ArrayList<>();
				List<Tree> t = new ArrayList<>();
				List<Tree> e1 = new ArrayList<>();
				if (lex.curToken().toString().equals("PLUS")) {
					PLUS.add(lex.curString());
				} else {
					throw new AssertionError("PLUS expected, instead of " + lex.curToken().toString());
				}
				lex.nextToken();
				t.add(t());
				e1.add(e1());
				return new Tree("E1", new Tree("+"), t.get(0), e1.get(0));
			}
			default:
				throw new AssertionError();
		}
	}

	private Tree t1() throws ParseException, IOException {
		switch (lex.curToken()) {
			case MUL: {
				List<String> MUL = new ArrayList<>();
				List<Tree> f = new ArrayList<>();
				List<Tree> t1 = new ArrayList<>();
				if (lex.curToken().toString().equals("MUL")) {
					MUL.add(lex.curString());
				} else {
					throw new AssertionError("MUL expected, instead of " + lex.curToken().toString());
				}
				lex.nextToken();
				f.add(f());
				t1.add(t1());
				return new Tree("T1", new Tree("*"), f.get(0), t1.get(0));
			}
			case CLOSE_BRACKET: {
				return new Tree("T1");
			}
			case EOF: {
				return new Tree("T1");
			}
			case PLUS: {
				return new Tree("T1");
			}
			default:
				throw new AssertionError();
		}
	}

}
