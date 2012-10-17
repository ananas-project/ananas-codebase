package ananans.app.timespaceterm;

import java.io.File;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import ananas.lib.blueprint2.dom.IDocument;
import ananas.lib.blueprint2.swing_ex.JDirectoryTreeNode;

public class MyFileTreeView {

	private final JTree mJTree;

	public MyFileTreeView(IDocument doc, String id) {
		if (id == null)
			id = this.getClass().getSimpleName();
		this.mJTree = (JTree) doc.findTargetById(id);
	}

	public JTree getJTree() {
		return this.mJTree;
	}

	public File getFile() {
		TreePath path = this.mJTree.getSelectionPath();
		JDirectoryTreeNode node = (JDirectoryTreeNode) path
				.getLastPathComponent();
		return node.getFile();
	}

	public void setFile(File file) {
	}

}
