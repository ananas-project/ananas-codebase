package ananas.lib.blueprint.elements.base;

import ananas.lib.blueprint.DefaultElement;
import ananas.lib.blueprint.IElement;

public interface ElementImport extends IElement {

	static final String type_class_old_style = "class";
	static final String type_namespace_old_style = "class:NamespaceLoader";

	static final String type_class = "class:*";
	static final String type_namespace = "class:NamespaceLoader";

	String getValue();

	String getType();

	Class<?> importClass();

	public class DefaultElementImport extends DefaultElement implements
			ElementImport {

		private String mValue;
		private String mType;
		private Class<?> mTargetClass;

		@Override
		public boolean setAttribute(String nsURI, String name, String value) {

			if (name == null || value == null) {
				return false;

			} else if (name.equals("type")) {
				this.mType = value;

			} else if (name.equals("value")) {
				this.mValue = value;

			} else {
				return super.setAttribute(nsURI, name, value);
			}

			return true;
		}

		@Override
		public String getType() {
			return this.mType;
		}

		@Override
		public String getValue() {
			return this.mValue;
		}

		@Override
		public Class<?> importClass() {

			if (this.mType == null) {
				return null;
			} else if (this.mType.equals(ElementImport.type_class)) {
			} else if (this.mType.equals(ElementImport.type_namespace)) {
			} else if (this.mType.equals(ElementImport.type_class_old_style)) {
			} else if (this.mType.equals(type_namespace_old_style)) {
			} else {
				return null;
			}

			Class<?> cls = this.mTargetClass;
			if (cls == null) {
				try {
					cls = Class.forName(this.mValue);
				} catch (ClassNotFoundException e) {
					// e.printStackTrace();
					throw new RuntimeException(e);
				}
				this.mTargetClass = cls;
			}
			return cls;
		}

	}
}
