package token;

public enum TokenType {
    ID_KW("\"\\$id\""),
    SCHEMA_KW("\"\\$schema\""),
    REF_KW("\"\\$ref\""),
    TITLE_KW("\"title\""),
    REQUIRED_KW("\"required\""),
    TYPE_KW("\"type\""),
    OBJECT_KW("\"object\""),
    INTEGER_KW("\"integer\""),
    NUMBER_KW("\"number\""),
    STRING_KW("\"string\""),
    NULL_KW("\"null\""),
    ARRAY_KW("\"array\""),
    BOOLEAN_KW("\"boolean\""),
    PROPERTIES_KW("\"properties\""),
    DESCRIPTION_KW("\"description\""),
    MINIMUM_KW("\"minimum\""),
    MAXIMUM_KW("\"maximum\""),
    MIN_LENGTH_KW("\"minLength\""),
    MAX_LENGTH_KW("\"maxLength\""),
    ENUM_KW("\"enum\""),
    DEFINITIONS_KW("\"definitions\""),
    OPEN_BRACE("\\{"),
    CLOSE_BRACE("\\}"),
    COLON("\\:"),
    OPEN_BRACKET("\\["),
    CLOSE_BRACKET("\\]"),
    COMMA("\\,"),
    STRING("\"([^\"\\\\\\\\]*|\\\\\\\\[\"\\\\\\\\bfnrt\\/]|\\\\\\\\u[0-9a-f]{4})*\""),
    NUMBER("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?"),
    WHITE_SIGN("\\s+");

    private String regex;

    TokenType(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
