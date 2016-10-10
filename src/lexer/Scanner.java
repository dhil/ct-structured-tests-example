package lexer;

import java.io.*;

/**
 * @author cdubach
 */
public class Scanner {

    BufferedReader input;
    private int peeked = -1;

    private int line = 1;
    private int column = 1;

    public Scanner(File source) throws FileNotFoundException {
        input = new BufferedReader(new FileReader(source));
    }
    
    /**
     * For testing purposes it would be convenient to be able to feed the scanner arbitrary streams, not just file streams.
     * Fortunately, classes are extensible. We use ad-hoc constructor overloading to provide an interface that accepts abstract streams. 
     * @param stream
     */
    public Scanner(InputStream stream) {
    	input = new BufferedReader(new InputStreamReader(stream));
    }


    public int getColumn() {
        return column;
    }

    public int getLine() {
        return line;
    }

    public char peek() throws IOException {
        if (peeked != -1)
            return (char)peeked;

        int r = input.read();
        if (r == -1)
            throw new EOFException();

        peeked = r;
        return (char) r;
    }


    public char next() throws IOException {
        char r;
        if (peeked != -1) {
            r = (char) peeked;
            peeked = -1;
        } else {
            int i = input.read();
            if (i == -1)
                throw new EOFException();
            r = (char) i;
        }

        if (r == '\n' || r == '\r') {
            line++;
            column =0;
        } else {
            column++;
        }
        return r;
    }

    public void close() throws IOException {
        input.close();
    }



}
