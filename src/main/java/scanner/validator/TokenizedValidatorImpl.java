package scanner.validator;
public class TokenizedValidatorImpl implements TokenizedValidator{

    private static final String VALID_WORD_REGEX = "<.*?>";

    public void validate(String tokenizedText) throws UnrecognizedSymbolException {
        String validated = tokenizedText.replaceAll(VALID_WORD_REGEX, "");
        if (validated.length() != 0) {
            throw new UnrecognizedSymbolException(validated);
        }
    }
}
