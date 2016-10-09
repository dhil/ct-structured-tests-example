package parser;

//From my main project
import lexer.Scanner;
import lexer.Tokeniser;
import parser.Parser;
import static util.Streams.*;

//From JUnit
import static org.junit.Assert.*;
import org.junit.Test;

//From Java Standard Library
import java.io.InputStream;

/**
 * 
 * @author dhil
 *
 */
public class ParserTests {

	public Scanner makeScanner(InputStream input) {
		return new Scanner(input);
	}
	
	public Tokeniser makeTokeniser(InputStream input) {
		return new Tokeniser(makeScanner(input));
	}
	
	public Parser makeParser(InputStream input) {
		return new Parser(makeTokeniser(input));
	}
	
	public int parse(InputStream input) {
		Parser parser = makeParser(input);
		parser.parse();
		return parser.getErrorCount();
	}
	
	@Test
	public void emptyProgram() {
		InputStream empty = toStream("");
		assertEquals(0, parse(empty));
	}
	
	@Test 
	public void lrlr() {
		InputStream lrlr = toStream("()()"); 
		assertEquals(0, parse(lrlr));
	}
	
	@Test
	public void lr() {
		InputStream lr = toStream("()");
		assertEquals(0, parse(lr));
	}
	
	@Test
	public void l() {
		InputStream l = toStream("(");
		assertNotEquals(0, parse(l));
	}
	
	@Test
	public void llrr() {
		InputStream llrr = toStream("(())");
		assertEquals(0, parse(llrr));
	}
	
	@Test
	public void llrrllrr() {
		InputStream llrrllrr = toStream("(())(())");
		assertEquals(0, parse(llrrllrr));
	}
	
	@Test
	public void lllrrr() {
		InputStream lllrrr = toStream("((()))");
		assertEquals(0, parse(lllrrr));
	}
	
	@Test
	public void llllrrr() {
		InputStream llllrrr = toStream("(((()))");
		assertNotEquals(0, parse(llllrrr));
	}
	
	@Test
	public void lrr() {
		InputStream lrr = toStream("())");
		assertNotEquals(0, parse(lrr));	
	}
	
	@Test
	public void lrlrr() {
		InputStream lrlrr = toStream("()())");
		assertNotEquals(0, parse(lrlrr));
	}
}
