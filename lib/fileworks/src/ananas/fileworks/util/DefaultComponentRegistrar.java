package ananas.fileworks.util;

import java.util.Hashtable;
import java.util.Map;

import ananas.fileworks.Component;
import ananas.fileworks.ComponentRegistrar;

public class DefaultComponentRegistrar implements ComponentRegistrar {

	private final Map<Class<?>, Class<?>> m_map;

	public DefaultComponentRegistrar() {
		this.m_map = new Hashtable<Class<?>, Class<?>>();
	}

	@Override
	public void register(Class<?> api, Class<?> aClass) {
		this.m_map.put(api, aClass);
	}

	@Override
	public Class<?> getComponentClass(Class<?> api) {
		return this.m_map.get(api);
	}

	@Override
	public Component createComponent(Class<?> api) {
		try {
			Class<?> cls = this.getComponentClass(api);
			if (cls == null) {
				System.err.println("need for implementation of " + api);
			}
			return (Component) cls.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
