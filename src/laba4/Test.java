package laba4;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        File file = new File("./output/arithm");
        file.getParentFile().mkdirs();
        ParserGenerator parserGenerator = new ParserGenerator(file);
        try {
            parserGenerator.prepare(new File("./src/laba4/arithm.in"));
            parserGenerator.generateFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
