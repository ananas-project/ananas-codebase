package ananas.lib.xmpp.client.component;

import ananas.lib.blueprint2.dom.helper.IImplementation;
import ananas.lib.blueprint2.dom.helper.INamespace;
import ananas.lib.blueprint2.dom.helper.INamespaceLoader;

public class NamespaceLoader implements INamespaceLoader {

	static final String nsURI = "xmlns:ananas:xmpp:client:component";
	static final String defaultPrefix = "xmpp-com";

	@Override
	public INamespace load(IImplementation impl) {
		INamespace ns = impl.newNamespace(nsURI, defaultPrefix);

		return ns;
	}

}
