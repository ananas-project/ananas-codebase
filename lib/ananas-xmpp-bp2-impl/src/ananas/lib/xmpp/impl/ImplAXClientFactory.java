package ananas.lib.xmpp.impl;

import ananas.lib.blueprint2.Blueprint2;
import ananas.lib.blueprint2.dom.IDocument;
import ananas.lib.xmpp.api.AXAccount;
import ananas.lib.xmpp.api.AXClientEx;
import ananas.lib.xmpp.api.AXClientFactory;

final class ImplAXClientFactory implements AXClientFactory {

	private final String mConfURI;

	public ImplAXClientFactory(String uri) {
		this.mConfURI = uri;
	}

	@Override
	public AXClientEx createClient(AXAccount account) {
		try {
			Blueprint2 bp = Blueprint2.getInstance();
			IDocument doc = bp.loadDocument(this.mConfURI);
			Object root = doc.findTargetById("root");
			AXClientEx client = (AXClientEx) root;
			client.bindAccount(account);
			return client;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
