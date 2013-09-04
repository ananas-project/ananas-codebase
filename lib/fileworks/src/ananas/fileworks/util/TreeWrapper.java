package ananas.fileworks.util;

import java.util.Map;

import ananas.fileworks.node.Tree;
import ananas.fileworks.node.TreeNode;
import ananas.fileworks.node.TreeNodeT;
import ananas.fileworks.node.TreeTemplate;
import ananas.lib.io.vfs.VFile;

public class TreeWrapper implements Tree {

	private final Tree _tree;

	public TreeWrapper(Tree tree) {
		this._tree = tree;
	}

	public TreeNodeT getNodeT() {
		return _tree.getNodeT();
	}

	public Map<String, Object> getFeatures() {
		return _tree.getFeatures();
	}

	public Tree getTree() {
		return _tree.getTree();
	}

	public TreeTemplate getTemplate() {
		return _tree.getTemplate();
	}

	public VFile getFile() {
		return _tree.getFile();
	}

	public TreeNode getNode(String path) {
		return _tree.getNode(path);
	}

}
