// From Java Standard Library
import java.io.InputStream;

// From my project
import lexer.Scanner;
import lexer.Tokeniser;
import parser.Parser;
import static util.Streams.*;


/**
 * @author dhil
 * 
 * This program provides an example of how to add structured tests to your projects, and furthermore, 
 * how to cleanly integrate a structured testing framework such as JUnit. By clean integration, I mean 
 * that CT Test Suite will build your project even though you make use of an unsupported library such as JUnit. 
 * 
 * The basic idea is two have two source folders:
 *    + src/ -- where the main project packages (lexer, parser, etc.) reside
 *    + testsuite/ -- where the testing classes reside
 *    
 * Everything under src/ is old news. You will find the interesting stuff under testsuite/.
 * You will find that we extend the packages from src/ with testing classes. For example, 
 * we add the class testsuite/parser/ParserTests.java to the parser package. This class contains 
 * a handful of test cases for the parser, which is defined in src/parser/Parser.java.
 *    
 * This program implements a parser for the language of sequenced well-balanced parentheses. 
 * It is given by the following grammar:
 *    S ::= S S | ( S ) | epsilon
 * where epsilon denotes the empty string.
 * 
 */
public class Main {
	    
	public static void usage() {
        System.out.println("Usage: java "+Main.class.getSimpleName()+" string");
        System.exit(-1);
    }

    public static void main(String[] args) {    	
    	if (args.length == 0)
    		usage();
    	
    	// Input stream
    	InputStream input = toStream(args[0]);
    	
    	// Prepare parser
    	Tokeniser tokeniser = new Tokeniser(new Scanner(input));
    	Parser parser = new Parser(tokeniser);
    	
    	// Parse input
    	parser.parse();
    	int errors = parser.getErrorCount() + tokeniser.getErrorCount();
    	if (errors > 0) {
    		System.err.println("Parse errors: " + errors);
                System.exit(1);
        }
    }
}
