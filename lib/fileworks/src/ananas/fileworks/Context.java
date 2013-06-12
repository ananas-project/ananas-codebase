package ananas.fileworks;

import ananas.lib.io.vfs.VFile;

public interface Context {

	Environment getEnvironment();

	ComponentManager getComponentManager();

	VFile getMainPath();
}
