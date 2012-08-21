package ananas.app.rfc_tw.gui;

import java.util.EventObject;

import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import ananas.app.rfc_tw.gui.base.IChildFrameController;
import ananas.app.rfc_tw.gui.base.IViewController;
import ananas.app.rfc_tw.gui.base.IWorkbenchFrameController;
import ananas.lib.blueprint.elements.awt.util.DefaultEventChainNode;
import ananas.lib.blueprint.elements.awt.util.IEventChainNode;

public class ChildFrameController implements IChildFrameController,
		InternalFrameListener {

	private final JInternalFrame mInFrame;

	public ChildFrameController(IWorkbenchFrameController workbench) {
		this.mWorkbench = workbench;
		this.mInFrame = new JInternalFrame("Untitled", true, true, true, true);
		this.mInFrame.setBounds(0, 0, 640, 480);

		this._setupListener();

	}

	private void _setupListener() {
		this.mInFrame.addInternalFrameListener(this);
	}

	@Override
	public JMenuBar getJMenuBar() {
		return null;
	}

	@Override
	public JInternalFrame getJInternalFrame() {
		return this.mInFrame;
	}

	private IViewController mContent;

	@Override
	public IViewController getContent() {
		return this.mContent;
	}

	@Override
	public void setContent(IViewController vc) {
		this.mContent = vc;
		this.mInFrame.getContentPane().add(vc.getView());
		IEventChainNode pNode = this.getEventChainNode();
		IEventChainNode cNode = vc.getEventChainNode();
		cNode.setNextNode(pNode);

		// this.getJInternalFrame().setJMenuBar(vc.getMenuBar().toJMenuBar());
	}

	@Override
	public IEventChainNode getEventChainNode() {
		return this.mEventChainNode;
	}

	protected EventObject onEvent(EventObject event) {
		return event;
	}

	private final IEventChainNode mEventChainNode = new DefaultEventChainNode() {

		@Override
		protected EventObject onEvent(EventObject event) {
			return ChildFrameController.this.onEvent(event);
		}
	};
	private final IWorkbenchFrameController mWorkbench;

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		this.getWorkbench().setCurrentView(null);
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		IViewController vc = this.getContent();
		this.getWorkbench().setCurrentView(vc);
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public IWorkbenchFrameController getWorkbench() {
		return this.mWorkbench;
	}
}
