package parser;

import token.Token;

import java.util.List;

public interface GrammarChecker {

    /**
     * @param tokens - list of tokens received from tokenizer
     * @throws ParsingException - exception thrown if any symbol was not recognized
     */
    void checkGrammar(List<Token> tokens) throws ParsingException;
}
