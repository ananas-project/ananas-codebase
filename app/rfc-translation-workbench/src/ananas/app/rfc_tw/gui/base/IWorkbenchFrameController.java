package ananas.app.rfc_tw.gui.base;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;

import ananas.lib.blueprint.elements.awt.util.IEventChainNode;
import ananas.lib.blueprint.elements.swing.IEJMenuBar;

public interface IWorkbenchFrameController {

	void setMenuBar(IEJMenuBar mb);

	IEJMenuBar getMenuBar();

	void addChildFrame(IChildFrameController child);

	JDesktopPane getJDesktopPane();

	JFrame getJFrame();

	IEventChainNode getEventChainNode();

	IViewController getCurrentView();

	void setCurrentView(IViewController vc);

}
