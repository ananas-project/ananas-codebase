package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class BprDictItem extends BprObjectBase {

	private String mKey;
	private BprHashableString mString;

	public BprDictItem() {
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

	public void setKey(String key) {
		this.mKey = key;
	}

	public void setBprHashableString(BprHashableString str) {
		this.mString = str;
	}

	public String getKey() {
		return this.mKey;
	}

}
