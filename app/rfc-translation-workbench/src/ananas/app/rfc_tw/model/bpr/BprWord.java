package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;

import ananas.app.rfc_tw.model.IWord;

public class BprWord extends BprObjectBase implements IWord {

	private BprHashableString mString;

	@Override
	public void onSaveBegin(OutputStreamWriter osw) throws IOException {
		osw.write("<word>");
	}

	@Override
	public void onSaveContent(OutputStreamWriter osw) throws IOException {
		this._getString().save(osw);
	}

	@Override
	public void onSaveEnd(OutputStreamWriter osw) throws IOException {
		osw.write("</word>");
	}

	public void setBprHashableString(BprHashableString str) {
		this.mString = str;
	}

	public void setText(String word) {
		this._getString().setValue(word);
	}

	private BprHashableString _getString() {
		if (this.mString == null)
			this.mString = new BprHashableString();
		return this.mString;
	}

	@Override
	public String getId() {
		return this._getString().getValue();
	}

	@Override
	public String getText() {
		return this._getString().getValue();
	}

}
