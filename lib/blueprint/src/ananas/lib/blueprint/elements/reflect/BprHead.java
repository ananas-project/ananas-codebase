package ananas.lib.blueprint.elements.reflect;

public class BprHead {

	private ReflectElement mElement;

	public boolean bind(Object child) {
		if (child instanceof ReflectElement) {
			this.mElement = (ReflectElement) child;
		} else if (child instanceof BprImport) {
			this.setBprImport((BprImport) child);
		} else {
			return true;
		}
		return true;
	}

	public void setBprImport(BprImport imp) {
		String type = imp.getType();
		String value = imp.getValue();
		if (type.equals(BprImport.type_namespace)
				|| type.equals(BprImport.type_namespace_old_style)) {
			this.mElement.getOwnerDocument().getImplementation()
					.getNamespaceRegistrar().loadNamespace(value);
		}
	}

}
