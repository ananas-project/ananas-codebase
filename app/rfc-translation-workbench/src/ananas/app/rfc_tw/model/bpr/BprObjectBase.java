package ananas.app.rfc_tw.model.bpr;

import java.io.IOException;
import java.io.OutputStreamWriter;

import ananas.lib.blueprint.elements.reflect.IBlueprintReflectable;

public abstract class BprObjectBase implements IBlueprintReflectable, ISaveSupport,
		ISaveSupportEx {

	public BprObjectBase() {
	}

	@Override
	public boolean bind(Object child) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public final void save(OutputStreamWriter osw) throws IOException {
		this.onSaveBegin(osw);
		this.onSaveContent(osw);
		this.onSaveEnd(osw);
	}

}
