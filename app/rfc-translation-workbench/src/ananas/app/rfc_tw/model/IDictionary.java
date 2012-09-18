package ananas.app.rfc_tw.model;

public interface IDictionary {

	void put(String key, String value);

	String get(String key);

	IDictionaryItem[] allItems();

}
