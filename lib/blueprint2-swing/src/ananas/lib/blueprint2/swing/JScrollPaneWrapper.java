package ananas.lib.blueprint2.swing;

import java.awt.Component;

import javax.swing.JScrollPane;

import ananas.lib.blueprint2.awt.ComponentWrapper;
import ananas.lib.blueprint2.dom.INode;

public class JScrollPaneWrapper extends JComponentWrapper {

	public boolean appendChild(INode ch) {
		if (ch == null) {
			return false;
		} else if (ch instanceof ComponentWrapper) {
			ComponentWrapper compW = (ComponentWrapper) ch;
			Component comp = compW.getComponent(true);
			JScrollPane sp = this.getJScrollPane(true);
			sp.setViewportView(comp);
		} else {
			return super.appendChild(ch);
		}
		return true;
	}

	public JScrollPane getJScrollPane(boolean create) {
		return (JScrollPane) this.getTarget(create);
	}

}
