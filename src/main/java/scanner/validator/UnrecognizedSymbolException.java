package scanner.validator;

public class UnrecognizedSymbolException extends Exception{
    public UnrecognizedSymbolException(String symbol) {
        super("Unrecozniged symbol while tokenizing: " + symbol);
    }
}
