package ananas.tools.codespider;

import java.io.File;

public interface FileHandler {

	void onBegin(File base);

	void onFile(File file);

	void onDirectory(File file);

	void onEnd(File base);

}
