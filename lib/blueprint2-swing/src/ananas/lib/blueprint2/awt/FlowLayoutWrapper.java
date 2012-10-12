package ananas.lib.blueprint2.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;

public class FlowLayoutWrapper extends ObjectWrapper implements
		LayoutManagerWrapper {

	public FlowLayoutWrapper() {
	}

	@Override
	public LayoutManager getLayoutManager(boolean create) {
		return (LayoutManager) this.getTarget(create);
	}

	@Override
	public void addComponentToContainer(Component component,
			Container container, String position) {

		container.add(component);
	}

}
