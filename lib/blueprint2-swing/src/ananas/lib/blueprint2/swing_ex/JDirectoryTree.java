package ananas.lib.blueprint2.swing_ex;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class JDirectoryTree extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2236332669278609191L;

	private File mBasePath;

	public JDirectoryTree() {
		this._init();
	}

	private void _init() {
		File basepath = File.listRoots()[0];
		this.setBasePath(basepath);
	}

	public File getBasePath() {
		return this.mBasePath;
	}

	public void setBasePath(File dir) {
		if (dir != null) {
			this.mBasePath = dir;
			MyTreeNode root = new MyTreeNode(dir, null);
			DefaultTreeModel model = new DefaultTreeModel(root);
			this.setModel(model);
		}
	}

	private class MyEnumeration implements Enumeration<MyTreeNode> {

		MyTreeNode[] mList;
		final int mLen;
		int mPtr = 0;

		public MyEnumeration(MyTreeNode[] chs) {
			this.mList = chs;
			this.mLen = chs.length;
		}

		@Override
		public boolean hasMoreElements() {
			return (this.mPtr < this.mLen);
		}

		@Override
		public MyTreeNode nextElement() {
			return this.mList[this.mPtr++];
		}
	}

	static final MyTreeNode[] sEmptyNodeArray = new MyTreeNode[0];

	static final Comparator<? super MyTreeNode> sNodeComp = new Comparator<MyTreeNode>() {

		@Override
		public int compare(MyTreeNode arg0, MyTreeNode arg1) {
			File f0 = arg0.mFile;
			File f1 = arg1.mFile;
			if (f0.isDirectory() == f1.isDirectory()) {
				String s1 = f1.getName();
				String s0 = f0.getName();
				return s0.compareTo(s1);
			} else {
				return (f0.isDirectory() ? -1 : 1);
			}
		}
	};

	private class MyTreeNode implements TreeNode, JDirectoryTreeNode {

		private final File mFile;
		private final MyTreeNode mParent;
		private MyTreeNode[] mChildren;

		// private final long mBirthTime;

		public MyTreeNode(File file, MyTreeNode parent) {
			this.mFile = file;
			this.mParent = parent;
			// this.mBirthTime = System.currentTimeMillis();
		}

		@Override
		public Enumeration<MyTreeNode> children() {
			MyTreeNode[] chs = this._listChildren(true);
			return new MyEnumeration(chs);
		}

		@Override
		public boolean getAllowsChildren() {
			return this.mFile.isDirectory();
		}

		@Override
		public TreeNode getChildAt(int index) {
			MyTreeNode[] chs = this._listChildren(true);
			return chs[index];
		}

		private MyTreeNode[] _listChildren(boolean create) {
			MyTreeNode[] list = this.mChildren;
			/*
			 * auto refresh
			 * 
			 * if (list != null) { if (list[0].mBirthTime + 3000 <
			 * System.currentTimeMillis()) { list = null; } }
			 */
			if (list == null) {
				if (!this.mFile.isDirectory()) {
					list = sEmptyNodeArray;
				} else {
					File[] files = this.mFile.listFiles();
					if (files == null) {
						list = sEmptyNodeArray;
					} else {
						list = new MyTreeNode[files.length];
						for (int i = files.length - 1; i >= 0; i--) {
							list[i] = new MyTreeNode(files[i], this);
						}
						Comparator<? super MyTreeNode> comp = sNodeComp;
						Arrays.<MyTreeNode> sort(list, comp);
					}
				}
				this.mChildren = list;
			}
			return list;
		}

		@Override
		public int getChildCount() {
			MyTreeNode[] chs = this._listChildren(true);
			return chs.length;
		}

		@Override
		public int getIndex(TreeNode node) {
			if (node == null)
				return -1;
			MyTreeNode[] chs = this._listChildren(true);
			int index = 0;
			for (MyTreeNode ch : chs) {
				if (node.equals(ch)) {
					return index;
				}
				index++;
			}
			return -1;
		}

		@Override
		public TreeNode getParent() {
			return this.mParent;
		}

		@Override
		public boolean isLeaf() {
			return !this.mFile.isDirectory();
		}

		// public void refresh() {
		// this.mChildren = null;
		// }

		public String toString() {
			return this.mFile.getName();
		}

		@Override
		public File getFile() {
			return this.mFile;
		}

	}

}
