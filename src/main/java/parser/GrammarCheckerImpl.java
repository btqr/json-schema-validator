package parser;

import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class GrammarCheckerImpl implements GrammarChecker {

    private List<Token> tokens;

    public void checkGrammar(List<Token> tokens) throws ParsingException {
        this.tokens = tokens;
        checkFile(tokens);
    }

    private boolean checkFile(List<Token> tokens) {
        if (checkTokens(List.of(this::checkOpenBrace, this::checkBody, this::checkCloseBrace), tokens)) {
            return true;
        }
        throw new ParsingException(tokens, this.tokens);
    }

    private boolean checkBody(List<Token> tokens) {
        List<List<Token>> copies = List.of(new ArrayList<>(tokens), new ArrayList<>(tokens));
        boolean correctGrammar = false;

        if (checkFirstAlternativeForBody(copies.get(0))) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(0));
        }
        if (checkSecondAlternativeForBody(copies.get(1)) && copies.get(1).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(1));
        }

        return correctGrammar;
    }

    private boolean checkFirstAlternativeForBody(List<Token> tokens) {
        return checkEntry(tokens);
    }

    private boolean checkSecondAlternativeForBody(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        boolean correctGrammar = true;
        boolean allCorrect = false;
        while (correctGrammar) {
            if (checkEntry(copyOfTokenList)) {
                allCorrect = true;
                tokens.clear();
                tokens.addAll(copyOfTokenList);
                if (checkTerminalToken(copyOfTokenList, TokenType.COMMA)) {
                    allCorrect = false;
                    tokens.clear();
                    tokens.addAll(copyOfTokenList);
                } else {
                    correctGrammar = false;
                }
            } else {
                correctGrammar = false;
            }
        }
        return allCorrect;
    }

    private boolean checkEntry(List<Token> tokens) {
        List<Token> copy = new ArrayList<>(tokens);
        boolean correctGrammar = false;

        List<Predicate<List<Token>>> entryCheckers = List.of(this::checkIdEntry, this::checkSchemaEntry, this::checkTitleEntry,
                this::checkRequiredEntry, this::checkTypeEntry, this::checkPropertiesEntry, this::checkStringEntry,
                this::checkDefinitionEntry, this::checkDescriptionEntry, this::checkEnumEntry, this::checkMinimumEntry,
                this::checkMaximumEntry, this::checkRefEntry, this::checkMinLength, this::checkMaxLength);

        for (Predicate<List<Token>> checker : entryCheckers) {
            List<Token> localCopy = new ArrayList<>(tokens);
            if (checker.test(localCopy) && localCopy.size() < copy.size()) {
                correctGrammar = true;
                tokens.clear();
                tokens.addAll(localCopy);
            }
        }

        return correctGrammar;
    }

    private boolean checkCloseBrace(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.CLOSE_BRACE);
    }

    private boolean checkOpenBrace(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.OPEN_BRACE);
    }

    private boolean checkRequiredKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.REQUIRED_KW);
    }

    private boolean checkTitleKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.TITLE_KW);
    }

    private boolean checkDefinitionsKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.DEFINITIONS_KW);
    }

    private boolean checkSchemaKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.SCHEMA_KW);
    }

    private boolean checkIdKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.ID_KW);
    }

    private boolean checkTypeKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.TYPE_KW);
    }

    private boolean checkDescriptionKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.DESCRIPTION_KW);
    }

    private boolean checkPropertiesKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.PROPERTIES_KW);
    }

    private boolean checkEnumKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.ENUM_KW);
    }

    private boolean checkMinimumKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.MINIMUM_KW);
    }

    private boolean checkMaximumKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.MAXIMUM_KW);
    }

    private boolean checkMinLengthKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.MIN_LENGTH_KW);
    }

    private boolean checkMaxLengthKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.MAX_LENGTH_KW);
    }

    private boolean checkRefKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.REF_KW);
    }

    private boolean checkColonKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.COLON);
    }

    private boolean checkStringKeyword(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.STRING_KW);
    }

    private boolean checkString(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.STRING);
    }

    private boolean checkOpenBracket(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.OPEN_BRACKET);
    }

    private boolean checkCloseBracket(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.CLOSE_BRACKET);
    }

    private boolean checkMaxLength(List<Token> tokens) {
        return checkTokens(List.of(this::checkMaxLengthKeyword, this::checkColonKeyword, this::checkNumber), tokens);
    }

    private boolean checkMinLength(List<Token> tokens) {
        return checkTokens(List.of(this::checkMinLengthKeyword, this::checkColonKeyword, this::checkNumber), tokens);
    }

    private boolean checkRefEntry(List<Token> tokens) {
        return checkTokens(List.of(this::checkRefKeyword, this::checkColonKeyword, this::checkString), tokens);
    }

    private boolean checkMaximumEntry(List<Token> tokens) {
        return checkTokens(List.of(this::checkMaximumKeyword, this::checkColonKeyword, this::checkNumber), tokens);
    }

    private boolean checkMinimumEntry(List<Token> tokens) {
        return checkTokens(List.of(this::checkMinimumKeyword, this::checkColonKeyword, this::checkNumber), tokens);
    }

    private boolean checkEnumEntry(List<Token> tokens) {
        return checkTokens(List.of(this::checkEnumKeyword, this::checkColonKeyword, this::checkStringArray), tokens);
    }

    private boolean checkDescriptionEntry(List<Token> tokens) {
        return checkTokens(List.of(this::checkDescriptionKeyword, this::checkColonKeyword, this::checkString), tokens);
    }

    private boolean checkTypeEntry(List<Token> tokens) {
        return checkTokens(List.of(this::checkTypeKeyword, this::checkColonKeyword, this::checkTypeBody), tokens);
    }

    private boolean checkDefinitionEntry(List<Token> tokens) {
        return checkTokens(List.of(this::checkDefinitionsKeyword, this::checkColonKeyword, this::checkFile), tokens);
    }

    private boolean checkIdEntry(List<Token> tokens) {
        return checkTokens(List.of(this::checkIdKeyword, this::checkColonKeyword, this::checkString), tokens);
    }

    private boolean checkSchemaEntry(List<Token> tokens) {
        return checkTokens(List.of(this::checkSchemaKeyword, this::checkColonKeyword, this::checkString), tokens);
    }

    private boolean checkTitleEntry(List<Token> tokens) {
        return checkTokens(List.of(this::checkTitleKeyword, this::checkColonKeyword, this::checkString), tokens);
    }

    private boolean checkRequiredEntry(List<Token> tokens) {
        return checkTokens(List.of(this::checkRequiredKeyword, this::checkColonKeyword, this::checkStringArray), tokens);
    }

    private boolean checkStringArray(List<Token> tokens) {
        return checkTokens(List.of(this::checkOpenBracket, this::checkMultipleStrings, this::checkCloseBracket), tokens);
    }

    private boolean checkStringEntry(List<Token> tokens) {
        return checkTokens(List.of(this::checkString, this::checkColonKeyword, this::checkFile), tokens);
    }

    private boolean checkPropertiesEntry(List<Token> tokens) {
        return checkTokens(List.of(this::checkPropertiesKeyword, this::checkColonKeyword, this::checkFile), tokens);
    }

    private boolean checkTypeBody(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.OBJECT_KW) || checkTerminalToken(tokens, TokenType.INTEGER_KW)
                || checkTerminalToken(tokens, TokenType.NUMBER_KW) || checkTerminalToken(tokens, TokenType.STRING_KW)
                || checkTerminalToken(tokens, TokenType.NULL_KW) || checkTerminalToken(tokens, TokenType.ARRAY_KW)
                || checkTerminalToken(tokens, TokenType.BOOLEAN_KW);
    }

    private boolean checkMultipleStrings(List<Token> tokens) {
        List<List<Token>> copies = List.of(new ArrayList<>(tokens), new ArrayList<>(tokens));
        boolean correctGrammar = false;

        if (checkFirstAlternativeForMultipleStrings(copies.get(0))) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(0));
        }

        if (checkSecondAlternativeForMultipleStrings(copies.get(1)) && copies.get(1).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(1));
        }

        return correctGrammar;
    }

    private boolean checkNumber(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        boolean correctGrammar = true;
        boolean anyCorrect = false;

        while (correctGrammar) {
            if (checkTerminalToken(copyOfTokenList, TokenType.DIGIT)) {
                anyCorrect = true;
                tokens.clear();
                tokens.addAll(copyOfTokenList);
            } else {
                correctGrammar = false;
            }
        }
        return anyCorrect;
    }

    private boolean checkFirstAlternativeForMultipleStrings(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.STRING);
    }

    private boolean checkSecondAlternativeForMultipleStrings(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        boolean correctGrammar = true;
        boolean allCorrect = false;
        while (correctGrammar) {
            if (checkTerminalToken(copyOfTokenList, TokenType.STRING)) {
                allCorrect = true;
                tokens.clear();
                tokens.addAll(copyOfTokenList);
                if (checkTerminalToken(copyOfTokenList, TokenType.COMMA)) {
                    allCorrect = false;
                    tokens.clear();
                    tokens.addAll(copyOfTokenList);
                } else {
                    correctGrammar = false;
                }
            } else {
                correctGrammar = false;
            }
        }
        return allCorrect;
    }

    private boolean checkTokens(List<Predicate<List<Token>>> checkers, List<Token> tokens) {
        List<Token> copy = new ArrayList<>(tokens);
        boolean correctGrammar = checkers.stream().allMatch(checker -> checker.test(copy));
        if (correctGrammar) {
            tokens.clear();
            tokens.addAll(copy);
        }
        return correctGrammar;
    }

    private boolean checkTerminalToken(List<Token> tokens, TokenType tokenType) {
        if (!tokens.isEmpty() && tokens.get(0).getTokenType() == tokenType) {
            tokens.remove(0);
            return true;
        }
        return false;
    }
}
