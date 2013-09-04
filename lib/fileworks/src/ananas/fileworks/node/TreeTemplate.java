package ananas.fileworks.node;

import java.util.Map;

import ananas.lib.io.vfs.VFile;

public interface TreeTemplate {

	String base = "/";

	Map<String, Object> getFeatures();

	Tree createTree(VFile base);

	TreeNodeT getNodeT(String path);

}
