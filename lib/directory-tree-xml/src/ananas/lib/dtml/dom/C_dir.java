package ananas.lib.dtml.dom;

public class C_dir extends C_node {

	public T_dir target_dir() {
		return (T_dir) this.getTarget(true);
	}

	public boolean append_child_(C_node node) {
		T_node child = node.target_node();
		T_dir parent = this.target_dir();
		parent.add(child);
		child.setParent(parent);
		return true;
	}
}
