import parser.GrammarChecker;
import parser.GrammarCheckerImpl;
import parser.ParsingException;
import token.Token;
import tokenizer.Tokenizer;
import tokenizer.TokenizerImpl;
import tokenizer.UnrecognizedSymbolException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class Main {

    public static void main(String[] args) {

        Tokenizer tokenizer = new TokenizerImpl();
        GrammarChecker grammarChecker = new GrammarCheckerImpl();

        try {
            String exampleJsonScheme = Files.readString(Path.of(args[0]));
            List<Token> tokenList = tokenizer.tokenize(exampleJsonScheme);
            grammarChecker.checkGrammar(tokenList);
            System.out.println("Validation successful.");
        } catch (UnrecognizedSymbolException | ParsingException ex) {
            System.err.print(ex.getMessage());
        } catch (IOException ex) {
            System.err.print("Couldn't read file given as argument" + args[0] + ".");
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.print("Path of file to validate is missing. Please give valid file path as argument.");
        }

    }
}