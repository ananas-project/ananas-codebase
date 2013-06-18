package ananas.fileworks.util;

import ananas.fileworks.Component;
import ananas.fileworks.ComponentManager;
import ananas.fileworks.ComponentRegistrar;
import ananas.fileworks.Context;

public class AutoComponentManager implements ComponentManager {

	private final ComponentRegistrar mCompReg;
	private final ComponentManager mCompMngr;
	private final Context mContext;

	public AutoComponentManager(Context context, ComponentRegistrar cr,
			ComponentManager cm) {
		this.mCompReg = cr;
		this.mCompMngr = cm;
		this.mContext = context;
	}

	@Override
	public Component get(Class<?> api, String name) {
		Component comp = this.mCompMngr.get(api, name);
		if (comp == null) {
			if (this.mCompMngr.hasDeclare(api, name)) {
				comp = this.mCompReg.create(this.mContext, api);
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
