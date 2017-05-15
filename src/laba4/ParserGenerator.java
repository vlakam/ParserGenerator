package laba4;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import laba4.grammar.GrammarBaseListener;
import laba4.grammar.GrammarLexer;
import laba4.grammar.GrammarParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ParserGenerator {

    private String grammarName;
    private static final String EOF = "EOF";
    private static final String EPS = "EPS";

    private Node start;
    private File outputDir;

    private final HashMap<String, Node> nonTerminals = new HashMap<>();
    private final HashMap<String, Node> terminals = new HashMap<>();
    private final HashMap<String, HashSet<String>> first = new HashMap<>();
    private final HashMap<String, HashSet<String>> follow = new HashMap<>();

    private String members = "";
    private String header = "";

    public ParserGenerator(File outputDir) {
        this.outputDir = outputDir;
    }

    public void prepare(File grammarFile) throws IOException {
        grammarName = grammarFile.getName().split("[.]")[0];
        GrammarLexer lexer = new GrammarLexer(CharStreams.fromStream(new FileInputStream(grammarFile)));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GrammarParser parser = new GrammarParser(tokens);

        ParseTreeWalker walker = new ParseTreeWalker();
        GrammarBaseListener visitor = new GrammarBaseListener() {
            @Override
            public void enterNonTerminalLabel(GrammarParser.NonTerminalLabelContext ctx) {
                Node currentNode = getNonTerm(ctx.NON_TERM_NAME().getText());
                System.out.println("Current nonterminal: " + currentNode.getName());
                if (ctx.synthesized() != null) {
                    if (ctx.synthesized().NON_TERM_NAME() != null) {
                        currentNode.setReturnType(ctx.synthesized().NON_TERM_NAME().getText());
                    } else if (ctx.synthesized().TERM_NAME() != null) {
                        currentNode.setReturnType(ctx.synthesized().TERM_NAME().getText());
                    } else if (ctx.synthesized().MIXED_CASE() != null) {
                        currentNode.setReturnType(ctx.synthesized().MIXED_CASE().getText());
                    }
                }

                if (ctx.inherited() != null) {
                    for (GrammarParser.ArgContext arg : ctx.inherited().declAttrs().arg()) {
                        currentNode.putDeclAttrs(arg.argName().getText(), arg.argType().getText());
                    }
                }

                for (GrammarParser.NonterminalProductionContext nonterminalContext : ctx.nonterminalProduction()) {
                    Production production = new Production();

                    if (nonterminalContext.nonterminalVariant().isEmpty()) {
                        production.add(getTerm(EPS));
                    }

                    for (GrammarParser.NonterminalVariantContext variantContext : nonterminalContext.nonterminalVariant()) {
                        Node temp;
                        if (variantContext.NON_TERM_NAME() != null) {
                            temp = new Node(variantContext.NON_TERM_NAME().getText());
                            if (variantContext.callAttrs() != null) {
                                temp.setCallAttrs(refactorCallAttrs(variantContext.callAttrs()));
                            }
                        } else {
                            temp = new Node(variantContext.TERM_NAME().getText());
                        }
                        production.add(temp);
                    }

                    if (nonterminalContext.CODE() != null) {
                        production.setJavaCode(refactorCode(nonterminalContext.CODE()));
                    }

                    currentNode.add(production);
                }
            }

            @Override
            public void enterTerminalLabel(GrammarParser.TerminalLabelContext ctx) {
                String name = ctx.TERM_NAME().getText();
                Node curNode = getTerm(name);

                for (GrammarParser.TerminalProductionContext terminalContext : ctx.terminalProduction()) {
                    Production production = new Production();
                    String s = "";
                    for (TerminalNode term : terminalContext.STRING()) {
                        s += term.getText().substring(1);
                        s = s.substring(0, s.length() - 1);
                    }
                    production.add(new Node(s));
                    curNode.add(production);
                }
            }

            @Override
            public void enterMembersLabel(GrammarParser.MembersLabelContext ctx) {
                if (ctx.CODE() != null) {
                    members = refactorCode(ctx.CODE());
                }
            }

            @Override
            public void enterHeaderLabel(GrammarParser.HeaderLabelContext ctx) {
                if (ctx.CODE() != null) {
                    header = refactorCode(ctx.CODE());
                }
            }
        };
        ParseTree tree = parser.gramma();
        walker.walk(visitor, tree);
        getTerm(EOF);

        if (!nonTerminals.containsKey("start")) {
            throw new RuntimeException("No such rule: start");
        }
        start = getNonTerm("start");

        System.out.println(grammarName);
        System.out.println(start.getName());
        System.out.println("Nonterminals:");
        printData(nonTerminals);
        System.out.println("Terminals:");
        printData(terminals);
    }

    private static void printData(Map mp) {
        for (Object o : mp.entrySet()) {
            Map.Entry pairs = (Map.Entry) o;
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
        }
    }

    private Node getTerm(String name) {
        if (!terminals.containsKey(name)) {
            terminals.put(name, new Node(name));
        }
        return terminals.get(name);
    }

    private Node getNonTerm(String name) {
        if (!nonTerminals.containsKey(name)) {
            nonTerminals.put(name, new Node(name));
        }
        return nonTerminals.get(name);
    }

    private void generateLexer() throws IOException {
        final String LEXER_NAME = grammarName + "Lexer";
        File file = new File(outputDir, LEXER_NAME + ".java");
        //file.getParentFile().mkdirs();
        PrintWriter out = new PrintWriter(file);

        out.println(header);

        out.println("\nimport java.io.IOException;");
        out.println("import java.io.InputStream;");
        out.println("import java.text.ParseException;\n");

        out.println("public class " + LEXER_NAME + " {");
        out.println("\tprivate InputStream is;");
        out.println("\tprivate int curChar;");
        out.println("\tprivate int curPos;");
        out.println("\tprivate Token curToken;");
        out.println("\tprivate String curString;\n");
        out.println("\tpublic " + LEXER_NAME + "(InputStream is) throws ParseException, IOException {");
        out.println("\t\tthis.is = is;");
        out.println("\t\tcurPos = 0;");
        out.println("\t\tnextChar();");
        out.println("\t}\n");

        out.println(
                "\tprivate boolean isBlank(int c) { return c == ' ' || c == '\\r' || c == '\\n' || c == '\\t'; }\n");

        out.println("\tprivate void nextChar() throws ParseException, IOException {");
        out.println("\t\tcurPos++;");
        out.println("\t\ttry {");
        out.println("\t\t\tcurChar = is.read();");
        out.println("\t\t} catch (IOException e) {");
        out.println("\t\t\tthrow new ParseException(e.getMessage(), curPos);");
        out.println("\t\t}");
        out.println("\t}\n");

        out.println("\tpublic Token curToken() {\n\t\treturn curToken;\n\t}\n");

        out.println("\tpublic int curPos() {\n\t\treturn curPos;\n\t}\n");

        out.println("\tpublic String curString() {\n\t\treturn curString;\n\t}\n");

        out.println("\tpublic void nextToken() throws ParseException, IOException {");
        out.println("\t\tcurString = \"\";");

        out.println("\t\tif (curChar == -1) {");
        out.println("\t\t\tcurToken = Token.EOF;");
        out.println("\t\t\treturn;");
        out.println("\t\t}");

        boolean first = true;
        for (String curStringTerminal : terminals.keySet()) {
            for (Production productionString : terminals.get(curStringTerminal).getProductionList()) {
                out.println(String.format(
                        (first ? "        if" : "        else if") +
                                " (\'%1$s\' == ((char) curChar)) {\n" +
                                "            curToken = Token.%2$s;\n" +
                                "            curString += (char) curChar;\n" +
                                "\t\t\tnextChar();\n" +
                                "        }",
                        productionString.get(0).getName(), curStringTerminal.toUpperCase()
                ));
                first = false;
            }
        }
        out.println("\t\telse throw new AssertionError(\"Illegal character \" + (char) curChar);");
        out.println("\t}\n}");
        out.close();
    }

    private void generateMainFile() throws IOException {
        File file = new File(outputDir, "Main.java");
        file.getParentFile().mkdirs();
        PrintWriter out = new PrintWriter(file);

        out.println(header);
        out.println("import java.io.*;");
        out.println("import java.text.ParseException;");

        out.println("public class Main {");
        out.println("\tpublic static void main(String[] args) throws IOException, ParseException {");
        out.println("\t\tInputStream is = new FileInputStream(new File(\"input.txt\"));");
        out.println("\t\tBufferedWriter bw = new BufferedWriter(new FileWriter(\"out.dot\"));");
        out.println("\t\t(new "+ grammarName+"Parser" +"()).parse(is" + (start.getDeclAttrs(true).isEmpty() ? "" : ", " + start.getDeclAttrs(false)) + ");");
        out.println("\t}");
        out.print("}");
        out.close();
    }

    private void createMiscFiles() throws IOException {
        PrintWriter out = new PrintWriter(new File(outputDir, "input.txt"));
        out.close();
    }

    private void generateParser() throws IOException {
        final String PARSER_NAME = grammarName + "Parser";
        final String LEXER_NAME = grammarName + "Lexer";
        File file = new File(outputDir, PARSER_NAME + ".java");
        file.getParentFile().mkdirs();
        PrintWriter out = new PrintWriter(file);

        out.println(header);

        out.println("\nimport java.io.InputStream;");
        out.println("import java.text.ParseException;");
        out.println("import java.io.IOException;");
        out.println("import java.util.ArrayList;");
        out.println("import java.util.List;\n");
        out.println("import laba4.Tree;\n");

        out.println("public class " + PARSER_NAME + " {");
        out.println("\tprivate " + LEXER_NAME + " lex;");

        out.println(concat("\t", members));

        out.println("\tpublic " + start.getReturnType() + " parse(InputStream is" + (start.getDeclAttrs(true).isEmpty() ? "" : ", " + start.getDeclAttrs(true)) + ") throws ParseException, IOException {");
        out.println("\t\tlex = new " + LEXER_NAME + "(is);");
        out.println("\t\tlex.nextToken();");
        out.println("\t\t" + (start.getReturnType().equals("void") ? "" : "return ") + start.getName() + "(" + start.getDeclAttrs(false) + ");");
        out.println("\t}\n");

        for (String nonTerm : nonTerminals.keySet()) {
            out.println("\tprivate " + getNonTerm(nonTerm).getReturnType() + " " + nonTerm + "(" + getNonTerm(nonTerm).getDeclAttrs(true) + ") throws ParseException, IOException {");
            out.println("\t\tswitch (lex.curToken()) {");

            Set<String> set = new HashSet<>(first.get(nonTerm));
            if (set.contains(EPS)) {
                set.addAll(follow.get(nonTerm));
            }
            set.remove(EPS);

            for (String term : set) {
                out.println("\t\t\tcase " + term + ": {");

                boolean ret = false;
                int suitableProds = 0;
                Set<String> rules = new HashSet<>();
                for (Production production : nonTerminals.get(nonTerm).getProductionList()) {
                    if (suitableProds == 0 && production.get(0).getName().equals(EPS)) {
                        if (!production.getJavaCode().isEmpty()) {
                            out.println(concat("\t\t\t\t", production.getJavaCode()));
                            ret = true;
                        }
                    } else if (first.get(production.get(0).getName()).contains(term)) {
                        for (Node node : production.getNodes()) {
                            String name = node.getName();
                            if (!rules.contains(node.getName())) {
                                if (nonTerminals.containsKey(name) && !getNonTerm(name).getReturnType().equals("void")) {
                                    out.println("\t\t\t\tList<" + getNonTerm(name).getReturnType() + "> " + name + " = new ArrayList<>();");
                                    rules.add(node.getName());
                                } else if (terminals.containsKey(name)) {
                                    out.println("\t\t\t\tList<String> " + name + " = new ArrayList<>();");
                                    rules.add(name);
                                }
                            }
                        }

                        suitableProds++;

                        for (Node node : production.getNodes()) {
                            String name = node.getName();
                            if (terminals.containsKey(node.getName())) {
                                out.println("\t\t\t\tif (lex.curToken().toString().equals(\"" + name + "\")) {");
                                out.println("\t\t\t\t\t" + name + ".add(lex.curString());");
                                out.println("\t\t\t\t} else {");
                                out.println("\t\t\t\t\tthrow new AssertionError(\"" + name + " expected, instead of \" + lex.curToken().toString());");
                                out.println("\t\t\t\t}");
                                out.println("\t\t\t\tlex.nextToken();");
                            } else if (nonTerminals.containsKey(name) && !getNonTerm(name).getReturnType().equals("void")) {
                                out.print(String.format("\t\t\t\t" + name + ".add(" + name + "(" + node.getCallAttrs() + "));\n", name));
                            } else {
                                out.println("\t\t\t\t" + name + "(" + node.getCallAttrs() + ");");
                            }
                        }
                        if (!production.getJavaCode().isEmpty()) {
                            out.println(concat("\t\t\t\t", production.getJavaCode()));
                            ret = true;
                        }
                    }
                }

                if (suitableProds > 1) {
                    throw new AssertionError(String.format("Ambigous rule: %s", nonTerm));
                } else {
                    if (getNonTerm(nonTerm).getReturnType().equals("String"))
                        out.print(
                                (ret ? "" : "\t\t\t\treturn \"\";\n") +
                                        "\t\t\t}\n");
                    else
                        out.print(
                                (ret ? "" : "\t\t\t\treturn;\n") +
                                        "\t\t\t}\n");
                }
            }

            out.print(
                    "\t\t\tdefault:\n" +
                            "\t\t\t\tthrow new AssertionError();\n" +
                            "\t\t}\n" +
                            "\t}\n\n"
            );
        }

        out.print("}\n");
        out.close();
    }

    private List<String> refactorCallAttrs(GrammarParser.CallAttrsContext context) {
        List<String> codes = new ArrayList<>(context.CODE().size());
        for (TerminalNode code : context.CODE()) {
            codes.add(refactorCode(code));
        }
        return codes;
    }

    private String refactorCode(TerminalNode javaCodeNode) {
        String content = javaCodeNode.getText().trim();
        if (content.length() == 0) {
            return "";
        }
        if (content.charAt(0) == '{' && content.charAt(content.length() - 1) == '}') {
            return content.substring(1, content.length() - 1).trim();
        }
        return content;
    }

    private String concat(String prefix, String content) {
        StringBuilder builder = new StringBuilder();

        for (String s : content.split("\\n")) {
            builder.append(prefix);
            builder.append(s);
        }

        return builder.toString();
    }

    private void generateTokens() throws IOException {
        File file = new File(outputDir, "Token.java");
        file.getParentFile().mkdirs();
        PrintWriter out = new PrintWriter(file);

        out.println(header);

        out.print("public enum Token {\n    ");
        List<String> tokenNames = new ArrayList<>(terminals.keySet());
        for (int i = 0; i < tokenNames.size(); i++) {
            out.print(tokenNames.get(i) + (i != tokenNames.size() - 1 ? ", " : ""));
        }
        out.println("\n}");
        out.close();
    }

    private void computeFirst() {
        for (String name : terminals.keySet()) {
            HashSet<String> set = new HashSet<>();
            set.add(name);
            first.put(name, set);
        }
        for (String name : nonTerminals.keySet()) {
            first.put(name, new HashSet<>());
            for (Production production : nonTerminals.get(name).getProductionList()) {
                if (production.get(0).getName().equals(EPS)) {
                    first.get(name).add(EPS);
                }
            }
        }

        boolean changed;
        do {
            changed = false;
            for (String name : nonTerminals.keySet()) {
                for (Production production : nonTerminals.get(name).getProductionList()) {
                    for (int i = 0; i < production.size(); i++) {
                        String label = production.get(i).getName();
                        if (first.get(label).contains(EPS)) {
                            for (String cur : first.get(label)) {
                                if (!first.get(name).contains(cur)) {
                                    first.get(name).add(cur);
                                    changed = true;
                                }
                            }
                            if (i == production.size() - 1) {
                                if (!first.get(name).contains(EPS)) {
                                    first.get(name).add(EPS);
                                    changed = true;
                                }
                            }
                        } else {
                            for (String cur : first.get(label)) {
                                if (!first.get(name).contains(cur)) {
                                    first.get(name).add(cur);
                                    changed = true;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        } while (changed);
    }

    private void computeFollow() {
        for (String name : nonTerminals.keySet()) {
            follow.put(name, new HashSet<>());
        }

        follow.get(start.getName()).add(EOF);

        boolean changed;
        do {
            changed = false;
            for (String name : nonTerminals.keySet()) {
                for (Production production : nonTerminals.get(name).getProductionList()) {
                    for (int i = 0; i < production.size() - 1; i++) {
                        if (nonTerminals.containsKey(production.get(i).getName())) {
                            for (String cur : first.get(production.get(i + 1).getName())) {
                                if (!cur.equals(EPS) && !follow.get(production.get(i).getName()).contains(cur)) {
                                    follow.get(production.get(i).getName()).add(cur);
                                    changed = true;
                                }
                            }
                        }
                    }
                    int i = production.size() - 1;
                    if (nonTerminals.containsKey(production.get(i).getName())) {
                        for (String cur : follow.get(name)) {
                            if (!cur.equals(EPS) && !follow.get(production.get(i).getName()).contains(cur)) {
                                follow.get(production.get(i).getName()).add(cur);
                                changed = true;
                            }
                        }
                    }
                    if (production.size() > 1 && first.get(production.get(production.size() - 1).getName()).contains(EPS)) {
                        i = production.size() - 2;
                        if (nonTerminals.containsKey(production.get(i).getName())) {
                            for (String cur : follow.get(production.get(i + 1).getName())) {
                                if (!cur.equals(EPS) && !follow.get(production.get(i).getName()).contains(cur)) {
                                    follow.get(production.get(i).getName()).add(cur);
                                    changed = true;
                                }
                            }
                        }
                    }
                }
            }
        } while (changed);
    }

    public void generateFiles() throws IOException {
        generateTokens();
        generateLexer();
        computeFirst();
        computeFollow();

        System.out.println("FIRST set:");
        printData(first);
        System.out.println("FOLLOW set:");
        printData(follow);

        generateParser();
        generateMainFile();
        createMiscFiles();
    }
}
