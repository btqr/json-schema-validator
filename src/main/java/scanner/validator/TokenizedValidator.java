package scanner.validator;

public interface TokenizedValidator {

    /**
     *
     * @param tokenizedText - text after tokenization to be validated if consists with only tokens in order to detect errors
     * @throws UnrecognizedSymbolException - exception when detected symbol which is not known as token
     */
    void validate(String tokenizedText) throws UnrecognizedSymbolException;
}
