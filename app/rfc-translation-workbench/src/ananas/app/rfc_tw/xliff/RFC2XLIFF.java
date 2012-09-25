package ananas.app.rfc_tw.xliff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class RFC2XLIFF implements Convertor {

	@Override
	public void convert(File src, File dest) throws IOException {

		File skl = new File(src.getCanonicalPath() + ".skl");

		MyReader rdr = new MyReader(src);
		MyWriter wtr = new MyWriter(dest);
		MySkeletonWriter wtrSkl = new MySkeletonWriter(skl);

		rdr.open();
		wtrSkl.open();
		wtr.open(src, skl);

		final TransUnit unit = new TransUnit();
		for (;;) {
			final TransUnit unit2 = rdr.readUnit(unit);
			if (unit2 == null)
				break;
			wtr.writeUnit(unit2);
			wtrSkl.writeUnit(unit2);
		}

		rdr.close();
		wtr.close();
		wtrSkl.close();

	}

	class TransUnit {
		boolean needTrans;
		String id;
		String text;
	}

	class MyReader {

		private final File mFile;
		private InputStreamReader mISR;
		private FileInputStream mIS;

		private final List<TransUnit> mUnitList = new ArrayList<TransUnit>();
		private int mUnitReadIndex = 0;

		public MyReader(File src) {
			this.mFile = src;
		}

		public TransUnit readUnit(TransUnit unit) {
			final int index = (this.mUnitReadIndex++);
			if (index < this.mUnitList.size()) {
				unit = this.mUnitList.get(index);
				unit.id = "$(xliff_unit_id_" + index + ")";
				return unit;
			} else {
				return null;
			}
		}

		public void open() throws IOException {
			FileInputStream is = new FileInputStream(this.mFile);
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			this.mIS = is;
			this.mISR = isr;
			this.doParse();
		}

		private void doParse() throws IOException {

			final char pager = 0x0c;

			final InputStreamReader rdr = this.mISR;
			final List<String> bufPage = new ArrayList<String>();
			final StringBuilder bufLine = new StringBuilder(512);
			for (int chint = rdr.read(); chint >= 0; chint = rdr.read()) {
				final char ch = (char) chint;
				if (ch == pager) {
					this.procPage(bufPage);
					this.addUnit(null, "" + ch, false);
					bufPage.clear();
					continue;
				}
				bufLine.append(ch);
				if (ch == 0x0a || ch == 0x0d) {
					// line end
					bufPage.add(bufLine.toString());
					bufLine.setLength(0);
				} else {
				}
			}
		}

		private void procPage(List<String> lines) {

			int iStart, iEnd;
			iStart = 1;
			iEnd = lines.size() - 2;

			for (; iStart < iEnd; iStart++) {
				String line = lines.get(iStart);
				if (line.length() == 1)
					break;
			}
			for (; iStart < iEnd; iEnd--) {
				String line = lines.get(iEnd);
				if (line.length() == 1)
					break;
			}

			String strHead = "";
			String strBody = "";
			String strFoot = "";
			final int cntLine = lines.size();
			final StringBuilder mixbuf = new StringBuilder();
			for (int iLine = 0; iLine < cntLine; iLine++) {
				String line = lines.get(iLine);
				if (iLine == iStart) {
					mixbuf.append(line);
					strHead = mixbuf.toString();
					mixbuf.setLength(0);
				} else if (iLine == iEnd) {
					strBody = mixbuf.toString();
					mixbuf.setLength(0);
					mixbuf.append(line);
				} else {
					mixbuf.append(line);
				}
			}
			strFoot = mixbuf.toString();

			this.onPage(strHead, strBody, strFoot);
		}

		private void onPage(String strHead, String strBody, String strFoot) {
			String id = "";
			this.addUnit(id, strHead, true);
			// this.addUnit(id, strBody, false);
			this.parsePageBody(strBody);
			this.addUnit(id, strFoot, true);
		}

		private void parsePageBody(String text) {
			boolean isPrevWords = false;
			char[] chs = text.toCharArray();
			StringBuilder buf = new StringBuilder(128);
			for (char ch : chs) {
				final boolean isWords;
				switch (ch) {
				case '.':
				case '!':
				case '?':
					isWords = false;
					break;
				default:
					isWords = true;
				}
				if (isWords != isPrevWords) {
					if (isPrevWords) {
						buf.append(ch);
						String str = buf.toString();
						buf.setLength(0);
						this.addUnit(null, str, true);
					} else {
						String str = buf.toString();
						buf.setLength(0);
						buf.append(ch);
						this.addUnit(null, str, false);
					}
				} else {
					buf.append(ch);
				}
				isPrevWords = isWords;
			}

		}

		private void addUnit(String id, String text, boolean needTrans) {
			TransUnit unit = new TransUnit();
			unit.id = id;
			unit.text = text;
			unit.needTrans = needTrans;
			this.mUnitList.add(unit);
		}

		public void close() throws IOException {
			// close isr
			this.mISR.close();
			// close is
			this.mIS.close();
		}
	}

	class MySkeletonWriter {

		private final File mFile;
		private OutputStreamWriter mOSW;
		private OutputStream mOS;

		public MySkeletonWriter(File dest) {
			this.mFile = dest;
		}

		public void writeUnit(TransUnit unit) throws IOException {
			OutputStreamWriter osw = this.mOSW;
			if (unit.needTrans) {
				osw.write("" + unit.id);
			} else {
				osw.write("" + unit.text);
			}
		}

		public void open() throws IOException {
			this.mFile.getParentFile().mkdirs();
			OutputStream os = new FileOutputStream(this.mFile);
			OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
			this.mOS = os;
			this.mOSW = osw;
		}

		public void close() throws IOException {
			// close osw
			this.mOSW.flush();
			this.mOSW.close();
			// close os
			this.mOS.flush();
			this.mOS.close();
		}
	}

	class MyWriter {

		private final File mFile;
		private OutputStreamWriter mOSW;
		private OutputStream mOS;

		public MyWriter(File dest) {
			this.mFile = dest;
		}

		public void writeUnit(TransUnit unit) throws IOException {
			if (unit.needTrans) {
				OutputStreamWriter osw = this.mOSW;
				osw.write("<trans-unit id='" + unit.id + "'>");
				osw.write("<source xml:lang='en'>");

				osw.write("<![CDATA[");
				osw.write(unit.text);
				osw.write("]]>");

				osw.write("</source>");
				osw.write("</trans-unit>");
			}
		}

		public void open(File src, File skl) throws IOException {

			this.mFile.getParentFile().mkdirs();

			OutputStream os = new FileOutputStream(this.mFile);
			OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
			this.mOS = os;
			this.mOSW = osw;

			osw.write("<?xml version=\"1.0\" ?>");
			osw.write("<xliff version=\"1.0\">");
			{
				// <file> begin
				String original = src.getName();
				String srcLang = "en";
				String dataType = "Plain Text";
				osw.write("<file");
				osw.write(" original='" + original + "'");
				osw.write(" source-language='" + srcLang + "'");
				osw.write(" datatype='" + dataType + "'");
				osw.write(">");
			}
			{
				// <header/>
				String sklFile = skl.getName();
				osw.write("<header>");
				osw.write("<skl>");
				osw.write("<external-file href='" + sklFile + "'/>");
				osw.write("</skl>");
				osw.write("</header>");
			}
			osw.write("<body>");
		}

		public void close() throws IOException {

			OutputStreamWriter osw = this.mOSW;
			osw.write("</body>");
			osw.write("</file>");
			osw.write("</xliff>");

			this.mOSW.flush();
			this.mOSW.close();

			this.mOS.flush();
			this.mOS.close();
		}
	}

}
