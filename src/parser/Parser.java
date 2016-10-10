package parser;

import lexer.Token;
import lexer.Tokeniser;
import lexer.Token.TokenClass;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * @author cdubach, dhil
 * 
 * See the bottom half of the class for the interesting stuff.
 */
public class Parser {

    private Token token;

    // use for backtracking (useful for distinguishing decls from procs when parsing a program for instance)
    private Queue<Token> buffer = new LinkedList<>();

    private final Tokeniser tokeniser;



    public Parser(Tokeniser tokeniser) {
        this.tokeniser = tokeniser;
    }


    public int getErrorCount() {
        return error;
    }

    private int error = 0;
    private Token lastErrorToken;

    private void error(TokenClass... expected) {

        if (lastErrorToken == token) {
            // skip this error, same token causing trouble
            return;
        }

        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (TokenClass e : expected) {
            sb.append(sep);
            sb.append(e);
            sep = "|";
        }
        System.out.println("Parsing error: expected ("+sb+") found ("+token+") at "+token.position);

        error++;
        lastErrorToken = token;
    }

    /*
     * Look ahead the i^th element from the stream of token.
     * i should be >= 1
     */
    private Token lookAhead(int i) {
        // ensures the buffer has the element we want to look ahead
        while (buffer.size() < i)
            buffer.add(tokeniser.nextToken());
        assert buffer.size() >= i;

        int cnt=1;
        for (Token t : buffer) {
            if (cnt == i)
                return t;
            cnt++;
        }

        assert false; // should never reach this
        return null;
    }


    /*
     * Consumes the next token from the tokeniser or the buffer if not empty.
     */
    private Token nextToken() {
        if (!buffer.isEmpty())
            token = buffer.remove();
        else
            token = tokeniser.nextToken();
        return this.token;
    }

    /*
     * If the current token is equals to the expected one, then skip it, otherwise report an error.
     * Returns the expected token or null if an error occurred.
     */
    private Token expect(TokenClass... expected) {
        for (TokenClass e : expected) {
            if (e == token.tokenClass) {
                Token cur = token;
                nextToken();
                return cur;
            }
        }

        error(expected);
        return null;
    }

    /*
    * Returns true if the current token is equals to any of the expected ones.
    */

    private boolean accept(TokenClass... expected) {
        boolean result = false;
        for (TokenClass e : expected)
            result |= (e == token.tokenClass);
        return result;
    }

    /*** The interesting stuff is below ***/
    
    /**
     * Recall the grammar:
     * S ::= S S | ( S ) | epsilon
     * 
     * After removal of left recursion:
     * 
     * S  ::= ( S ) S' | epsilon
     * S' ::= S S' | epsilon
     */
    public void parse() {
    	nextToken();    	
    	parseS();
    	expect(TokenClass.EOF);
    }
    
    /**
     * Parses the empty string "epsilon".
     */
    public void parseEpsilon() { }
    
    /**
     * Parses 
     * S ::= ( S ) S' | epsilon
     */
    public void parseS() {    	
    	if (accept(TokenClass.LPAR)) {
    		expect(TokenClass.LPAR); // Consume '('
    		
    		if (accept(TokenClass.LPAR)) parseS();
    		else parseEpsilon();
    		
    		expect(TokenClass.RPAR); // Consume ')'
    		parseSprime();
    	} else
    		parseEpsilon();
    }         
    
    /**
     * Parses 
     * S' ::= S S' | epsilon
     */
    public void parseSprime() {    	
    	if (accept(TokenClass.LPAR)) {
    		parseS();
    		parseSprime();
    	} else
    		parseEpsilon();
    }
}
