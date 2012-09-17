package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashMap;

import ananas.app.rfc_tw.model.IWord;
import ananas.app.rfc_tw.model.IWordSet;

public class BprWords extends BprObjectBase implements IWordSet {

	private final HashMap<String, BprWord> mMap;

	public BprWords() {
		this.mMap = new HashMap<String, BprWord>();
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

	public void setBprWord(BprWord word) {
		String id = word.getId();
		this.mMap.put(id, word);
	}

	@Override
	public IWord getById(String id) {
		return this.mMap.get(id);
	}

	@Override
	public void put(String word) {
		BprWord bprWord = new BprWord();
		bprWord.setText(word);
		this.mMap.put(word, bprWord);
	}

	@Override
	public IWord[] allWords() {
		Collection<BprWord> all = this.mMap.values();
		IWord[] array = new IWord[all.size()];
		int index = 0;
		for (BprWord word : all) {
			array[index++] = word;
		}
		return array;
	}

}
