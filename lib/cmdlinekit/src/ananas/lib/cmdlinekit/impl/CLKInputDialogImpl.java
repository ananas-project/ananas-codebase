package ananas.lib.cmdlinekit.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import ananas.lib.cmdlinekit.CLKExecuteContext;
import ananas.lib.cmdlinekit.CLKInputDialog;


public class CLKInputDialogImpl implements CLKInputDialog {

	@Override
	public String readInput(CLKExecuteContext context, String prompt,
			String ending) {
		final PrintStream ps = context.getPrint();
		final InputStream is = context.getInput();
		if (prompt != null) {
			ps.println();
			ps.print(prompt);
		}
		String str = null;
		try {
			do {
				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				for (int b = is.read(); b >= 0; b = is.read()) {
					if (b == 0x0a || b == 0x0d) {
						break;
					} else {
						baos.write(b);
					}
				}
				byte[] ba = baos.toByteArray();
				str = new String(ba, "utf-8");
				str = str.trim();
			} while (str.length() < 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

}
