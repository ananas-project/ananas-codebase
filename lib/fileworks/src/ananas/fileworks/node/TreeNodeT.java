package ananas.fileworks.node;

public interface TreeNodeT {

	TreeTemplate getTemplate();

	String getPath();

	TreeNode createNode(Tree tree);

	boolean isRequired();

	boolean isDirectory();

}
