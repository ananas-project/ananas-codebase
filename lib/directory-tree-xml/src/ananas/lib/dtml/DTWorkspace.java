package ananas.lib.dtml;

public interface DTWorkspace extends DTNode {

	DTFile getFileById(String id);

	DTDirectory getDirectoryById(String id);

	DTWorkspaceFactory getFactory();
}
