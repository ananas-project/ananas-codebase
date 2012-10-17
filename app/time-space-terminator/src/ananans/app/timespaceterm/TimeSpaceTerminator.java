package ananans.app.timespaceterm;

import ananas.lib.blueprint2.Blueprint2;
import ananas.lib.blueprint2.dom.helper.IBlueprint;

public class TimeSpaceTerminator {

	public static void main(String args[]) {

		IBlueprint bp2 = Blueprint2.getInstance();
		bp2.getConnector().getConnectionFactoryRegistrar().printItems();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				(new MainFrame()).show();
			}
		});
	}

}
