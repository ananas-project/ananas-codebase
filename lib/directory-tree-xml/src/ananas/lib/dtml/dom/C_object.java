package ananas.lib.dtml.dom;

import ananas.lib.blueprint3.core.dom.BPAttribute;
import ananas.lib.blueprint3.core.lang.CObject;

public class C_object extends CObject {

	public T_object target_object() {
		return (T_object) this.getTarget(true);
	}

	public boolean set_attribute_id(BPAttribute attr) {
		this.target_object().setId(attr.getValue());
		return true;
	}

}
