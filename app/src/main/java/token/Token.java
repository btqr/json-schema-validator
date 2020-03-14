package token;

public class Token {
    private TokenType tokenType;
    private int line;
    private String replacedString;

    public Token(TokenType tokenType, int line, String replacedString) {
        this.tokenType = tokenType;
        this.line = line;
        this.replacedString = replacedString;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public int getLine() {
        return line;
    }

    public String getReplacedString() {
        return replacedString;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", line=" + line +
                ", replacedString='" + replacedString + '\'' +
                '}';
    }
}
