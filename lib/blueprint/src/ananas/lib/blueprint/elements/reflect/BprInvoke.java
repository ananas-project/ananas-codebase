package ananas.lib.blueprint.elements.reflect;

import java.util.Vector;

public class BprInvoke extends BprObject {

	private final Vector<Object> mParamList;

	public BprInvoke() {
		this.mParamList = new Vector<Object>();
	}

	public boolean bind(Object child) {
		this.mParamList.addElement(child);
		return true;
	}

	public void doInvoke(Object parent) {
		// TODO Auto-generated method stub

	}

	public void setMethod(String s) {
	}

	public void setParameterTypes(String s) {
	}

}
