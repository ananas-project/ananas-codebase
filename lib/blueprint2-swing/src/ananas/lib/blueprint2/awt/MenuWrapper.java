package ananas.lib.blueprint2.awt;

import java.awt.Menu;
import java.awt.MenuItem;

import ananas.lib.blueprint2.dom.INode;

public class MenuWrapper extends MenuItemWrapper {

	@Override
	public boolean appendChild(INode child) {

		if (child instanceof MenuItemWrapper) {
			MenuItem item = (MenuItem) ((MenuItemWrapper) child)
					.getTarget(true);
			Menu menu = (Menu) this.getTarget(true);
			menu.add(item);

		} else {
			return super.appendChild(child);
		}
		return true;
	}

}
