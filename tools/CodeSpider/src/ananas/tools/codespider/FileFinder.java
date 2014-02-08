package ananas.tools.codespider;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

public class FileFinder {

	void find(File path, FileFilter filter, FileHandler h) {
		h.onBegin(path);
		this.__find(path, filter, h, 32);
		h.onEnd(path);
	}

	private void __find(File path, FileFilter filter, FileHandler h,
			int depthLimit) {

		if (depthLimit < 0) {
			throw new RuntimeException("path too deep : " + path);
		}
		if (!filter.accept(path)) {
			return;
		}
		if (path.isDirectory()) {
			h.onDirectory(path);
			String[] list = path.list();
			Arrays.sort(list);
			for (String name : list) {
				File ch = new File(path, name);
				this.__find(ch, filter, h, depthLimit - 1);
			}
		} else {
			h.onFile(path);
		}
	}

}
