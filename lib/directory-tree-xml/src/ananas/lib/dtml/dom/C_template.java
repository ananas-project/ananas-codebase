package ananas.lib.dtml.dom;

import ananas.lib.blueprint3.core.dom.BPAttribute;

public class C_template extends C_object {

	private BPAttribute m_attr_workspace;

	public T_template target_template() {
		return (T_template) this.getTarget(true);
	}

	public boolean append_child_(C_node node) {
		this.target_template().setRootNode(node.target_node());
		return true;
	}

	public boolean set_attribute_workspace(BPAttribute attr) {
		this.m_attr_workspace = attr;
		return true;
	}

}
