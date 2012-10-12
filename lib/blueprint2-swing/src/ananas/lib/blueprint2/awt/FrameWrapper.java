package ananas.lib.blueprint2.awt;

import java.awt.Frame;
import java.awt.MenuBar;

import ananas.lib.blueprint2.dom.IAttr;
import ananas.lib.blueprint2.dom.INode;

public class FrameWrapper extends WindowWrapper {

	private IAttr title;

	@Override
	public boolean setAttribute(IAttr attr) {
		String name = attr.getBlueprintClass().getLocalName();
		if (name == null) {
			return false;
		} else if (name.equals("title")) {
			this.title = attr;
		} else {
			return super.setAttribute(attr);
		}
		return true;
	}

	@Override
	public void tagBegin() {
		super.tagBegin();
		Frame frame = (Frame) this.getTarget(true);
		if (this.title != null) {
			String str = this.getLocalString(this.title.getValue());
			frame.setTitle(str);
		}
	}

	@Override
	public boolean appendChild(INode child) {

		if (child instanceof MenuBarWrapper) {
			MenuBar menubar = (MenuBar) ((MenuBarWrapper) child)
					.getTarget(true);
			Frame frame = (Frame) this.getTarget(true);
			frame.setMenuBar(menubar);

		} else {
			return super.appendChild(child);
		}
		return true;
	}

}
