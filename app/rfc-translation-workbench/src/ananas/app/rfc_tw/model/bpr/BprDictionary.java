package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import ananas.app.rfc_tw.model.IDictionary;
import ananas.app.rfc_tw.model.IDictionaryItem;

public class BprDictionary extends BprObjectBase implements IDictionary {

	private final HashMap<String, BprDictItem> mMap;

	public BprDictionary() {
		this.mMap = new HashMap<String, BprDictItem>();
	}

	@Override
	public void onSaveBegin(OutputStreamWriter osw) throws IOException {
		osw.write("<dictionary>");
	}

	@Override
	public void onSaveContent(OutputStreamWriter osw) throws IOException {
		List<BprDictItem> list = new ArrayList<BprDictItem>();
		list.addAll(this.mMap.values());
		Comparator<BprDictItem> comp = new Comparator<BprDictItem>() {
			@Override
			public int compare(BprDictItem o1, BprDictItem o2) {
				String k1 = o1.getKey();
				String k2 = o2.getKey();
				return k1.compareTo(k2);
			}
		};
		Collections.<BprDictItem> sort(list, comp);
		for (BprDictItem item : list) {
			item.save(osw);
		}
	}

	@Override
	public void onSaveEnd(OutputStreamWriter osw) throws IOException {
		osw.write("</dictionary>");
	}

	public void setBprDictItem(BprDictItem item) {
		String key = item.getKey();
		this.mMap.put(key, item);
	}

	@Override
	public void put(String key, String value) {
		BprDictItem item = new BprDictItem();
		item.setItem(key, value);
		this.setBprDictItem(item);
	}

	@Override
	public String get(String key) {
		IDictionaryItem value = this.mMap.get(key);
		return value.value();
	}

	@Override
	public IDictionaryItem[] allItems() {
		ArrayList<IDictionaryItem> v = new ArrayList<IDictionaryItem>();
		for (BprDictItem val : this.mMap.values()) {
			v.add(val);
		}
		return v.toArray(new IDictionaryItem[v.size()]);
	}
}
