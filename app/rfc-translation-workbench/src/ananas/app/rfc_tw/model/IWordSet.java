package ananas.app.rfc_tw.model;

public interface IWordSet {

	IWord getById(String id);

	void put(String word);

	IWord[] allWords();

}
