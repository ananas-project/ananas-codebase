package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import ananas.app.rfc_tw.model.IWord;
import ananas.app.rfc_tw.model.IWordSet;

public class BprWords extends BprObjectBase implements IWordSet {

	private final HashMap<String, BprWord> mMap;

	public BprWords() {
		this.mMap = new HashMap<String, BprWord>();
	}

	@Override
	public void onSaveBegin(OutputStreamWriter osw) throws IOException {
		osw.write("<words>");
	}

	@Override
	public void onSaveContent(OutputStreamWriter osw) throws IOException {
		List<BprWord> list = new ArrayList<BprWord>();
		list.addAll(this.mMap.values());
		Comparator<? super BprWord> comp = new Comparator<BprWord>() {
			@Override
			public int compare(BprWord arg0, BprWord arg1) {
				String t0 = arg0.getText();
				String t1 = arg1.getText();
				return t0.compareTo(t1);
			}
		};
		Collections.<BprWord> sort(list, comp);
		for (BprWord word : list) {
			word.save(osw);
		}
	}

	@Override
	public void onSaveEnd(OutputStreamWriter osw) throws IOException {
		osw.write("</words>");
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
