package tokenizer;

import org.apache.commons.text.StringEscapeUtils;

public class UnrecognizedSymbolException extends Exception{

    UnrecognizedSymbolException(String symbol, int lineNumber) {
        super(String.format("Unrecognized symbol: %s at line %d (with escaped: %s)",
                symbol, lineNumber, StringEscapeUtils.escapeJava(symbol)));
    }

}
