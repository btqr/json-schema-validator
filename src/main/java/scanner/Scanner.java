package scanner;
import scanner.tokenizer.KeywordTokenizer;
import scanner.tokenizer.RegexTokenizer;
import scanner.tokenizer.SymbolTokenizer;
import scanner.tokenizer.Tokenizer;
import scanner.validator.TokenizedValidator;
import scanner.validator.TokenizedValidatorImpl;
import scanner.validator.UnrecognizedSymbolException;

import java.util.List;

public class Scanner {

    private List<Tokenizer> tokenizers;
    private TokenizedValidator validator;

    public Scanner() {
        tokenizers = List.of(new KeywordTokenizer(), new SymbolTokenizer(), new RegexTokenizer());
        validator = new TokenizedValidatorImpl();
    }

    public String process(String inputText) throws UnrecognizedSymbolException {
        String tokenizedText = tokenize(inputText);
        validator.validate(tokenizedText);
        return tokenizedText;
    }

    public String tokenize(String inputText) {
        String output = inputText;
        for (Tokenizer tokenizer : tokenizers) {
            output = tokenizer.apply(output);
        }
        return output;
    }
}
