package ananas.lib.blueprint2;

import ananas.lib.blueprint2.dom.helper.IBlueprint;
import ananas.lib.blueprint2.impl.FinalBlueprintImpl;

public abstract class Blueprint2 implements IBlueprint {

	private static final Blueprint2 sInst;

	static {
		sInst = FinalBlueprintImpl.getInst();
	}

	public static Blueprint2 getInstance() {
		return sInst;
	}

}
