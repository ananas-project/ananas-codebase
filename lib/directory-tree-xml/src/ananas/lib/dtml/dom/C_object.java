package ananas.lib.dtml.dom;

import ananas.lib.blueprint3.dom.BPAttribute;
import ananas.lib.blueprint3.lang.CObject;

public class C_object extends CObject {

	public T_object target_object() {
		return (T_object) this.getTarget(true);
	}

	public boolean set_attribute_id(BPAttribute attr) {
		String id = attr.getValue();
		this.target_object().setId(id);
		this.getOwnerDocument().getElementRegistrar().put(id, this);
		return true;
	}

}
