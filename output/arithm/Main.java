package arithm;

import laba4.Tree;
import java.io.*;
import java.text.ParseException;
public class Main {
	public static void main(String[] args) throws IOException, ParseException {
		InputStream is = new FileInputStream(new File("input.txt"));
		Tree result = (new arithmParser()).parse(is);
BufferedWriter bw = new BufferedWriter(new FileWriter("out.dot"));
result.printTree(0, bw);
bw.flush();
	}
}