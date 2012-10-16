package ananas.lib.blueprint2.swing;

import java.awt.Component;

import javax.swing.JSplitPane;

import ananas.lib.blueprint2.awt.Awt_positionWrapper;
import ananas.lib.blueprint2.awt.ComponentWrapper;
import ananas.lib.blueprint2.dom.IAttr;
import ananas.lib.blueprint2.dom.INode;

public class JSplitPaneWrapper extends JComponentWrapper {

	private String mCurrentPosition;
	private IAttr mOrientation;

	@Override
	public boolean appendChild(INode child) {

		if (child == null) {
			return false;

		} else if (child instanceof ComponentWrapper) {
			ComponentWrapper comp = (ComponentWrapper) child;
			this._addComp_2(comp);

		} else if (child instanceof Awt_positionWrapper) {
			Awt_positionWrapper pos = (Awt_positionWrapper) child;
			this.mCurrentPosition = pos.getValue();

		} else {
			return super.appendChild(child);
		}
		return true;
	}

	public JSplitPane getJSplitPane(boolean create) {
		return (JSplitPane) this.getTarget(create);
	}

	@Override
	public boolean setAttribute(IAttr attr) {
		String name = attr.getBlueprintClass().getLocalName();
		if (name == null) {
			return false;

		} else if (name.equals("orientation")) {
			this.mOrientation = attr;

		} else {
			return super.setAttribute(attr);
		}
		return true;
	}

	public void onTagBegin() {
		super.onTagBegin();
		JSplitPane my = this.getJSplitPane(true);
		if (this.mOrientation != null) {
			String ori = this.mOrientation.getValue();
			if ("VERTICAL".equalsIgnoreCase(ori)
					|| "vert".equalsIgnoreCase(ori)
					|| "v".equalsIgnoreCase(ori)
					|| "VERTICAL_SPLIT".equalsIgnoreCase(ori)) {

				my.setOrientation(JSplitPane.VERTICAL_SPLIT);
			} else {
				my.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
			}
		}
	}

	private void _addComp_2(ComponentWrapper compWrapper) {

		Component comp = compWrapper.getComponent(true);
		JSplitPane my = this.getJSplitPane(true);

		String pos = this.mCurrentPosition;
		if (pos == null) {
			return;

		} else if (pos.equalsIgnoreCase("top")) {
			my.setTopComponent(comp);
		} else if (pos.equalsIgnoreCase("bottom")) {
			my.setBottomComponent(comp);
		} else if (pos.equalsIgnoreCase("left")) {
			my.setLeftComponent(comp);
		} else if (pos.equalsIgnoreCase("right")) {
			my.setRightComponent(comp);
		}

	}

}
