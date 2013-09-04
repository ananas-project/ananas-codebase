package ananas.fileworks.node;

import ananas.lib.io.vfs.VFile;

public interface TreeNode {

	TreeNodeT getNodeT();

	Tree getTree();

	VFile getFile();
}
