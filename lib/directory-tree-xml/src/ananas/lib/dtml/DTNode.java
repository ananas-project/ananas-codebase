package ananas.lib.dtml;

import ananas.lib.io.vfs.VFile;

public interface DTNode {

	DTWorkspace getWorkspace();

	VFile getFile();

	String getId();

	boolean init();

	boolean check();

	boolean repair();

}
