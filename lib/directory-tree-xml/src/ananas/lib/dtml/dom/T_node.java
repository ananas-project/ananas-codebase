package ananas.lib.dtml.dom;

public class T_node extends T_object {

	private boolean mRequired;
	private String mName;
	private T_dir mParent;

	public void setRequired(boolean required) {
		this.mRequired = required;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public void setParent(T_dir parent) {
		this.mParent = parent;
	}

}
