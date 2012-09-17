package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;

import ananas.app.rfc_tw.model.IOriginal;

public class BprOriginal extends BprObjectBase implements IOriginal {

	private BprHashableString mString;
	private String mTextCache;
	private String mTextCacheWithoutPageInfo;

	public BprOriginal() {
	}

	@Override
	public void onSaveBegin(OutputStreamWriter osw) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveContent(OutputStreamWriter osw) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveEnd(OutputStreamWriter osw) throws IOException {
		// TODO Auto-generated method stub

	}

	public void setBprHashableString(BprHashableString str) {
		this.mString = str;
	}

	public void setText(String text) {
		this.mTextCache = text;
		this.mTextCacheWithoutPageInfo = null;
	}

	public String getText(boolean withoutPageInfo) {
		return this._getText(withoutPageInfo);
	}

	private String _getText(boolean withoutPageInfo) {
		if (withoutPageInfo) {
			String ret = this.mTextCacheWithoutPageInfo;
			if (ret == null) {
				this.mTextCacheWithoutPageInfo = ret = _removePageInfo(this.mTextCache);
			}
			return ret;
		} else {
			return this.mTextCache;
		}
	}

	private static String _removePageInfo(String text) {
		if (text == null)
			text = "";
		final char chNewPage = 0x0c;
		final StringBuilder pageBuf = new StringBuilder(1024 * 5);
		final StringBuilder outputBuf = new StringBuilder(1024 * 5);
		final char[] chs = (text + chNewPage).toCharArray();
		char chPrev = 0;
		int firstEmptyLine = -1;
		int lastEmptyLine = -1;
		char chCRLF = 0x0d;
		for (char ch : chs) {
			if (ch == 0x0a || ch == 0x0d) {
				if (chPrev == 0x0a || chPrev == 0x0d) {
					chCRLF = ch;
					int index = pageBuf.length();
					if (firstEmptyLine < 0)
						firstEmptyLine = index;
					lastEmptyLine = index;
				}
			}
			if (ch == chNewPage) {
				String page = pageBuf.toString();
				if (lastEmptyLine >= 0 && firstEmptyLine >= 0) {
					page = page.substring(firstEmptyLine, lastEmptyLine).trim();
				}
				outputBuf.append(chCRLF + page);
				// reset
				pageBuf.setLength(0);
				firstEmptyLine = -1;
				lastEmptyLine = -1;
			} else {
				pageBuf.append(ch);
			}
			chPrev = ch;
		}
		return outputBuf.toString();
	}

	@Override
	public String getText() {
		return this._getText(false);
	}
}
