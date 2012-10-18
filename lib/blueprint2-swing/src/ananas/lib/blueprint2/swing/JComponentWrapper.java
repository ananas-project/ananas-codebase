package ananas.lib.blueprint2.swing;

import java.awt.Color;

import javax.swing.JComponent;

import ananas.lib.blueprint2.awt.ContainerWrapper;
import ananas.lib.blueprint2.dom.IAttr;

public class JComponentWrapper extends ContainerWrapper {

	private IAttr mBackground;

	@Override
	public boolean setAttribute(IAttr attr) {
		String name = attr.getBlueprintClass().getLocalName();
		if (name == null) {
			return false;
		} else if (name.equals("background")) {
			this.mBackground = attr;
		} else {
			return super.setAttribute(attr);
		}
		return true;
	}

	protected void onTagBegin() {
		super.onTagBegin();
		JComponent comp = (JComponent) this.getTarget(true);
		if (this.mBackground != null) {
			Color color = this.colorFromAttr(this.mBackground);
			comp.setBackground(color);
		}
	}

}
