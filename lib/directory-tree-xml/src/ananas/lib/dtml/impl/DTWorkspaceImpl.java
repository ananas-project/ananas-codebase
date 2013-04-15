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
import ananas.lib.io.vfs.VFile;

public class DTWorkspaceImpl implements DTWorkspace {

	private final List<DTNode> mList;
	private final Map<String, DTNode> mMap;
	private final DTWorkspaceFactory mFactory;

	public DTWorkspaceImpl(DTWorkspaceFactory factory) {
		this.mFactory = factory;
		this.mList = new ArrayList<DTNode>();
		this.mMap = new HashMap<String, DTNode>();
	}

	@Override
	public DTWorkspace getWorkspace() {
		return null;
	}

	@Override
	public VFile getFile() {
		return null;
	}

	@Override
	public String getId() {
		return "";
	}

	@Override
	public boolean init() {
		for (DTNode node : this.mList) {
			if (!node.init()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean check() {
		for (DTNode node : this.mList) {
			if (!node.check()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean repair() {
		for (DTNode node : this.mList) {
			if (!node.repair()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public DTFile findFileById(String id) {
		DTNode node = this.mMap.get(id);
		return (DTFile) node;
	}

	@Override
	public DTDirectory findDirectoryById(String id) {
		DTNode node = this.mMap.get(id);
		return (DTDirectory) node;
	}

	@Override
	public DTNode findNodeById(String id) {
		DTNode node = this.mMap.get(id);
		return node;
	}

	@Override
	public DTWorkspaceFactory getFactory() {
		return this.mFactory;
	}

	@Override
	public List<DTNode> listNodes() {
		return new ArrayList<DTNode>(this.mList);
	}

	public void addNode(DTNode node) {
		this.mList.add(node);
		String id = node.getId() + "";
		this.mMap.put(id, node);
	}

}
