package ananas.tools.codespider;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSet implements FileHandler {

	private int _count_file;
	private int _count_line;
	public String output;

	@Override
	public void onFile(File file) {

		this._count_file++;

		System.out.println(file);

		try {
			InputStream in = new FileInputStream(file);
			Reader reader = new InputStreamReader(in);
			StringBuilder sb = new StringBuilder();
			char[] buf = new char[1024];
			for (;;) {
				final int cc = reader.read(buf);
				if (cc < 0)
					break;
				for (int i = 0; i < cc; i++) {
					final char ch = buf[i];
					switch (ch) {
					case 0x0a:
					case 0x0d: {
						String s = sb.toString().trim();
						sb.setLength(0);
						if (s.length() <= 0)
							break;
						this.onLine(s);
						break;
					}
					default:
						sb.append(ch);
						break;
					}
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onLine(String s) {
		System.out.println("        " + s);
		this._count_line++;
		MyLinePattern patt = this.getPattern(s, true);
		patt.inc();
	}

	@Override
	public void onDirectory(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBegin(File base) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnd(File base) {

		final String endl = "\r\n";

		List<MyLinePattern> list = new ArrayList<MyLinePattern>(
				_map_patt.values());
		Comparator<? super MyLinePattern> comp = new MyPatternComp();
		Collections.sort(list, comp);
		final int count_patt = list.size();

		StringBuilder sb = new StringBuilder();
		sb.append("in " + base + endl);
		sb.append(endl);
		sb.append("find " + this._count_file + " file(s)" + endl);
		sb.append("find " + this._count_line + " line(s)" + endl);
		sb.append("find " + count_patt + " pattern(s)" + endl);
		sb.append(endl);

		boolean overflow = false;
		for (int i = 0; i < count_patt; ++i) {
			MyLinePattern patt = list.get(i);
			String str = i + ". [x" + patt._count + "] " + patt._string;
			if (i < (1024 * 8))
				sb.append(str + endl);
			else
				overflow = true;
		}
		if (overflow) {
			sb.append("(has more ...)" + endl);
		}

		System.out.println(sb);
		this.output = sb.toString();

	}

	private MyLinePattern getPattern(String str, boolean create) {
		MyLinePattern patt = _map_patt.get(str);
		if (patt == null) {
			if (create) {
				patt = new MyLinePattern(str);
				_map_patt.put(str, patt);
			}
		}
		return patt;
	}

	final Map<String, MyLinePattern> _map_patt = new HashMap<String, MyLinePattern>();

	class MyLinePattern {

		private final String _string;
		private int _count;

		public MyLinePattern(String str) {
			this._string = str;
		}

		public void inc() {
			this._count++;
		}
	}

	static class MyPatternComp implements Comparator<MyLinePattern> {

		@Override
		public int compare(MyLinePattern o1, MyLinePattern o2) {
			int c2 = o2._count;
			int c1 = o1._count;
			if (c2 != c1) {
				return (c2 - c1);
			} else {
				String s1 = o1._string;
				String s2 = o2._string;
				return s1.compareTo(s2);
			}
		}
	}
}
