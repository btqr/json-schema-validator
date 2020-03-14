package parser;

import org.junit.BeforeClass;
import org.junit.Test;
import tokenizer.Tokenizer;
import tokenizer.TokenizerImpl;
import tokenizer.UnrecognizedSymbolException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ParserTest {

    private static GrammarChecker grammarChecker;
    private static Tokenizer tokenizer;


    @BeforeClass
    public static void setUp() {
        grammarChecker = new GrammarCheckerImpl();
        tokenizer = new TokenizerImpl();
    }

    @Test
    public void shouldParseExampleFileCorrectly() throws IOException, UnrecognizedSymbolException {
        // given
        String exampleJson = Files.readString(Path.of("example_json_schema.json"));

        // when
        grammarChecker.checkGrammar(tokenizer.tokenize(exampleJson));

        // then
        // exception wont be thrown
    }

    @Test
    public void shouldParseOtherExampleFilesCorrectly() throws IOException, UnrecognizedSymbolException {
        // given
        String example1 = Files.readString(Path.of("schema_example1.json"));
        String example2 = Files.readString(Path.of("schema_example2.json"));
        String example3 = Files.readString(Path.of("schema_example3.json"));
        String example4 = Files.readString(Path.of("schema_example4.json"));

        // when
        grammarChecker.checkGrammar(tokenizer.tokenize(example1));
        grammarChecker.checkGrammar(tokenizer.tokenize(example2));
        grammarChecker.checkGrammar(tokenizer.tokenize(example3));
        grammarChecker.checkGrammar(tokenizer.tokenize(example4));

        // then
        // exception wont be thrown
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
