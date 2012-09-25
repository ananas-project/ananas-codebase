package ananas.app.rfc_tw.xliff;

import java.io.File;
import java.io.IOException;

public interface Convertor {

	/**
	 * convert
	 * 
	 * @param src
	 * @param dest
	 * */

	void convert(File src, File dest) throws IOException;

}
