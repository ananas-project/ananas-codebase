package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;

import ananas.app.rfc_tw.model.IDictionary;

public class BprRFCTW extends BprObjectBase {

	private BprOriginal mOriginal;
	private BprSentences mSentences;
	private BprWords mWords;
	private BprDictionary mDictionary;

	public IDictionary getDict() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setOriginalText(String text) {
		// TODO Auto-generated method stub

	}

	public String getOriginalText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onSaveBegin(OutputStreamWriter osw) throws IOException {
		osw.write("<rfctw xmlns='xmlns:ananas:rfctw'>");
	}

	@Override
	public void onSaveContent(OutputStreamWriter osw) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveEnd(OutputStreamWriter osw) throws IOException {
		osw.write("</rfctw>");
	}

	public void setBprOriginal(BprOriginal original) {
		this.mOriginal = original;
	}

	public void setBprDictionary(BprDictionary dict) {
		this.mDictionary = dict;
	}

	public void setBprWords(BprWords words) {
		this.mWords = words;
	}

	public void setBprSentences(BprSentences sentences) {
		this.mSentences = sentences;
	}

}
