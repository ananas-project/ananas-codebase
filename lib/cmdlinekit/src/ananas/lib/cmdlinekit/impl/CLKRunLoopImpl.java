package ananas.lib.cmdlinekit.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Vector;

import ananas.lib.cmdlinekit.CLKCommand;
import ananas.lib.cmdlinekit.CLKCommandSet;
import ananas.lib.cmdlinekit.CLKExecuteContext;
import ananas.lib.cmdlinekit.CLKMutableParameter;
import ananas.lib.cmdlinekit.CLKMutableParameterList;
import ananas.lib.cmdlinekit.CLKParameterList;
import ananas.lib.cmdlinekit.CLKRunLoop;
import ananas.lib.cmdlinekit.DefaultCLKExecuteContext;

class CLKRunLoopImpl implements CLKRunLoop {

	private boolean mIsNeedExit;

	@Override
	public void run(CLKExecuteContext context) {

		try {
			InputStream is = context.getInput();
			this.mIsNeedExit = false;
			for (; !this.mIsNeedExit;) {

				PrintStream ps = context.getPrint();
				ps.println();
				ps.print(context.getPrompt().getText());

				String[] line = this._readOneLine(is);
				String cmd_name = this._getNameOfCommand(line);
				if (cmd_name == null) {
					continue;
				} else {
					if (cmd_name.length() < 1) {
						continue;
					}
				}
				CLKCommandSet cmdset = context.getCommandSet();
				CLKCommand cmd = cmdset.getCommand(cmd_name);
				if (cmd == null) {
					context.getPrint()
							.println("bad command:'" + cmd_name + "'");
				} else {
					DefaultCLKExecuteContext cont = new DefaultCLKExecuteContext(
							context);
					cont.mRunLoop = this;
					cont.mCommand = cmd;
					cont.mCommandSet = cmdset;
					cont.mParamList = this._getPrarmListOf(line, context);
					this._safe_exec(cont, cmd);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _safe_exec(DefaultCLKExecuteContext cont, CLKCommand cmd) {
		try {
			cmd.execute(cont);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class MyParameterReader {

		private final char[] mChs;
		private final int mLength;
		private int mPtr;

		MyParameterReader(String str) {
			this.mChs = str.toCharArray();
			this.mLength = this.mChs.length;
			this.mPtr = 0;
		}

		public void skipAllOfChar(char c) {
			for (; mPtr < mLength; mPtr++) {
				if (mChs[mPtr] != c) {
					break;
				}
			}
		}

		public String readWord() {
			StringBuffer sbuf = new StringBuffer();
			for (; mPtr < mLength; mPtr++) {
				final char ch = mChs[mPtr];
				if (('0' <= ch) && (ch <= '9')) {
				} else if (('a' <= ch) && (ch <= 'z')) {
				} else if (('A' <= ch) && (ch <= 'Z')) {
				} else if (ch == '_' || ch == '.' || ch == '-') {
				} else {
					break;
				}
				sbuf.append(ch);
			}
			return sbuf.toString();
		}

		public String readAll() {
			return new String(this.mChs, this.mPtr, (this.mLength - this.mPtr));
		}
	}

	private CLKParameterList _getPrarmListOf(String[] line,
			CLKExecuteContext context) {

		CLKMutableParameterList plist = context.getElementsFactory()
				.newParameterList();

		for (int i = 1; i < line.length; i++) {
			String seg = line[i];
			MyParameterReader reader = new MyParameterReader(seg);
			reader.skipAllOfChar('-');
			String name = reader.readWord();
			String value = reader.readAll().trim();
			CLKMutableParameter param = context.getElementsFactory()
					.newParameter(name);
			param.setValue(value);
			plist.addParameter(param);
		}

		return plist;
	}

	private String _getNameOfCommand(String[] line) {
		if (line.length > 0) {
			String cmd = line[0];
			if (cmd != null) {
				return cmd.trim();
			}
		}
		return null;
	}

	private static class MyWordsList extends OutputStream {

		final Vector<String> mv = new Vector<String>();
		final ByteArrayOutputStream mbaos = new ByteArrayOutputStream();

		@Override
		public void write(int b) throws IOException {
			if (b == 0x0a || b == 0x0d) {
				byte[] ba = mbaos.toByteArray();
				mbaos.reset();
				String str = new String(ba, "utf-8");
				mv.addElement(str);
			} else {
				mbaos.write(b);
			}
		}

		public String[] toArray() {
			return mv.toArray(new String[0]);
		}
	}

	private String[] _readOneLine(InputStream is) throws IOException {
		final MyWordsList baos = new MyWordsList();
		int prevByte = 0;
		for (int b = is.read(); b >= 0; b = is.read()) {
			if (b == 0x0a || b == 0x0d) {
				break;
			} else if (b == '-') {
				if (prevByte == ' ' || prevByte == 0x09) {
					baos.write('\n');
				}
			}
			baos.write(b);
			prevByte = b;
		}
		baos.write('\n');
		baos.close();
		return baos.toArray();
	}

	@Override
	public void exit() {
		this.mIsNeedExit = true;
	}

}
