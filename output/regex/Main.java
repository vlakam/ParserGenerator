package regex;
import java.io.*;
import java.text.ParseException;
public class Main {
	public static void main(String[] args) throws IOException, ParseException {
		InputStream is = new FileInputStream(new File("input.txt"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("out.dot"));
		(new regexParser()).parse(is);
	}
}