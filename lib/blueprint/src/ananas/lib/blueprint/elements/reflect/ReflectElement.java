package ananas.lib.blueprint.elements.reflect;

import java.lang.reflect.Method;

import ananas.lib.blueprint.DefaultElement;
import ananas.lib.blueprint.IElement;

public interface ReflectElement extends IElement {

	IElement findElementById(String ref);

	Object findTargetObjectById(String ref);

	Class<?> findClass(String ref);

	Class<?> findClass(String ref, boolean allowNull);

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
			Object tar = this.getTarget(true);
			try {
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
				System.err.println("exception while _setAttr: " + name + "="
						+ value + "; for " + tar);
				throw new RuntimeException(e);
			}
		}

		@Override
		public boolean appendText(String text) {
			IBlueprintReflectable parent = (IBlueprintReflectable) this
					.getTarget(true);
			boolean rlt = _doBind(parent, text);
			if (rlt) {
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
				IBlueprintReflectable parent = (IBlueprintReflectable) this
						.getTarget(true);
				Object child = element.getTarget();
				boolean rlt = this._doAppendChild(parent, child);
				if (rlt) {
					return true;
				} else {
					return super.appendChild(element);
				}
			}
		}

		private boolean _doAppendChild(IBlueprintReflectable parent,
				Object child) {

			boolean rlt = _doBind(parent, child);
			if (rlt) {
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
					field.bind(this);
				}
				field.doSet(parent, child);
			}
			return true;
		}

		@Override
		public final Object createTarget() {
			Object target = super.createTarget();
			_doBind((IBlueprintReflectable) target, this);
			return target;
		}

		private static boolean _doBind(IBlueprintReflectable parent,
				Object child) {
			return parent.bind(child);
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
			return this._findClass(ref);
		}

		@Override
		public Object findTargetObjectById(String ref) {
			IElement element = this.findElementById(ref);
			return element.getTarget();
		}

		@Override
		public Class<?> findClass(String ref, boolean allowNull) {
			Class<?> cls = this._findClass(ref);
			if (cls == null && (!allowNull)) {
				throw new RuntimeException("cannot find class of : " + ref);
			}
			return cls;
		}

		private Class<?> _findClass(String ref) {
			try {
				if (ref == null) {
					return null;
				}
				if (ref.startsWith("#")) {
					BprImport imp = (BprImport) this.findTargetObjectById(ref);
					return imp.importClass();
				} else {
					return Class.forName(ref);
				}
			} catch (Exception e) {
				System.err.println("cannot find import with id : " + ref);
				e.printStackTrace();
				return null;
			}
		}

	}

}
