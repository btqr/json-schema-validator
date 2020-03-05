package scanner.tokenizer;

import java.util.AbstractMap;
import java.util.Map;

public class RegexTokenizer implements Tokenizer{

    private final static Map<String, String> REGEX_MAP = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("([\"'])(?:(?=(\\\\?))\\2.)*?\\1", "<STRING>"),
            new AbstractMap.SimpleEntry<>("\\b(\\d+)", "<NUMBER>"),
            new AbstractMap.SimpleEntry<>("\\s+", "")
    );

    @Override
    public String apply(String inputText) {
        String output = inputText;
        for(Map.Entry<String, String> keyword : REGEX_MAP.entrySet()) {
            output = output.replaceAll(keyword.getKey(), keyword.getValue());
        }
        return output;
    }
}
