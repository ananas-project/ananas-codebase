package ananas.lib.dtml.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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
import ananas.lib.io.vfs.VFileSystem;

public class DTWorkspaceBuilder {

	private final T_template mTemp;
	private final VFile mFile;

	public DTWorkspaceBuilder(T_template template, VFile file) {
		this.mTemp = template;
		this.mFile = file;
	}

	public DTWorkspace build() {
		WorkspaceFacade facade = new WorkspaceFacade();
		T_node root = this.mTemp.getRootNode();
		T_node main = this.mTemp.getMainNode();
		BuildContext bc = new BuildContext();
		bc.root = root;
		bc.main = main;
		bc.main_file = this.mFile;
		bc.workspace = facade;
		List<DTNode> list = bc.build();
		WorkspaceCore core = new WorkspaceCore(list);
		facade.core = core;
		return facade;
	}

	class BuildContext {

		public DTWorkspace workspace;
		public VFile main_file;
		public T_node main;
		public T_node root;
		private VFile root_file;

		public List<DTNode> build() {
			T_node base = main;
			VFile baseFile = this.main_file;
			for (; !base.equals(root);) {
				base = base.getParent();
				baseFile = baseFile.getParentFile();
			}
			this.root_file = baseFile;
			List<DTNode> list = new ArrayList<DTNode>();
			if (base instanceof T_file) {
				T_file file = (T_file) base;
				this.addFile(list, file);
			} else {
				T_dir dir = (T_dir) base;
				this.addDir(list, dir, 32);
			}
			return list;
		}

		private void addDir(List<DTNode> list, T_dir dir, int depthLimit) {
			if (depthLimit <= 0) {
				throw new RuntimeException("too deep!");
			}
			DTDirectory dtDir = this.createDir(dir);
			list.add(dtDir);
			List<T_node> chs = dir.listChildren();
			for (T_node ch : chs) {
				if (ch instanceof T_dir) {
					T_dir chDir = (T_dir) ch;
					this.addDir(list, chDir, depthLimit - 1);
				} else {
					T_file chFile = (T_file) ch;
					this.addFile(list, chFile);
				}
			}
		}

		private DTDirectory createDir(T_dir dir) {
			boolean required = dir.isRequired();
			String id = dir.getId();
			VFile file = this.getFileForNode(dir);
			return new DTDirectoryImpl(this.workspace, id, file, required);
		}

		private DTFile createFile(T_file t_file) {
			boolean required = t_file.isRequired();
			String id = t_file.getId();
			VFile file = this.getFileForNode(t_file);
			return new DTFileImpl(this.workspace, id, file, required);
		}

		private VFile getFileForNode(T_node node) {
			Stack<T_node> stack = new Stack<T_node>();
			T_node pn = node;
			for (; pn != null; pn = pn.getParent()) {
				stack.push(pn);
			}
			stack.pop();
			StringBuilder sb = new StringBuilder();
			for (; stack.size() > 0;) {
				T_node n = stack.pop();
				if (sb.length() > 0) {
					sb.append('/');
				}
				sb.append(n.getName());
			}
			String str = sb.toString();
			VFileSystem fs = this.root_file.getVFS();
			return fs.newFile(this.root_file, str);
		}

		private void addFile(List<DTNode> list, T_file file) {
			DTFile fileNode = this.createFile(file);
			list.add(fileNode);
		}
	}

	static class WorkspaceFacade implements DTWorkspace {

		private DTWorkspace core;

		@Override
		public DTFile findFileById(String id) {
			return core.findFileById(id);
		}

		@Override
		public DTWorkspace getWorkspace() {
			return this;
		}

		@Override
		public DTDirectory findDirectoryById(String id) {
			return core.findDirectoryById(id);
		}

		@Override
		public VFile getFile() {
			return core.getFile();
		}

		@Override
		public String getId() {
			return core.getId();
		}

		@Override
		public DTWorkspaceFactory getFactory() {
			return core.getFactory();
		}

		@Override
		public boolean init() {
			return core.init();
		}

		@Override
		public boolean check() {
			return core.check();
		}

		@Override
		public boolean repair() {
			return core.repair();
		}

		@Override
		public List<DTNode> listNodes() {
			return core.listNodes();
		}

	}

	class WorkspaceCore implements DTWorkspace {

		private final Map<String, DTNode> mNodeMap = new HashMap<String, DTNode>();
		private final List<DTNode> mNodeList;

		public WorkspaceCore(List<DTNode> list) {
			this.mNodeList = list;
			for (DTNode node : list) {
				String id = node.getId() + "";
				this.mNodeMap.put(id, node);
			}
		}

		@Override
		public DTWorkspace getWorkspace() {
			return this;
		}

		@Override
		public VFile getFile() {
			return DTWorkspaceBuilder.this.mFile;
		}

		@Override
		public boolean init() {
			List<DTNode> list = this.mNodeList;
			for (DTNode node : list) {
				if (!node.init()) {
					return false;
				}
			}
			return true;
		}

		@Override
		public boolean check() {
			List<DTNode> list = this.mNodeList;
			for (DTNode node : list) {
				if (!node.check()) {
					return false;
				}
			}
			return true;
		}

		@Override
		public boolean repair() {
			List<DTNode> list = this.mNodeList;
			for (DTNode node : list) {
				if (!node.repair()) {
					return false;
				}
			}
			return true;
		}

		@Override
		public DTFile findFileById(String id) {
			DTNode node = this.mNodeMap.get(id);
			if (node instanceof DTFile) {
				return (DTFile) node;
			} else {
				return null;
			}
		}

		@Override
		public DTDirectory findDirectoryById(String id) {
			DTNode node = this.mNodeMap.get(id);
			if (node instanceof DTDirectory) {
				return (DTDirectory) node;
			} else {
				return null;
			}
		}

		@Override
		public DTWorkspaceFactory getFactory() {
			return DTWorkspaceBuilder.this.mTemp;
		}

		@Override
		public String getId() {
			return "";
		}

		@Override
		public List<DTNode> listNodes() {
			return new ArrayList<DTNode>(this.mNodeList);
		}
	}

}
