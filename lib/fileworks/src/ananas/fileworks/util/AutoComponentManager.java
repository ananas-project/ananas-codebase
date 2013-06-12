package ananas.fileworks.util;

import ananas.fileworks.Component;
import ananas.fileworks.ComponentManager;
import ananas.fileworks.ComponentRegistrar;

public class AutoComponentManager implements ComponentManager {

	private final ComponentRegistrar mCompReg;
	private final ComponentManager mCompMngr;

	public AutoComponentManager(ComponentRegistrar cr, ComponentManager cm) {
		this.mCompReg = cr;
		this.mCompMngr = cm;
	}

	@Override
	public Component get(Class<?> api, String name) {
		Component comp = this.mCompMngr.get(api, name);
		if (comp == null) {
			if (this.mCompMngr.hasDeclare(api, name)) {
				comp = this.mCompReg.createComponent(api);
				if (comp != null) {
					this.mCompMngr.put(api, name, comp);
				}
			}
		}
		return comp;
	}

	@Override
	public boolean put(Class<?> api, String name, Component comp) {
		return this.mCompMngr.put(api, name, comp);
	}

	@Override
	public boolean declare(Class<?> api, String name, boolean isFinal) {
		return this.mCompMngr.declare(api, name, isFinal);
	}

	@Override
	public boolean hasDeclare(Class<?> api, String name) {
		return this.mCompMngr.hasDeclare(api, name);
	}

}
