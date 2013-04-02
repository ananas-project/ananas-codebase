package ananas.lib.dtml;

import ananas.lib.io.vfs.VFile;

public interface DTNode {

	DTWorkspace getWorkspace();

	VFile getFile();

	boolean init();

	boolean check();

	boolean repair();

}
