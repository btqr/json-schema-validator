package parser;

import org.junit.BeforeClass;
import org.junit.Test;
import tokenizer.Tokenizer;
import tokenizer.TokenizerImpl;
import tokenizer.UnrecognizedSymbolException;

public class ParserTest {

    private static GrammarChecker grammarChecker;
    private static Tokenizer tokenizer;


    @BeforeClass
    public static void setUp() {
        grammarChecker = new GrammarCheckerImpl();
        tokenizer = new TokenizerImpl();
    }

    @Test(expected = ParsingException.class)
    public void shouldNotParseBecauseOfDoubleComma() throws UnrecognizedSymbolException {
        // given
        String json = "{ \"$id\":\"example_id\",, \"$ref\":\"refref\" }";

        // when
        grammarChecker.checkGrammar(tokenizer.tokenize(json));

        // then
        // should throw an ParseException
    }

    @Test(expected = ParsingException.class)
    public void shouldNotParseBecauseOfCommaAfterLastObject() throws UnrecognizedSymbolException {
        // given
        String json = "{ \"enum\":[\"a\",\"b\",] }";

        // when
        grammarChecker.checkGrammar(tokenizer.tokenize(json));

        // then
        // should throw an ParseException
    }
}
