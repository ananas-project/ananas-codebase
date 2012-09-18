package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;

import ananas.app.rfc_tw.model.IDictionaryItem;

public class BprDictItem extends BprObjectBase implements IDictionaryItem {

	private String mKey;
	private String mValue;

	// private BprHashableString mString;

	public BprDictItem() {
	}

	@Override
	public void onSaveBegin(OutputStreamWriter osw) throws IOException {
		osw.write("<item>");
	}

	@Override
	public void onSaveContent(OutputStreamWriter osw) throws IOException {
		BprHashableString str = new BprHashableString();
		str.setValue(this.mValue + "");
		str.save(osw);
	}

	@Override
	public void onSaveEnd(OutputStreamWriter osw) throws IOException {
		osw.write("</item>");
	}

	public void setKey(String key) {
		this.mKey = key;
	}

	public void setBprHashableString(BprHashableString str) {
		this.mValue = str.getValue();
	}

	public String getKey() {
		return this.mKey;
	}

	@Override
	public String key() {
		return this.mKey;
	}

	@Override
	public String value() {
		return this.mValue;
	}

	public void setItem(String key, String value) {
		this.mKey = key;
		this.mValue = value;
	}

}
