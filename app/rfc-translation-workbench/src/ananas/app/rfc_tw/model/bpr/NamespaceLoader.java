package ananas.app.rfc_tw.model.bpr;

import ananas.lib.blueprint.elements.reflect.AbstractReflectNamespaceLoader;

public class NamespaceLoader extends AbstractReflectNamespaceLoader {

	static final String nsURI = "xmlns:ananas:rfctw";
	static final String defaultPrefix = "tw";

	public NamespaceLoader() {
		super(nsURI, defaultPrefix);
	}

	@Override
	protected void onLoad(ClassRegistrar reg) {

		// reg.reg("factory", BprPoolFactory.class);

		reg.reg("rfctw", BprRFCTW.class);
		reg.reg("original", BprOriginal.class);
		reg.reg("bytes", BprBytes.class);
		reg.reg("string", BprHashableString.class);

		reg.reg("dictionary", BprDictionary.class);
		reg.reg("item", BprDictItem.class);
		reg.reg("words", BprWords.class);
		reg.reg("word", BprWord.class);
		reg.reg("sentences", BprSentences.class);
		reg.reg("sentence", BprSentence.class);

	}

}
