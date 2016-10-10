package lexer;

import lexer.Token.TokenClass;

import java.io.EOFException;
import java.io.IOException;

/**
 * @author cdubach, dhil
 */
public class Tokeniser {

    private Scanner scanner;

    private int error = 0;
    public int getErrorCount() {
	return this.error;
    }

    public Tokeniser(Scanner scanner) {
        this.scanner = scanner;
    }

    private void error(char c, int line, int col) {
        System.out.println("Lexing error: unrecognised character ("+c+") at "+line+":"+col);
	error++;
    }


    public Token nextToken() {
        Token result;
        try {
             result = next();
        } catch (EOFException eof) {
            // end of file, nothing to worry about, just return EOF token
            return new Token(TokenClass.EOF, scanner.getLine(), scanner.getColumn());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            // something went horribly wrong, abort
            System.exit(-1);
            return null;
        }
        return result;
    }

    /**
     * This is the interesting stuff.
     */
    private Token next() throws IOException {

        int line = scanner.getLine();
        int column = scanner.getColumn();

        // get the next character
        char c = scanner.next();

        // attempt to recognise c
        Token tok;
        if (c == '(')
        	tok = new Token(TokenClass.LPAR, line, column);
        else if (c == ')')
        	tok = new Token(TokenClass.RPAR, line, column);
        else { // if we reach this point, it means we did not recognise a valid token
        	error(c,line,column);
        	tok = new Token(TokenClass.INVALID, line, column);
        }
        return tok;
    }


}
