package tokenizer;

import org.junit.BeforeClass;
import org.junit.Test;
import token.Token;
import token.TokenType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenizerTest {

    private static Tokenizer tokenizer;

    @BeforeClass
    public static void setUp() {
        tokenizer = new TokenizerImpl();
    }

    @Test
    public void shouldTokenizeIdEntry() throws UnrecognizedSymbolException {
        // given
        String json = "{ \"$id\":\"example_id\"}";

        // when
        List<Token> tokens = tokenizer.tokenize(json);

        // then
        assertThat(tokens.size()).isEqualTo(5);
        assertThat(tokens.get(1).getReplacedString()).isEqualTo("\"$id\"");
        assertThat(tokens.get(1).getTokenType()).isEqualTo(TokenType.ID_KW);
        assertThat(tokens.get(3).getReplacedString()).isEqualTo("\"example_id\"");
        assertThat(tokens.get(3).getTokenType()).isEqualTo(TokenType.STRING);
    }

    @Test(expected = UnrecognizedSymbolException.class)
    public void shouldNotTokenizeIdEntryBecauseOfNoQuotationMark() throws UnrecognizedSymbolException {
        // given
        String json = "{ \"$id\":example_id }";

        // when
        List<Token> tokens = tokenizer.tokenize(json);

        // then
        // should throw Exception because of no ' " ' character
    }

    @Test(expected = UnrecognizedSymbolException.class)
    public void shouldNotTokenizeIdEntryBecauseOfInvalidSymbolInStringRegex() throws UnrecognizedSymbolException {
        // given
        String json = "{ \"$id\":\"example_id\\\uFEFF\" }";

        // when
        List<Token> tokens = tokenizer.tokenize(json);

        // then
        // should throw Exception because of uFEFF character
    }

}
