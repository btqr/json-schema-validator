package tokenizer;

public class UnrecognizedSymbolException extends Exception{
    public UnrecognizedSymbolException(String symbol, int lineNumber) {
        super("Unrecozniged symbol \"" + symbol + "\" at line " + lineNumber);
    }
}
