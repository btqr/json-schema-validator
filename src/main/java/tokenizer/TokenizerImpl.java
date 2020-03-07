package tokenizer;

import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class TokenizerImpl implements Tokenizer {
    @Override
    public List<Token> apply(String inputText) throws UnrecognizedSymbolException {
        List<Token> tokens = new ArrayList<>();
        String[] lines = inputText.split("\n");
        int lineNumber = 0;

        for (String line : lines) {
            lineNumber++;
            convertWordToToken(tokens, line, lineNumber);
        }

        return tokens;
    }

    private void convertWordToToken(List<Token> tokens, String word, int lineNumber) throws UnrecognizedSymbolException {
        for(int i = 0; i <= word.length(); i++) {
            String partOfWord = word.substring(0, i);

            for(TokenType tokenType : TokenType.values()) {
                if (partOfWord.matches(tokenType.getRegex())) {
                    if(!tokenType.name().equals("WHITE_SIGN")) {
                        tokens.add(new Token(tokenType, lineNumber, partOfWord));
                    }
                    word = word.substring(i, word.length());

                    if (word.length() != 0) {
                        convertWordToToken(tokens, word, lineNumber);
                        return;
                    }
                }
            }
        }

        if(word.length() == 0) {
            return;
        }

        throw new UnrecognizedSymbolException(word.replaceAll("\\s+", ""), lineNumber);
    }

}
