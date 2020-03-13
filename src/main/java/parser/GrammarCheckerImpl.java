package parser;

import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GrammarCheckerImpl implements GrammarChecker {

    private List<Token> tokens;

    public void checkGrammar(List<Token> tokens) throws ParsingException {
        this.tokens = tokens;
        checkFile(tokens);
    }

    private boolean checkFile(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.OPEN_BRACE) && checkBody(copyOfTokenList)
                && checkTerminalToken(copyOfTokenList, TokenType.CLOSE_BRACE)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        throw new ParsingException(copyOfTokenList, this.tokens);
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

    private boolean checkMaxLength(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.MAX_LENGTH_KW) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkNumber(copyOfTokenList)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
    }

    private boolean checkMinLength(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.MIN_LENGTH_KW) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkNumber(copyOfTokenList)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
    }

    private boolean checkRefEntry(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.REF_KW) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkTerminalToken(copyOfTokenList, TokenType.STRING)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
    }

    private boolean checkMaximumEntry(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.MINIMUM_KW) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkNumber(copyOfTokenList)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
    }

    private boolean checkMinimumEntry(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.MINIMUM_KW) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkNumber(copyOfTokenList)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
    }

    private boolean checkEnumEntry(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.ENUM_KW) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkStringArray(copyOfTokenList)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
    }

    private boolean checkDescriptionEntry(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.DESCRIPTION_KW) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkTerminalToken(copyOfTokenList, TokenType.STRING)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
    }

    private boolean checkDefinitionEntry(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.DEFINITIONS_KW) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkFile(copyOfTokenList)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
    }


    private boolean checkStringEntry(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.STRING) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkFile(copyOfTokenList)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
    }

    private boolean checkPropertiesEntry(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.PROPERTIES_KW) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkFile(copyOfTokenList)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
    }

    private boolean checkTypeEntry(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.TYPE_KW) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkTypeBody(copyOfTokenList)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
    }

    private boolean checkTypeBody(List<Token> tokens) {
        return checkTerminalToken(tokens, TokenType.OBJECT_KW) || checkTerminalToken(tokens, TokenType.INTEGER_KW)
                || checkTerminalToken(tokens, TokenType.NUMBER_KW) || checkTerminalToken(tokens, TokenType.STRING_KW)
                || checkTerminalToken(tokens, TokenType.NULL_KW) || checkTerminalToken(tokens, TokenType.ARRAY_KW)
                || checkTerminalToken(tokens, TokenType.BOOLEAN_KW);
    }

    private boolean checkIdEntry(List<Token> tokens) {
        return checkTerminalTokens(tokens, List.of(TokenType.ID_KW, TokenType.COLON, TokenType.STRING));
    }

    private boolean checkSchemaEntry(List<Token> tokens) {
        return checkTerminalTokens(tokens, List.of(TokenType.SCHEMA_KW, TokenType.COLON, TokenType.STRING));
    }

    private boolean checkTitleEntry(List<Token> tokens) {
        return checkTerminalTokens(tokens, List.of(TokenType.TITLE_KW, TokenType.COLON, TokenType.STRING));
    }

    private boolean checkRequiredEntry(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.REQUIRED_KW) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkStringArray(copyOfTokenList)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
    }

    private boolean checkStringArray(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.OPEN_BRACKET) && checkMultipleStrings(copyOfTokenList)
                && checkTerminalToken(copyOfTokenList, TokenType.CLOSE_BRACKET)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
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

    private boolean checkTerminalTokens(List<Token> tokens, List<TokenType> grammar) {
        if (tokens.stream()
                .map(Token::getTokenType)
                .collect(Collectors.toList())
                .subList(0, grammar.size())
                .equals(grammar)) {
            tokens.subList(0, grammar.size()).clear();
            return true;
        }
        return false;
    }

    private boolean checkTerminalToken(List<Token> tokens, TokenType tokenType) {
        if (!tokens.isEmpty() && tokens.get(0).getTokenType() == tokenType) {
            tokens.remove(0);
            return true;
        }
        return false;
    }
}
