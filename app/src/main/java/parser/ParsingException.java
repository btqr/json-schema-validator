package parser;

import org.apache.commons.text.StringEscapeUtils;
import token.Token;

import java.util.List;
import java.util.stream.Collectors;

public class ParsingException extends RuntimeException {

    ParsingException(List<Token> invalidTokens, List<Token> allTokens) {
        super(String.format("Cannot parse expression: %s at line %d (line with escaped: %s)",
                tokensToOriginalCode(invalidTokens, invalidTokens.get(0).getLine()),
                invalidTokens.get(0).getLine(),
                StringEscapeUtils.escapeEcmaScript(tokensToOriginalCode(allTokens, invalidTokens.get(0).getLine()))));
    }

    private static String tokensToOriginalCode(List<Token> tokens, int line) {
        return tokens.stream()
                .filter(t -> t.getLine() == line)
                .map(Token::getReplacedString)
                .collect(Collectors.joining("", "\"", "\""));
    }
}
