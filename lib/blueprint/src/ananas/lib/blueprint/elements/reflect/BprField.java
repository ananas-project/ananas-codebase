package ananas.lib.blueprint.elements.reflect;

import java.lang.reflect.Method;

public class BprField {

	private ReflectElement mElement;
	private Class<?> mClassCache;
	private String mFieldName;
	private String mClassRef;

	public boolean bind(Object child) {
		if (child instanceof ReflectElement) {
			this.mElement = (ReflectElement) child;
			return true;
		} else {
			return false;
		}
	}

	public void setName(String s) {
		this.mFieldName = s;
	}

	public void setClass(String s) {
		this.mClassRef = s;
	}

	public void doSet(Object parent, Object child) {

		try {
			Class<?> aClass = this._getClass();
			String fieldName = this.mFieldName;
			if (aClass == null) {
				aClass = child.getClass();
			}
			if (fieldName == null) {
				fieldName = aClass.getSimpleName();
			}
			char ch = fieldName.toUpperCase().charAt(0);
			String setterName = "set" + ch + fieldName.substring(1);
			Method method = parent.getClass().getMethod(setterName, aClass);
			method.invoke(parent, child);
		} catch (Exception e) {
			System.err
					.println("exception while put " + child + " to " + parent);
			throw new RuntimeException(e);
		}
	}

	private Class<?> _getClass() {
		Class<?> cls = this.mClassCache;
		if (cls == null) {
			cls = this.mElement.findClass(this.mClassRef);
			if (cls == null) {
				System.err.println("cannot find class:" + this.mClassRef);
			}
			this.mClassCache = cls;
		}
		return cls;
	}

}
