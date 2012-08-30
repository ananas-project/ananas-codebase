package ananas.lib.blueprint.elements.reflect;

public class NamespaceLoader extends AbstractReflectNamespaceLoader {

	static final String nsURI = "xmlns:ananas:blueprint:reflect";
	static final String defaultPrefix = "bpr";

	public NamespaceLoader() {
		super(nsURI, defaultPrefix);
	}

	@Override
	protected void onLoad(ClassRegistrar reg) {
		reg.reg("field", BprField.class);
		reg.reg("invoke", BprInvoke.class);

		reg.reg("boolean", BprBaseType.class);
		reg.reg("int", BprBaseType.class);
		reg.reg("long", BprBaseType.class);
		reg.reg("double", BprBaseType.class);
		reg.reg("float", BprBaseType.class);
		reg.reg("string", BprBaseType.class);
	}

}
