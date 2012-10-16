package ananas.lib.blueprint2.swing;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import ananas.lib.blueprint2.awt.FrameWrapper;
import ananas.lib.blueprint2.dom.INode;

public class JFrameWrapper extends FrameWrapper {

	@Override
	public boolean appendChild(INode child) {

		if (child == null) {
			return false;

		} else if (child instanceof JMenuBarWrapper) {
			JMenuBar menubar = (JMenuBar) ((JMenuBarWrapper) child)
					.getTarget(true);
			JFrame frame = (JFrame) this.getTarget(true);
			frame.setJMenuBar(menubar);

		} else {
			return super.appendChild(child);

		}
		return true;
	}

}
