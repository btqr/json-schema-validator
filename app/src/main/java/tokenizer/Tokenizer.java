package tokenizer;

import token.Token;

import java.util.List;

public interface Tokenizer {
    /**
     *
     * @param inputText - text to be tokenized
     * @return - list of tokens
     * @throws UnrecognizedSymbolException - exception when text is not recognized as valid token
     */
    List<Token> tokenize(String inputText) throws UnrecognizedSymbolException;
}
