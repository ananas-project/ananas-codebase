package ananas.fileworks.util;

import java.util.Hashtable;
import java.util.Map;

import ananas.fileworks.Component;
import ananas.fileworks.ComponentManager;

public class DefaultComponentManager implements ComponentManager {

	private final Map<String, Item> m_table;

	public DefaultComponentManager() {
		this.m_table = new Hashtable<String, Item>();
	}

	@Override
	public Component get(Class<?> api, String name) {
		String key = this.__get_key(api, name);
		Item item = this.m_table.get(key);
		if (item == null) {
			System.err.println("cannot get component : " + key);
			return null;
		}
		return item.getComponent();
	}

	private String __get_key(Class<?> api, String name) {
		return (api + "###" + name);
	}

	@Override
	public boolean put(Class<?> api, String name, Component comp) {
		String key = this.__get_key(api, name);
		Item item = this.m_table.get(key);
		if (item == null) {
			return false;
		}
		return item.doPut(comp);
	}

	@Override
	public boolean declare(Class<?> api, String name, boolean isFinal) {
		String key = this.__get_key(api, name);
		Item item = this.m_table.get(key);
		if (item == null) {
			item = new Item(isFinal);
			this.m_table.put(key, item);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean hasDeclare(Class<?> api, String name) {
		String key = this.__get_key(api, name);
		Item item = this.m_table.get(key);
		return (item != null);
	}

	class Item {

		private final boolean m_isFinal;
		private Component m_comp;

		public Item(boolean isFinal) {
			this.m_isFinal = isFinal;
		}

		public Component getComponent() {
			return this.m_comp;
		}

		public boolean doPut(Component comp) {
			if (this.m_isFinal) {
				if (this.m_comp == null) {
					this.m_comp = comp;
					return true;
				} else {
					return false;
				}
			} else {
				this.m_comp = comp;
				return true;
			}
		}
	}
}
