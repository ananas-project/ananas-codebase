package ananas.lib.dtml.dom;

import ananas.lib.blueprint3.core.dom.BPAttribute;
import ananas.lib.blueprint3.core.util.attribute_helper.AttrUtil;

public class C_node extends C_object {

	public T_node target_node() {
		return (T_node) this.getTarget(true);
	}

	public boolean set_attribute_name(BPAttribute attr) {
		String name = AttrUtil.getString(attr);
		this.target_node().setName(name);
		return true;
	}

	public boolean set_attribute_optional(BPAttribute attr) {
		boolean opt = AttrUtil.getBool(attr);
		this.target_node().setRequired(!opt);
		return true;
	}

	public boolean set_attribute_required(BPAttribute attr) {
		boolean required = AttrUtil.getBool(attr);
		this.target_node().setRequired(required);
		return true;
	}

}
