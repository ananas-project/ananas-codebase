package ananas.lib.xmpp.client.component;

import ananas.lib.blueprint2.dom.helper.IImplementation;
import ananas.lib.blueprint2.dom.helper.INamespace;
import ananas.lib.blueprint2.dom.helper.INamespaceLoader;
import ananas.lib.blueprint2.element.base.BaseAttr;

public class NamespaceLoader implements INamespaceLoader {

	static final String nsURI = "xmlns:ananas:xmpp:client:component";
	static final String defaultPrefix = "xmpp-com";

	@Override
	public INamespace load(IImplementation impl) {
		INamespace ns = impl.newNamespace(nsURI, defaultPrefix);
		MyHelper h = new MyHelper(ns);
		{
			h.reg("id", BaseAttr.class, String.class);
		}
		{
			h.reg("root", XMPPComRoot.class);

			h.reg("account-manager", XMPPComAccountManager.class);
			h.reg("codec", XMPPComCodec.class);
			h.reg("connection", XMPPComConnection.class);
		}
		return ns;
	}

	class MyHelper {

		private final INamespace mNS;
		private final String mURI;

		public MyHelper(INamespace ns) {
			this.mNS = ns;
			this.mURI = ns.getNamespaceURI();
		}

		public void reg(String localName, Class<?> classTarget) {
			try {
				String cn = classTarget.getName() + "Element";
				Class<?> classWrapper = Class.forName(cn);
				this.reg(localName, classWrapper, classTarget);
			} catch (Exception e) {
				// e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		public void reg(String localName, Class<?> classWrapper,
				Class<?> classTarget) {

			this.mNS.registerClass(this.mURI, localName, classWrapper,
					classTarget);
		}
	}

}
