package scanner.tokenizer;

public interface Tokenizer {
    /**
     *
     * @param inputText - input text to be replaced using concrete tokenizer
     * @return text with replaced key words by tokens
     */
    String apply(String inputText);
}
