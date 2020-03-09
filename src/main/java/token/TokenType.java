package token;

public enum TokenType {
    ID_KW("<ID_KW>", "\"\\$id\""),
    SCHEMA_KW("<SCHEMA_KW>", "\"\\$schema\""),
    REF_KW("<REF_KW>", "\"\\$ref\""),
    TITLE_KW("<TITLE_KW>", "\"title\""),
    REQUIRED_KW("<REQUIRED_KW>", "\"required\""),
    TYPE_KW("<TYPE_KW>", "\"type\""),
    OBJECT_KW("<OBJECT_KW>", "\"object\""),
    INTEGER_KW("<INTEGER_KW>", "\"integer\""),
    NUMBER_KW("<NUMBER_KW>", "\"number\""),
    STRING_KW("<STRING_KW>", "\"string\""),
    NULL_KW("<NULL_KW>","\"null\""),
    ARRAY_KW("<ARRAY_KW>", "\"array\""),
    PROPERTIES_KW("<PROPERTIES_KW>", "\"properties\""),
    DESCRIPTION_KW("<DESCRIPTION_KW>","\"description\""),
    MINIMUM_KW("<MINIMUM_KW>", "\"minimum\""),
    MAXIMUM_KW("<MAXIMUM_KW>", "\"maximum\""),
    MIN_LENGTH_KW("<MIN_LENGTH_KW>", "\"minLength\""),
    MAX_LENGTH_KW("<MAX_LENGTH_KW>", "\"maxLength\""),
    ENUM_KW("<ENUM_KW>", "\"enum\""),
    DEFINITIONS_KW("<DEFINITIONS_KW>", "\"definitions\""),
    OPEN_BRACE("<OPEN_BRACE>", "\\{"),
    CLOSE_BRACE("<CLOSE_BRACE>", "\\}"),
    COLON("<COLON>", "\\:"),
    OPEN_BRACKET("<OPEN_BRACKET>", "\\["),
    CLOSE_BRACKET("<CLOSE_BRACKET>", "\\]"),
    COMMA("<COMMA>", "\\,"),
    STRING("<STRING>", "\"([^\"\\\\\\\\]*|\\\\\\\\[\"\\\\\\\\bfnrt\\/]|\\\\\\\\u[0-9a-f]{4})*\""),
    DIGIT("<DIGIT>", "^\\d+$"),
    WHITE_SIGN(null, "\\s+");

    private String textRepresentation;
    private String regex;

    TokenType(String textRepresentation, String regex) {
        this.textRepresentation = textRepresentation;
        this.regex = regex;
    }

    public String getTextRepresentation() {
        return textRepresentation;
    }

    public String getRegex() {
        return regex;
    }
}
