package ananas.fileworks.util;

import ananas.fileworks.ComponentManager;

public class ComponentCache<T> implements Refer<T> {

	private final ComponentManager m_cm;
	private final Class<?> m_api;
	private final String m_name;
	private T m_ptr;

	public ComponentCache(ComponentManager cm, Class<?> api, String name) {
		this.m_cm = cm;
		this.m_api = api;
		this.m_name = name;
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get() {
		T ptr = this.m_ptr;
		if (ptr == null) {
			Class<?> api = this.m_api;
			String name = this.m_name;
			ptr = (T) this.m_cm.get(api, name);
			this.m_ptr = ptr;
		}
		return ptr;
	}

}
