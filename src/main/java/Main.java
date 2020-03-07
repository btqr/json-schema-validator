import parser.GrammarChecker;
import token.Token;
import tokenizer.Tokenizer;
import tokenizer.TokenizerImpl;
import tokenizer.UnrecognizedSymbolException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Main {
    public static void main(String[] args) throws Exception {
        String exampleJsonScheme = Files.readString(Path.of("example_json_schema.json"));
        Tokenizer tokenizer = new TokenizerImpl();
        GrammarChecker grammarChecker = new GrammarChecker();

        try {
            List<Token> tokenList = tokenizer.apply(exampleJsonScheme);
            System.out.println(grammarChecker.checkGrammar(tokenList));
        } catch(UnrecognizedSymbolException ex) {
            System.err.print(ex.getMessage());
        }

    }
}