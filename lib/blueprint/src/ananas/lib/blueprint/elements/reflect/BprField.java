package ananas.lib.blueprint.elements.reflect;

import java.lang.reflect.Method;

public class BprField {

	private ReflectElement mElement;
	private Class<?> mClass;
	private String mFieldName;

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
		this.mClass = this.mElement.findClass(s);
	}

	public void doSet(Object parent, Object child) {

		try {
			Class<?> aClass = this.mClass;
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
			throw new RuntimeException(e);
		}
	}

}
