package tokenizer;

public class UnrecognizedSymbolException extends Exception{
    public UnrecognizedSymbolException(String symbol, int lineNumber) {
        super("Unrecozniged symbol while tokenizing: " + symbol + " at line " + lineNumber);
    }
}
