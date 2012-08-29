package ananas.lib.blueprint.elements.base;

import ananas.lib.blueprint.DefaultElement;
import ananas.lib.blueprint.IElement;

public interface ElementKey extends IElement {

	String getKeyName();

	public class DefaultElementKey extends DefaultElement implements ElementKey {

		private static final Object attr_name = "name";
		private String mName;

		@Override
		public boolean setAttribute(String nsURI, String name, String value) {

			if (name == null || value == null) {
				return false;

			} else if (name.equals(attr_name)) {
				this.mName = value;

			} else {
				return super.setAttribute(nsURI, name, value);
			}

			return true;
		}

		@Override
		public String getKeyName() {
			return this.mName;
		}

	}
}
