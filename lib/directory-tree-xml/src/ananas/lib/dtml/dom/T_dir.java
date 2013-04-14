package ananas.lib.dtml.dom;

import java.util.ArrayList;
import java.util.List;

public class T_dir extends T_node {

	private final List<T_node> m_child_list = new ArrayList<T_node>();

	public T_dir() {
	}

	public void add(T_node child) {
		this.m_child_list.add(child);
	}

	public List<T_node> listChildren() {
		return this.m_child_list;
	}

}
