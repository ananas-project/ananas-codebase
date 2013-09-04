package ananas.fileworks.node;

import java.util.Map;

public interface Tree extends TreeNode {

	Map<String, Object> getFeatures();

	TreeTemplate getTemplate();

	TreeNode getNode(String path);

}
