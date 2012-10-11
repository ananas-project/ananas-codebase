package ananas.lib.blueprint2.element.base;

import ananas.lib.blueprint2.dom.helper.IClassRegistrar;
import ananas.lib.blueprint2.dom.helper.IImplementation;
import ananas.lib.blueprint2.dom.helper.INamespace;
import ananas.lib.blueprint2.dom.helper.INamespaceLoader;

public class NamespaceLoader implements INamespaceLoader {

	@Override
	public INamespace load(IImplementation impl) {
		String uri = "xmlns:ananas:blueprint:base";
		String defaultPrefix = "bp";
		INamespace ns = impl.newNamespace(uri, defaultPrefix);
		{
			final IClassRegistrar ar = ns.getAttributeClassRegistrar();
			ar.registerClass(uri, "id", Object.class, Object.class);
			ar.registerClass(uri, "xmlns", Object.class, Object.class);
		}
		{
			final IClassRegistrar er = ns.getElementClassRegistrar();
			er.registerClass(uri, "blueprint", ElementBlueprint.class,
					Object.class);
			er.registerClass(uri, "link", ElementLink.class, Object.class);
			er.registerClass(uri, "head", ElementHead.class, Object.class);
			er.registerClass(uri, "body", ElementBody.class, Object.class);
		}
		return ns;
	}

}
