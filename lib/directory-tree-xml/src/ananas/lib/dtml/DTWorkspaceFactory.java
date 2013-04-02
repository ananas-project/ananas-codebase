package ananas.lib.dtml;

import ananas.lib.io.vfs.VFile;

public interface DTWorkspaceFactory {

	DTWorkspace createWorkspace(VFile file);

}
