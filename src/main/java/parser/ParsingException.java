package parser;

import token.Token;

import java.util.List;
import java.util.stream.Collectors;

public class ParsingException extends RuntimeException {

    public ParsingException(List<Token> invalidTokens) {
        super("Cannot parse expression " + tokensToOriginalCode(invalidTokens) + " at line " + invalidTokens.get(0).getLine());
    }

    private static String tokensToOriginalCode(List<Token> invalidTokens) {
        int lineOfFirstToken = invalidTokens.get(0).getLine();
        return invalidTokens.stream()
                .filter(t -> t.getLine() == lineOfFirstToken)
                .map(Token::getReplacedString)
                .collect(Collectors.joining("", "\"", "\""));
    }
}
