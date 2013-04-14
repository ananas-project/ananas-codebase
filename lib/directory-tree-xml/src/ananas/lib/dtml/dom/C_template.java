package ananas.lib.dtml.dom;

import ananas.lib.blueprint3.dom.BPAttribute;
import ananas.lib.blueprint3.dom.BPDocument;

public class C_template extends C_object {

	private BPAttribute m_attr_main;

	public T_template target_template() {
		return (T_template) this.getTarget(true);
	}

	public boolean append_child_(C_node node) {
		this.target_template().setRootNode(node.target_node());
		return true;
	}

	public boolean set_attribute_main(BPAttribute attr) {
		this.m_attr_main = attr;
		return true;
	}

	public void onTagEnd() {
		super.onTagEnd();
		String uri = this.m_attr_main.getValue();
		BPDocument doc = this.getOwnerDocument();
		T_node main = (T_node) doc.findTargetByURI(uri);
		this.target_template().setMain(main);
	}
}
