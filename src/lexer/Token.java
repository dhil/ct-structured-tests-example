package lexer;

import util.Position;

/**
 * @author cdubach, dhil
 */
public class Token {

    public enum TokenClass {
        LPAR, // the character '('
        RPAR, // the character ')'
        EOF, // end of file token
        INVALID // special error token 
    }


    public final TokenClass tokenClass;
    public final String data;
    public final Position position;

    public Token(TokenClass type, int lineNum, int colNum) {
        this(type, "", lineNum, colNum);
    }
    
    public Token(TokenClass type) {
    	this(type, "", -1, -1); // dummy positional info
    }

    public Token (TokenClass tokenClass, String data, int lineNum, int colNum) {
        assert (tokenClass != null);
        this.tokenClass = tokenClass;
        this.data = data;
        this.position = new Position(lineNum, colNum);
    }



    @Override
    public String toString() {
        if (data.equals(""))
            return tokenClass.toString();
        else
            return tokenClass.toString()+"("+data+")";
    }

}


