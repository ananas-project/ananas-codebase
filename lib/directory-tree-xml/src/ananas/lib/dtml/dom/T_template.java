package ananas.lib.dtml.dom;

import ananas.lib.dtml.DTWorkspace;
import ananas.lib.dtml.DTWorkspaceFactory;
import ananas.lib.dtml.impl.DTWorkspaceBuilder;
import ananas.lib.io.vfs.VFile;

public class T_template implements DTWorkspaceFactory {

	private T_node mRootNode;
	private T_node mMainNode;

	public void setRootNode(T_node node) {
		this.mRootNode = node;
	}

	@Override
	public DTWorkspace createWorkspace(VFile file) {
		DTWorkspaceBuilder builder = new DTWorkspaceBuilder(this, file);
		return builder.build();
	}

	public void setMain(T_node main) {
		this.mMainNode = main;
	}

	public T_node getRootNode() {
		return mRootNode;
	}

	public T_node getMainNode() {
		return mMainNode;
	}

}
