package ananas.fileworks.util;

import java.util.Hashtable;
import java.util.Map;

import ananas.fileworks.Component;
import ananas.fileworks.ComponentFactory;
import ananas.fileworks.ComponentRegistrar;
import ananas.fileworks.Context;

public class DefaultComponentRegistrar implements ComponentRegistrar {

	private final Map<Class<?>, Class<?>> m_map_class;
	private final Map<Class<?>, ComponentFactory> m_map_factory;

	public DefaultComponentRegistrar() {
		this.m_map_class = new Hashtable<Class<?>, Class<?>>();
		this.m_map_factory = new Hashtable<Class<?>, ComponentFactory>();
	}

	@Override
	public void register(Class<?> api, Class<?> aClass) {
		this.m_map_class.put(api, aClass);
	}

	@Override
	public ComponentFactory getFactory(Class<?> api) {
		ComponentFactory fact = this.m_map_factory.get(api);
		if (fact == null) {
			Class<?> cls = this.m_map_class.get(api);
			if (cls == null) {
				System.err.println("need for implementation of " + api);
			} else {
				try {
					fact = (ComponentFactory) cls.newInstance();
					this.m_map_factory.put(api, fact);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return fact;
	}

	@Override
	public Component create(Context context, Class<?> api) {
		ComponentFactory factory = this.getFactory(api);
		return factory.createComponent(context);
	}

}
