package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;

import ananas.app.rfc_tw.model.IDictionary;
import ananas.app.rfc_tw.model.IDoc;
import ananas.app.rfc_tw.model.IOriginal;
import ananas.app.rfc_tw.model.ISentenceSet;
import ananas.app.rfc_tw.model.IWordSet;

public class BprRFCTW extends BprObjectBase implements IDoc {

	private BprOriginal mOriginal;
	private BprSentences mSentences;
	private BprWords mWords;
	private BprDictionary mDictionary;

	public void setOriginalText(String text) {
		BprOriginal original = new BprOriginal();
		original.setText(text);
		this.mOriginal = original;
	}

	public String getOriginalText(boolean withoutPageInfo) {
		BprOriginal original = this.mOriginal;
		if (original == null)
			return null;
		return original.getText(withoutPageInfo);
	}

	@Override
	public void onSaveBegin(OutputStreamWriter osw) throws IOException {
		osw.write("<rfctw xmlns='xmlns:ananas:rfctw'>");
	}

	@Override
	public void onSaveContent(OutputStreamWriter osw) throws IOException {

		BprOriginal original = (BprOriginal) this.getOriginal();
		original.save(osw);

		BprDictionary dict = (BprDictionary) this.getDictionary();
		dict.save(osw);

		BprWords words = (BprWords) this.getWordSet();
		words.save(osw);

		BprSentences sents = (BprSentences) this.getSentenceSet();
		sents.save(osw);
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

	@Override
	public IDictionary getDictionary() {
		if (this.mDictionary == null)
			this.mDictionary = new BprDictionary();
		return this.mDictionary;
	}

	@Override
	public ISentenceSet getSentenceSet() {
		if (this.mSentences == null)
			this.mSentences = new BprSentences();
		return this.mSentences;
	}

	@Override
	public IWordSet getWordSet() {
		if (this.mWords == null)
			this.mWords = new BprWords();
		return this.mWords;
	}

	@Override
	public IOriginal getOriginal() {
		if (this.mOriginal == null)
			this.mOriginal = new BprOriginal();
		return this.mOriginal;
	}

}
