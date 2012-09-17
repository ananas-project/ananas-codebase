package ananas.app.rfc_tw.model;

public interface IDoc {

	IDictionary getDictionary();

	ISentenceSet getSentenceSet();

	IWordSet getWordSet();

	IOriginal getOriginal();
}
