package util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

/***
 * 
 * @author dhil
 *
 */
public class Streams {
	public static InputStream toStream(String source, Charset charset) {
		InputStream stream = new ByteArrayInputStream(source.getBytes(charset));
		return stream;
	}
	
	public static InputStream toStream(String source) {
		return toStream(source, Charset.forName("ASCII"));
	}
}
