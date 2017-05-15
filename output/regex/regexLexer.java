package regex;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

public class regexLexer {
	private InputStream is;
	private int curChar;
	private int curPos;
	private Token curToken;
	private String curString;

	public regexLexer(InputStream is) throws ParseException, IOException {
		this.is = is;
		curPos = 0;
		nextChar();
	}

	private boolean isBlank(int c) { return c == ' ' || c == '\r' || c == '\n' || c == '\t'; }

	private void nextChar() throws ParseException, IOException {
		curPos++;
		try {
			curChar = is.read();
		} catch (IOException e) {
			throw new ParseException(e.getMessage(), curPos);
		}
	}

	public Token curToken() {
		return curToken;
	}

	public int curPos() {
		return curPos;
	}

	public String curString() {
		return curString;
	}

	public void nextToken() throws ParseException, IOException {
		curString = "";
		if (curChar == -1) {
			curToken = Token.EOF;
			return;
		}
        if ('0' == ((char) curChar)) {
            curToken = Token.NUMBER;
            curString += (char) curChar;
			nextChar();
        }
        else if ('1' == ((char) curChar)) {
            curToken = Token.NUMBER;
            curString += (char) curChar;
			nextChar();
        }
        else if ('2' == ((char) curChar)) {
            curToken = Token.NUMBER;
            curString += (char) curChar;
			nextChar();
        }
        else if ('3' == ((char) curChar)) {
            curToken = Token.NUMBER;
            curString += (char) curChar;
			nextChar();
        }
        else if ('4' == ((char) curChar)) {
            curToken = Token.NUMBER;
            curString += (char) curChar;
			nextChar();
        }
        else if ('5' == ((char) curChar)) {
            curToken = Token.NUMBER;
            curString += (char) curChar;
			nextChar();
        }
        else if ('6' == ((char) curChar)) {
            curToken = Token.NUMBER;
            curString += (char) curChar;
			nextChar();
        }
        else if ('7' == ((char) curChar)) {
            curToken = Token.NUMBER;
            curString += (char) curChar;
			nextChar();
        }
        else if ('8' == ((char) curChar)) {
            curToken = Token.NUMBER;
            curString += (char) curChar;
			nextChar();
        }
        else if ('9' == ((char) curChar)) {
            curToken = Token.NUMBER;
            curString += (char) curChar;
			nextChar();
        }
        else if ('*' == ((char) curChar)) {
            curToken = Token.MUL;
            curString += (char) curChar;
			nextChar();
        }
        else if ('(' == ((char) curChar)) {
            curToken = Token.OPEN_BRACKET;
            curString += (char) curChar;
			nextChar();
        }
        else if (')' == ((char) curChar)) {
            curToken = Token.CLOSE_BRACKET;
            curString += (char) curChar;
			nextChar();
        }
        else if ('+' == ((char) curChar)) {
            curToken = Token.PLUS;
            curString += (char) curChar;
			nextChar();
        }
		else throw new AssertionError("Illegal character " + (char) curChar);
	}
}
