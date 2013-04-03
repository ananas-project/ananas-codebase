package ananas.lib.dtml.dom;

import ananas.lib.dtml.DTWorkspace;
import ananas.lib.dtml.DTWorkspaceFactory;
import ananas.lib.io.vfs.VFile;

public class T_template implements DTWorkspaceFactory {

	private T_node mRootNode;

	public void setRootNode(T_node node) {
		this.mRootNode = node;
	}

	@Override
	public DTWorkspace createWorkspace(VFile file) {
		DTWorkspaceBuilder builder = new DTWorkspaceBuilder(this, file);
		return builder.build();
	}

	public void setMain(T_node main) {
		// TODO Auto-generated method stub
		
	}

}
