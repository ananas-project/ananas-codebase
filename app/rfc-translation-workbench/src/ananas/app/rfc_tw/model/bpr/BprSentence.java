package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class BprSentence extends BprObjectBase {

	private BprHashableString mString;

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

}
