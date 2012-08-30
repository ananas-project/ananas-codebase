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

		reg.reg("boolean", BprBoolean.class);
		reg.reg("int", BprInteger.class);
		reg.reg("long", BprLong.class);
		reg.reg("double", BprDouble.class);
		reg.reg("float", BprFloat.class);
		reg.reg("string", BprString.class);
	}

}
