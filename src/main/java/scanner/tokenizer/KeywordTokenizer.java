package scanner.tokenizer;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.*;

public class KeywordTokenizer implements Tokenizer {

    private final static Map<String, String> KEYWORD_MAP = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("\"\\$id\"", "<ID_KW>"),
            new AbstractMap.SimpleEntry<>("\"\\$schema\"", "<SCHEMA_KW>"),
            new AbstractMap.SimpleEntry<>("\"\\$ref\"", "<REF_KW>"),
            new AbstractMap.SimpleEntry<>("\"title\"", "<TITLE_KW>"),
            new AbstractMap.SimpleEntry<>("\"required\"", "<REQUIRED_KW>"),
            new AbstractMap.SimpleEntry<>("\"type\"", "<TYPE_KW>"),
            new AbstractMap.SimpleEntry<>("\"object\"", "<OBJECT_KW>"),
            new AbstractMap.SimpleEntry<>("\"integer\"", "<INTEGER_KW>"),
            new AbstractMap.SimpleEntry<>("\"number\"", "<NUMBER_KW>"),
            new AbstractMap.SimpleEntry<>("\"string\"", "<STRING_KW>"),
            new AbstractMap.SimpleEntry<>("\"null\"", "<NULL_KW>"),
            new AbstractMap.SimpleEntry<>("\"array\"", "<ARRAY_KW>"),
            new AbstractMap.SimpleEntry<>("\"properties\"", "<PROPERTIES_KW>"),
            new AbstractMap.SimpleEntry<>("\"description\"", "<DESCRIPTION_KW>"),
            new AbstractMap.SimpleEntry<>("\"minimum\"", "<MINIMUM_KW>"),
            new AbstractMap.SimpleEntry<>("\"maximum\"", "<MAXIMUM_KW>"),
            new AbstractMap.SimpleEntry<>("\"enum\"", "<ENUM_KW>"),
            new AbstractMap.SimpleEntry<>("\"definitions\"", "<DEFINITIONS_KW>")
    );

    @Override
    public String apply(String inputText) {
        String output = inputText;
        for(Entry<String, String> keyword : KEYWORD_MAP.entrySet()) {
            output = output.replaceAll(keyword.getKey(), keyword.getValue());
        }
        return output;
    }
}
