package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import ananas.app.rfc_tw.model.ISentence;
import ananas.app.rfc_tw.model.ISentenceSet;

public class BprSentences extends BprObjectBase implements ISentenceSet {

	private final HashMap<String, BprDictItem> mMap;

	public BprSentences() {
		this.mMap = new HashMap<String, BprDictItem>();
	}

	@Override
	public void onSaveBegin(OutputStreamWriter osw) throws IOException {
		osw.write("<sentences>");
	}

	@Override
	public void onSaveContent(OutputStreamWriter osw) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSaveEnd(OutputStreamWriter osw) throws IOException {
		osw.write("</sentences>");
	}

	public void setBprSentence(BprSentence sentence) {
	}

	@Override
	public ISentence getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
