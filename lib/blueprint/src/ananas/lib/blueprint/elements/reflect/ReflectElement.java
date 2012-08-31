package ananas.lib.blueprint.elements.reflect;

import java.lang.reflect.Method;

import ananas.lib.blueprint.DefaultElement;
import ananas.lib.blueprint.IElement;
import ananas.lib.blueprint.elements.base.ElementImport;

public interface ReflectElement extends IElement {

	IElement findElementById(String ref);

	Object findTargetObjectById(String ref);

	Class<?> findClass(String ref);

	public class DefaultElementReflect extends DefaultElement implements
			ReflectElement {

		private BprField mCurrentField;

		@Override
		public boolean setAttribute(String nsURI, String name, String value) {
			if (name == null) {
				return false;
			} else if (!name.equals("id")) {
				this._setAttr(name, value);
			} else {
				return super.setAttribute(nsURI, name, value);
			}
			return true;
		}

		private void _setAttr(String name, String value) {
			try {
				Object tar = this.getTarget(true);
				char ch = name.charAt(0);
				if ('a' <= ch && ch <= 'z') {
					ch = (char) ((ch - 'a') + 'A');
				}
				String methodName = "set" + ch + name.substring(1);
				Class<?> parameterTypes = String.class;
				Method method = tar.getClass().getMethod(methodName,
						parameterTypes);
				method.invoke(tar, value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public boolean appendText(String text) {
			Object parent = this.getTarget(true);
			Object rlt = _doBind(parent, text);
			if (Boolean.TRUE.equals(rlt)) {
				return true;
			}
			return super.appendText(text);
		}

		@Override
		public void tagBegin() {
			super.tagBegin();
		}

		@Override
		public boolean appendChild(IElement element) {

			if (element == null) {
				return false;
			} else {
				Object parent = this.getTarget(true);
				Object child = element.getTarget();
				boolean rlt = this._doAppendChild(parent, child);
				if (rlt) {
					return true;
				} else {
					return super.appendChild(element);
				}
			}
		}

		private boolean _doAppendChild(Object parent, Object child) {

			Object rlt = _doBind(parent, child);
			if (Boolean.TRUE.equals(rlt)) {
				return true;
			}

			// child select

			if (child instanceof BprInvoke) {
				BprInvoke invoke = (BprInvoke) child;
				invoke.doInvoke(parent);

			} else if (child instanceof BprField) {
				BprField field = (BprField) child;
				this.mCurrentField = field;

			} else {
				BprField field = this.mCurrentField;
				this.mCurrentField = null;
				if (field == null) {
					field = new BprField();
				}
				field.doSet(parent, child);
			}
			return true;
		}

		@Override
		public Object createTarget() {
			Object target = super.createTarget();
			_doBind(target, this);
			return target;
		}

		private static Object _doBind(Object parent, Object child) {
			try {
				String methodName = "bind";
				Class<?> parameterTypes = Object.class;
				Method method = parent.getClass().getMethod(methodName,
						parameterTypes);
				return method.invoke(parent, child);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public IElement findElementById(String ref) {
			if (ref.startsWith("#")) {
				return this.getOwnerDocument()
						.findElementById(ref.substring(1));
			}
			System.err.println("cannot find elelemt by id : " + ref);
			return null;
		}

		@Override
		public Class<?> findClass(String ref) {
			if (ref.startsWith("#")) {
				ElementImport imp = (ElementImport) this.findElementById(ref);
				if (imp == null) {
					System.err.println("cannot find import with id : " + ref);
				}
				return imp.importClass();
			} else {
				try {
					return Class.forName(ref);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		public Object findTargetObjectById(String ref) {
			IElement element = this.findElementById(ref);
			return element.getTarget();
		}

	}

}
