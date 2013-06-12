package ananas.fileworks.util;

import ananas.fileworks.ComponentManager;
import ananas.fileworks.ComponentRegistrar;
import ananas.fileworks.Context;
import ananas.fileworks.Environment;
import ananas.lib.io.vfs.VFile;

public class DefaultContext implements Context {

	private final Environment mEnvi;
	private final VFile mMainPath;
	private final ComponentManager mAutoCompMan;

	public DefaultContext(Environment envi, VFile path) {
		if (envi == null) {
			envi = new DefaultEnvironment();
		}
		this.mEnvi = envi;
		this.mMainPath = path;
		ComponentManager cm = new DefaultComponentManager();
		ComponentRegistrar cr = envi.getComponentRegistrar();
		this.mAutoCompMan = new AutoComponentManager(cr, cm);
	}

	@Override
	public Environment getEnvironment() {
		return this.mEnvi;
	}

	@Override
	public ComponentManager getComponentManager() {
		return this.mAutoCompMan;
	}

	@Override
	public VFile getMainPath() {
		return this.mMainPath;
	}

}
