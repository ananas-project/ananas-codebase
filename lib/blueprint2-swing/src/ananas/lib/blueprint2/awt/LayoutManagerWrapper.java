package ananas.lib.blueprint2.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;

public interface LayoutManagerWrapper {

	LayoutManager getLayoutManager(boolean create);

	void addComponentToContainer(Component component, Container container,
			String position);

}
