package lexer;

// From my main project
import lexer.Token.TokenClass;
import static util.Streams.*;

// From JUnit
import static org.junit.Assert.*;
import org.junit.Test;

// From Java Standard Library
import java.io.InputStream;
import java.util.ArrayList;

/***
 * 
 * @author dhil
 *
 */
public class TokeniserTests {

	public Scanner makeScanner(InputStream input) {
		return new Scanner(input);
	}
	
	public Tokeniser makeTokeniser(InputStream input) {
		return new Tokeniser(makeScanner(input));
	}
	
	public ArrayList tokenize(InputStream input) {
		ArrayList<Token> tokens = new ArrayList();	
		Tokeniser tokeniser = makeTokeniser(input);
		
		Token tok = null;
		while ((tok = tokeniser.nextToken()).tokenClass != TokenClass.EOF) {
			tokens.add(tok);
		}
		tokens.add(tok);
		
		return tokens;
	}
	
	public void assertEqualsSequence(ArrayList<Token> expected, ArrayList<Token> actual) {
		assertEquals(expected.size(), actual.size());
		
		for (int i = 0; i < expected.size(); i++)
			assertEquals(expected.get(i).tokenClass, actual.get(i).tokenClass);
	}
	
	@Test
	public void empty() {
		ArrayList<Token> empty  = new ArrayList<Token>() {{
			add(new Token(TokenClass.EOF));
		}};
		ArrayList<Token> tokens = tokenize(toStream(""));
		assertEqualsSequence(empty, tokens);
	}
	
	@Test
	public void lrlr() {
		ArrayList<Token> lrlr = new ArrayList<Token>() {{ 
			add(new Token(TokenClass.LPAR));
			add(new Token(TokenClass.RPAR));
			add(new Token(TokenClass.LPAR));
			add(new Token(TokenClass.RPAR));
			add(new Token(TokenClass.EOF));
		}};
		
		ArrayList<Token> tokens = tokenize(toStream("()()"));
		
		assertEqualsSequence(lrlr, tokens);
	}
	
	@Test
	public void lrc() {
		ArrayList<Token> lr_invalid = new ArrayList<Token>() {{ 
			add(new Token(TokenClass.LPAR));
			add(new Token(TokenClass.RPAR));			
			add(new Token(TokenClass.INVALID));
			add(new Token(TokenClass.EOF));
		}};
		
		ArrayList<Token> tokens = tokenize(toStream("()c"));
		
		assertEqualsSequence(lr_invalid, tokens);
	}

}
