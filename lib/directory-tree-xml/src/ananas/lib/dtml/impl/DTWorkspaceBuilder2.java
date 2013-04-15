package ananas.lib.dtml.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ananas.lib.dtml.DTDirectory;
import ananas.lib.dtml.DTFile;
import ananas.lib.dtml.DTNode;
import ananas.lib.dtml.DTWorkspace;
import ananas.lib.dtml.DTWorkspaceFactory;
import ananas.lib.dtml.dom.T_dir;
import ananas.lib.dtml.dom.T_file;
import ananas.lib.dtml.dom.T_node;
import ananas.lib.dtml.dom.T_template;
import ananas.lib.io.vfs.VFile;

public class DTWorkspaceBuilder2 {

	private final T_template mTemp;
	private final VFile mFile;

	public DTWorkspaceBuilder2(T_template template, VFile file) {
		this.mTemp = template;
		this.mFile = file;
	}

	public DTWorkspace build() {
		T_node root = this.mTemp.getRootNode();
		List<NodeShell> list = new ArrayList<NodeShell>();
		this._scanTree(list, root, 32);

		this._setVFile(list);

		DTWorkspaceImpl works = new DTWorkspaceImpl(this.mTemp);
		DTWorkspace worksFacade = new DTWorkspaceFacade(works);
		for (NodeShell ns : list) {
			DTNode dtNode = ns.createNode(worksFacade);
			works.addNode(dtNode);
		}
		return worksFacade;
	}

	private void _setVFile(List<NodeShell> list) {
		// make map
		Map<T_node, NodeShell> map = new HashMap<T_node, NodeShell>();
		for (NodeShell ns : list) {
			map.put(ns.mNode, ns);
		}
		// find main node
		T_node mainNode = this.mTemp.getMainNode();
		NodeShell mainNS = map.get(mainNode);
		// set parent for NS(s)
		for (NodeShell ns : list) {
			T_node node_p, node_c;
			node_c = ns.mNode;
			if (node_c == null) {
				node_p = null;
			} else {
				node_p = node_c.getParent();
			}
			NodeShell ns_p = map.get(node_p);
			ns.setParent(ns_p);
		}
		// set path for mainNS
		mainNS.setPath(this.mFile);
		for (NodeShell ns : list) {
			ns.getVFile();
		}
	}

	private void _scanTree(List<NodeShell> list, T_node node, int depthLimit) {
		if (depthLimit <= 0) {
			throw new RuntimeException("the tree is too deep.");
		}
		list.add(new NodeShell(node));
		if (node instanceof T_dir) {
			T_dir dir = (T_dir) node;
			List<T_node> chs = dir.listChildren();
			for (T_node ch : chs) {
				this._scanTree(list, ch, depthLimit - 1);
			}
		}
	}

	class NodeShell {

		private final T_node mNode;
		private VFile mVFile;// do cache
		private NodeShell mParent;
		private String mName;

		public NodeShell(T_node node) {
			this.mNode = node;
		}

		public void setPath(VFile file) {

			this.mVFile = file;

			String name_f = file.getName();
			String name_n = this.getName();

			if (name_n == null) {
				// skip
			} else if (name_n.equals("")) {
				// skip
			} else if (name_n.equals("*")) {
				// skip
			} else {
				if (!name_n.equals(name_f)) {
					throw new RuntimeException("required a '" + name_n
							+ "' but get a '" + name_f + "'");
				}
			}

			NodeShell parent = this.mParent;
			if (parent != null) {
				parent.setPath(file.getParentFile());
			}
		}

		public void setParent(NodeShell parent) {
			this.mParent = parent;
		}

		public DTNode createNode(DTWorkspace works) {

			if (this.mNode == null) {

			} else if (this.mNode instanceof T_file) {
				String id = this.getId();
				VFile vfile = this.getVFile();
				boolean required = this.isRequired();
				return new DTFileImpl(works, id, vfile, required);

			} else if (this.mNode instanceof T_dir) {
				String id = this.getId();
				VFile vfile = this.getVFile();
				boolean required = this.isRequired();
				return new DTDirectoryImpl(works, id, vfile, required);

			} else {
			}

			return null;
		}

		private boolean isRequired() {
			return this.mNode.isRequired();
		}

		private VFile getVFile() {
			VFile vf = this.mVFile;
			if (vf == null) {
				VFile parent = this.mParent.getVFile();
				vf = parent.getVFS().newFile(parent, this.getName());
				this.mVFile = vf;
			}
			return vf;
		}

		private String getName() {
			String name = this.mName;
			if (name == null) {
				name = this.mNode.getName();
				this.mName = name;
			}
			return name;
		}

		private String getId() {
			return this.mNode.getId();
		}
	}

	static class DTWorkspaceFacade implements DTWorkspace {

		private final DTWorkspaceImpl inner;

		public DTWorkspaceFacade(DTWorkspaceImpl works) {
			this.inner = works;
		}

		public DTWorkspace getWorkspace() {
			return inner.getWorkspace();
		}

		public VFile getFile() {
			return inner.getFile();
		}

		public String getId() {
			return inner.getId();
		}

		public boolean init() {
			return inner.init();
		}

		public boolean check() {
			return inner.check();
		}

		public boolean repair() {
			return inner.repair();
		}

		public DTFile findFileById(String id) {
			return inner.findFileById(id);
		}

		public DTDirectory findDirectoryById(String id) {
			return inner.findDirectoryById(id);
		}

		public DTWorkspaceFactory getFactory() {
			return inner.getFactory();
		}

		public void addNode(DTNode dtNode) {
			inner.addNode(dtNode);
		}

		public boolean equals(Object obj) {
			return inner.equals(obj);
		}

		public DTNode findNodeById(String id) {
			return inner.findNodeById(id);
		}

		public int hashCode() {
			return inner.hashCode();
		}

		public List<DTNode> listNodes() {
			return inner.listNodes();
		}

		public String toString() {
			return inner.toString();
		}

	}
}
