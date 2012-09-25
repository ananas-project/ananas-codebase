package ananas.app.rfc_tw.xliff;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class RFC2XLIFF implements Convertor {

	@Override
	public void convert(File src, File dest) throws IOException {

		MyReader rdr = new MyReader(src);
		MyWriter wtr = new MyWriter(dest);

		rdr.open();
		wtr.open();

		rdr.close();
		wtr.close();

	}

	class MyReader {

		public MyReader(File src) {
			// TODO Auto-generated constructor stub
		}

		public void open() {
			// TODO Auto-generated method stub

		}

		public void close() {
			// TODO Auto-generated method stub

		}
	}

	class MyWriter {

		private final File mFile;
		private OutputStreamWriter mOSW;
		private OutputStream mOS;

		public MyWriter(File dest) {
			this.mFile = dest;
		}

		public void open() throws IOException {

			this.mFile.getParentFile().mkdirs();

			OutputStream os = new FileOutputStream(this.mFile);
			OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
			this.mOS = os;
			this.mOSW = osw;

			osw.write("<?xml version=\"1.0\" ?>");
			osw.write("<xliff version=\"1.0\">");

		}

		public void close() throws IOException {

			OutputStreamWriter osw = this.mOSW;
			osw.write("</xliff>");

			this.mOSW.flush();
			this.mOSW.close();

			this.mOS.flush();
			this.mOS.close();
		}
	}

}
