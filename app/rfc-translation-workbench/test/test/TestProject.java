package test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestProject {

	public static final String filepath = "C:/Users/xukun/Desktop/test-rfctw.xml";

	public String getOriginalText() {
		try {
			String path = "openid-authentication-2_0.txt";
			InputStream is = this.getClass().getResourceAsStream(path);
			InputStreamReader rdr = new InputStreamReader(is);
			StringBuilder sb = new StringBuilder(1024);
			char[] cbuf = new char[1024];
			for (int cc = rdr.read(cbuf); cc > 0; cc = rdr.read(cbuf)) {
				sb.append(cbuf, 0, cc);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public File getDocumentFile() {
		return new File(filepath);
	}

}
