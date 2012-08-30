package ananas.lib.blueprint.elements.reflect;

import ananas.lib.blueprint.IImplementation;
import ananas.lib.blueprint.INamespace;
import ananas.lib.blueprint.INamespaceLoader;

public abstract class AbstractReflectNamespaceLoader implements
		INamespaceLoader {

	private final String mDefaultPrefix;
	private final String mNSURI;

	public AbstractReflectNamespaceLoader(String nsURI, String defaultPrefix) {
		this.mNSURI = nsURI;
		this.mDefaultPrefix = defaultPrefix;
	}

	@Override
	public final INamespace load(IImplementation impl) {
		INamespace ns = impl.newNamespace(this.mNSURI, this.mDefaultPrefix);
		MyClassRegistrar reg = new MyClassRegistrar(ns);
		this.onLoad(reg);
		return ns;
	}

	protected interface ClassRegistrar {
		void reg(String localName, Class<?> aClass);
	}

	protected class MyClassRegistrar implements ClassRegistrar {

		private final INamespace mNS;

		public MyClassRegistrar(INamespace ns) {
			this.mNS = ns;
		}

		public void reg(String localName, Class<?> aClass) {
			this.mNS.registerClass(localName,
					ReflectElement.DefaultElementReflect.class, aClass);
		}
	}

	protected abstract void onLoad(ClassRegistrar reg);

}
