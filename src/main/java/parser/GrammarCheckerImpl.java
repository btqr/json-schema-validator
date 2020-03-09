package parser;

import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class GrammarCheckerImpl implements GrammarChecker {

    public void checkGrammar(List<Token> tokens) throws ParsingException {
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
        throw new ParsingException(copyOfTokenList);
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
        boolean anyCorrect = false;
        while (correctGrammar) {
            if (checkEntry(copyOfTokenList)) {
                anyCorrect = true;
                tokens.clear();
                tokens.addAll(copyOfTokenList);
                if (checkTerminalToken(copyOfTokenList, TokenType.COMMA)) {
                    tokens.clear();
                    tokens.addAll(copyOfTokenList);
                } else {
                    correctGrammar = false;
                }
            } else {
                correctGrammar = false;
            }
        }
        return anyCorrect;
    }

    private boolean checkEntry(List<Token> tokens) {
        List<List<Token>> copies = List.of(new ArrayList<>(tokens), new ArrayList<>(tokens), new ArrayList<>(tokens), new ArrayList<>(tokens),
                new ArrayList<>(tokens), new ArrayList<>(tokens), new ArrayList<>(tokens), new ArrayList<>(tokens), new ArrayList<>(tokens),
                new ArrayList<>(tokens), new ArrayList<>(tokens), new ArrayList<>(tokens), new ArrayList<>(tokens),
                new ArrayList<>(tokens), new ArrayList<>(tokens));

        boolean correctGrammar = false;

        if (checkIdEntry(copies.get(0))) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(0));
        }
        if (checkSchemaEntry(copies.get(1)) && copies.get(1).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(1));
        }
        if (checkTitleEntry(copies.get(2)) && copies.get(2).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(2));
        }
        if (checkRequiredEntry(copies.get(3)) && copies.get(3).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(3));
        }
        if (checkTypeEntry(copies.get(4)) && copies.get(4).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(4));
        }
        if (checkPropertiesEntry(copies.get(5)) && copies.get(5).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(5));
        }
        if (checkStringEntry(copies.get(6)) && copies.get(6).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(6));
        }
        if (checkDefinitionEntry(copies.get(7)) && copies.get(7).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(7));
        }
        if (checkDescriptionEntry(copies.get(8)) && copies.get(8).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(8));
        }
        if (checkEnumEntry(copies.get(9)) && copies.get(9).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(9));
        }
        if (checkMinimumEntry(copies.get(10)) && copies.get(10).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(10));
        }
        if (checkMaximumEntry(copies.get(11)) && copies.get(11).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(11));
        }
        if (checkRefEntry(copies.get(12)) && copies.get(12).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(12));
        }
        if (checkMinLength(copies.get(13)) && copies.get(13).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(13));
        }
        if (checkMaxLength(copies.get(14)) && copies.get(14).size() < tokens.size()) {
            correctGrammar = true;
            tokens.clear();
            tokens.addAll(copies.get(14));
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
                || checkTerminalToken(tokens, TokenType.NULL_KW) || checkTerminalToken(tokens, TokenType.ARRAY_KW);
    }

    private boolean checkIdEntry(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.ID_KW) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkTerminalToken(copyOfTokenList, TokenType.STRING)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
    }

    private boolean checkSchemaEntry(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.SCHEMA_KW) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkTerminalToken(copyOfTokenList, TokenType.STRING)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
    }

    private boolean checkTitleEntry(List<Token> tokens) {
        List<Token> copyOfTokenList = new ArrayList<>(tokens);
        if (checkTerminalToken(copyOfTokenList, TokenType.TITLE_KW) && checkTerminalToken(copyOfTokenList, TokenType.COLON)
                && checkTerminalToken(copyOfTokenList, TokenType.STRING)) {
            tokens.clear();
            tokens.addAll(copyOfTokenList);
            return true;
        }
        return false;
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
        boolean anyCorrect = false;
        while (correctGrammar) {
            if (checkTerminalToken(copyOfTokenList, TokenType.STRING)) {
                anyCorrect = true;
                tokens.clear();
                tokens.addAll(copyOfTokenList);
                if (checkTerminalToken(copyOfTokenList, TokenType.COMMA)) {
                    tokens.clear();
                    tokens.addAll(copyOfTokenList);
                } else {
                    correctGrammar = false;
                }
            } else {
                correctGrammar = false;
            }
        }
        return anyCorrect;
    }

    private boolean checkTerminalToken(List<Token> tokens, TokenType tokenType) {
        if (!tokens.isEmpty() && tokens.get(0).getTokenType() == tokenType) {
            tokens.remove(0);
            return true;
        }
        return false;
    }
}
