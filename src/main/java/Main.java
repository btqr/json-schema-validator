import parser.GrammarChecker;
import parser.GrammarCheckerImpl;
import parser.ParsingException;
import token.Token;
import tokenizer.Tokenizer;
import tokenizer.TokenizerImpl;
import tokenizer.UnrecognizedSymbolException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Main {

    private static final String FILE_PATH = "example_json_schema.json";

    public static void main(String[] args) throws Exception {

        String exampleJsonScheme = Files.readString(Path.of(FILE_PATH));
        Tokenizer tokenizer = new TokenizerImpl();
        GrammarChecker grammarChecker = new GrammarCheckerImpl();

        try {
            List<Token> tokenList = tokenizer.tokenize(exampleJsonScheme);
            grammarChecker.checkGrammar(tokenList);
            System.out.println("Text validation passed.");
        } catch (UnrecognizedSymbolException | ParsingException ex) {
            System.err.print(ex.getMessage());
        }

    }
}