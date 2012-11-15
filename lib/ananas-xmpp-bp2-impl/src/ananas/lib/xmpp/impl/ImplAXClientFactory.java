package ananas.lib.xmpp.impl;

import ananas.lib.blueprint2.Blueprint2;
import ananas.lib.blueprint2.dom.IDocument;
import ananas.lib.xmpp.api.AXAccount;
import ananas.lib.xmpp.api.AXClient;
import ananas.lib.xmpp.api.AXClientFactory;
import ananas.lib.xmpp.api.command.AXCmdSetClient;

final class ImplAXClientFactory implements AXClientFactory {

	private final String mConfURI;

	public ImplAXClientFactory(String uri) {
		this.mConfURI = uri;
	}

	@Override
	public AXClient createClient(AXAccount account) {
		try {
			Blueprint2 bp = Blueprint2.getInstance();
			IDocument doc = bp.loadDocument(this.mConfURI);
			Object root = doc.findTargetById("root");
			AXClient client = (AXClient) root;
			AXCmdSetClient cmd = new AXCmdSetClient();
			cmd.bindAccount(account);
			client.execute(cmd);
			return client;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
