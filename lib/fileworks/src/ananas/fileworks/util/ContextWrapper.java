package ananas.fileworks.util;

import ananas.fileworks.ComponentManager;
import ananas.fileworks.Context;
import ananas.fileworks.Environment;
import ananas.lib.io.vfs.VFile;

public class ContextWrapper implements Context {

	private final Context mTarget;

	public ContextWrapper(Context target) {
		this.mTarget = target;
	}

	@Override
	public Environment getEnvironment() {
		return this.mTarget.getEnvironment();
	}

	@Override
	public ComponentManager getComponentManager() {
		return this.mTarget.getComponentManager();
	}

	@Override
	public VFile getMainPath() {
		return this.mTarget.getMainPath();
	}

}
