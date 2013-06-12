package ananas.fileworks.util;

import ananas.fileworks.ComponentManager;
import ananas.fileworks.ComponentRegistrar;
import ananas.fileworks.Environment;

public class DefaultEnvironment implements Environment {

	private final ComponentManager mAutoCompMan;
	private final ComponentRegistrar mCompReg;

	public DefaultEnvironment() {
		ComponentRegistrar cr = new DefaultComponentRegistrar();
		this.mCompReg = cr;
		ComponentManager cm = new DefaultComponentManager();
		this.mAutoCompMan = new AutoComponentManager(cr, cm);
	}

	@Override
	public ComponentManager getSingletonManager() {
		return this.mAutoCompMan;
	}

	@Override
	public ComponentRegistrar getComponentRegistrar() {
		return this.mCompReg;
	}

}
