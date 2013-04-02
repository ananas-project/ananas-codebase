package ananas.lib.dtml.dom;

public class C_file extends C_node {

	public T_file target_file() {
		return (T_file) this.getTarget(true);
	}

}
