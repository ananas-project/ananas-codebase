package ananas.lib.blueprint.elements.reflect;

public class BprImport {

	public static final String type_class_old_style = "class";
	public static final String type_namespace_old_style = "class:NamespaceLoader";
	public static final String type_class = "class:*";
	public static final String type_namespace = "class:NamespaceLoader";

	// private ReflectElement mElement;

	private String mValue;
	private String mType;

	public boolean bind(Object child) {
		if (child instanceof ReflectElement) {
			// this.mElement = (ReflectElement) child;
		} else {
			return false;
		}
		return true;
	}

	public void setType(String type) {
		this.mType = type;
	}

	public void setValue(String type) {
		this.mValue = type;
	}

	public String getValue() {
		return mValue;
	}

	public String getType() {
		return mType;
	}

	public Class<?> importClass() {
		try {
			return Class.forName(this.mValue);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
