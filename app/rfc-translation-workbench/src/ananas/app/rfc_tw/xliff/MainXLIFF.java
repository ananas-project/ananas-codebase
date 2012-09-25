package ananas.app.rfc_tw.xliff;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainXLIFF {

	public static void main(String[] args) {
		Map<String, String> mapArgs = new HashMap<String, String>();
		String field = "";
		for (String a : args) {
			a = a.trim();
			if (a.startsWith("-")) {
				field = a;
			} else {
				mapArgs.put(field, a);
			}
		}
		(new MainXLIFF()).run(mapArgs);
		System.out.println("The End.");
	}

	private MainXLIFF() {
	}

	private void run(Map<String, String> args) {

		try {
			String src = args.get("-src");
			String dest = args.get("-dest");
			String op = args.get("-op");
			final Convertor cvt;
			if ("scan".equals(op)) {
				cvt = new RFC2XLIFF();
			} else if ("export".equals(op)) {
				cvt = new XLIFF2RFC();
			} else {
				this.showHelp();
				return;
			}
			System.out.println("execute with:");
			this.printParameters(op, src, dest);
			File fileSrc = new File(src);
			File fileDest = new File(dest);
			cvt.convert(fileSrc, fileDest);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println();
			System.out.println();
			this.showHelp();
		}
	}

	private void printParameters(String op, String src, String dest) {
		System.out.println("    op   = " + op);
		System.out.println("    src  = " + src);
		System.out.println("    dest = " + dest);
	}

	private void showHelp() {
		System.out.println("use this command with parameters like this:");
		System.out
				.println("    -op {scan|export} -src [src_file_path] -dest [dest_file_path]");
	}
}
