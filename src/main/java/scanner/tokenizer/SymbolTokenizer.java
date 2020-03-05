package scanner.tokenizer;

import java.util.AbstractMap;
import java.util.Map;

public class SymbolTokenizer implements Tokenizer{

    private final static Map<String, String> SYMBOL_MAP = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("\\{", "<OPEN_BRACE>"),
            new AbstractMap.SimpleEntry<>("\\}", "<CLOSE_BRACE>"),
            new AbstractMap.SimpleEntry<>("\\:", "<COLON>"),
            new AbstractMap.SimpleEntry<>("\\[", "<OPEN_BRACKET>"),
            new AbstractMap.SimpleEntry<>("\\]", "<CLOSE_BRACKET>"),
            new AbstractMap.SimpleEntry<>("\\,", "<COMMA>")
    );

    @Override
    public String apply(String inputText) {
        String output = inputText;
        for(Map.Entry<String, String> keyword : SYMBOL_MAP.entrySet()) {
            output = output.replaceAll(keyword.getKey(), keyword.getValue());
        }
        return output;
    }
}
