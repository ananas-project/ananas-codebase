package ananas.lib.blueprint2.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;

import ananas.lib.blueprint2.dom.INode;

public class ContainerWrapper extends ComponentWrapper {

	private LayoutManagerWrapper mLayoutManager;
	private String mCurrentPosition;

	@Override
	public boolean appendChild(INode child) {

		if (child == null) {
			return false;

		} else if (child instanceof ComponentWrapper) {
			ComponentWrapper comp = (ComponentWrapper) child;
			this._addComp(comp);

		} else if (child instanceof Awt_positionWrapper) {
			Awt_positionWrapper pos = (Awt_positionWrapper) child;
			this.mCurrentPosition = pos.getValue();

		} else if (child instanceof LayoutManagerWrapper) {
			LayoutManagerWrapper layoutW = (LayoutManagerWrapper) child;
			this.mLayoutManager = layoutW;
			Container cont = (Container) this.getTarget(true);
			LayoutManager layout = layoutW.getLayoutManager(true);
			cont.setLayout(layout);

		} else {
			return super.appendChild(child);
		}
		return true;
	}

	private void _addComp(ComponentWrapper compWrapper) {
		final LayoutManagerWrapper layout = this.mLayoutManager;
		Container cont = this.getContainer(true);
		Component comp = compWrapper.getComponent(true);
		if (layout == null) {
			cont.add(comp);
		} else {
			String pos = this.mCurrentPosition;
			layout.addComponentToContainer(comp, cont, pos);
		}
		this.mCurrentPosition = null;
	}

	private Container getContainer(boolean create) {
		return (Container) this.getTarget(create);
	}

}
