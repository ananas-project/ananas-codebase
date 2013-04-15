package ananas.lib.dtml;

import java.util.List;

public interface DTWorkspace extends DTNode {

	DTFile findFileById(String id);

	DTDirectory findDirectoryById(String id);

	DTNode findNodeById(String id);

	DTWorkspaceFactory getFactory();

	List<DTNode> listNodes();
}
