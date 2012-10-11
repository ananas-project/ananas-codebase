package ananas.lib.blueprint2.impl;

import ananas.lib.blueprint2.dom.IText;
import ananas.lib.blueprint2.dom.helper.IClass;

final class ImplText implements IText {

	private final String mData;

	public ImplText(String data) {
		this.mData = data;
	}

	@Override
	public boolean bindBlueprintClass(IClass aClass) {
		return false;
	}

	@Override
	public IClass getBlueprintClass() {
		return null;
	}

	@Override
	public String getData() {
		return this.mData;
	}

}
