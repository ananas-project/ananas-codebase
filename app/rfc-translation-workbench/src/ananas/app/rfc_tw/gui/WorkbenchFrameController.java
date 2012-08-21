package ananas.app.rfc_tw.gui;

import java.awt.event.ActionEvent;
import java.util.EventObject;

import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import ananas.app.rfc_tw.gui.base.IChildFrameController;
import ananas.app.rfc_tw.gui.base.IViewController;
import ananas.app.rfc_tw.gui.base.IWorkbenchFrameController;
import ananas.app.rfc_tw.model.IProject;
import ananas.lib.blueprint.Blueprint;
import ananas.lib.blueprint.IDocument;
import ananas.lib.blueprint.elements.awt.util.DefaultEventChainNode;
import ananas.lib.blueprint.elements.awt.util.IEventChainNode;
import ananas.lib.blueprint.elements.swing.IEJMenuBar;

public class WorkbenchFrameController implements IWorkbenchFrameController {

	// private final IDocument mDoc;
	private final JFrame mMainFrame;
	private final JDesktopPane mDesktop;
	private final IEJMenuBar mDefaultMenuBar;
	private IEJMenuBar mCurrentMenuBar;

	private WorkbenchFrameController() {

		// load
		IDocument doc = Blueprint.getInstance().loadDocument(
				R.file.workbench_frame_xml);
		// this.mDoc = doc;

		JFrame mainFrame = (JFrame) doc.findTargetById(R.id.root_view);
		this.mMainFrame = mainFrame;
		this.mMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// load default menu bar
		IDocument menuDoc = Blueprint.getInstance().loadDocument(
				R.file.menu_xml);
		this.mDefaultMenuBar = (IEJMenuBar) menuDoc
				.findElementById(R.id.menu_bar);
		this.setMenuBar(this.mDefaultMenuBar);

		JDesktopPane desktop = (JDesktopPane) doc.findTargetById(R.id.desktop);
		this.mDesktop = desktop;

		this._setupEventHandlers();

	}

	private void _setupEventHandlers() {

	}

	private void _exeCmdFileNewProject() {

		IProject project = IProject.Factory.newProject();
		NewProjectDialogController dlg = new NewProjectDialogController(project);
		JDialog jdlg = dlg.getJDialog();
		jdlg.setModal(true);
		jdlg.setVisible(true);
		if (project.getOriginalText() == null) {
			return;
		}

		IViewController prjView = new ProjectViewController(project);
		IChildFrameController childFrame = new ChildFrameController(this);
		childFrame.setContent(prjView);
		this.addChildFrame(childFrame);

	}

	public void show() {
		this.mMainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.mMainFrame.setVisible(true);
	}

	public static void showFrame() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				WorkbenchFrameController wfc = new WorkbenchFrameController();
				wfc.show();
			}
		});
	}

	@Override
	public void addChildFrame(IChildFrameController child) {
		JInternalFrame iframe = child.getJInternalFrame();
		this.mDesktop.add(iframe);
		iframe.setVisible(true);
		IEventChainNode cNode = child.getEventChainNode();
		IEventChainNode pNode = this.getEventChainNode();
		cNode.setNextNode(pNode);
	}

	@Override
	public JDesktopPane getJDesktopPane() {
		return this.mDesktop;
	}

	@Override
	public IEventChainNode getEventChainNode() {
		return this.mEventChainNode;
	}

	protected EventObject onEvent(EventObject event) {
		if (event instanceof ActionEvent) {
			return this.onActionEvent((ActionEvent) event);
		} else {
			return event;
		}
	}

	private ActionEvent onActionEvent(ActionEvent event) {
		final String cmd = event.getActionCommand();
		if (cmd == null) {
			return event;
		} else if (cmd.equals(R.command.file_new)) {
			this._exeCmdFileNewProject();
		} else {
			return event;
		}
		// System.out.println(this + ".onActionEvent():" + event);
		return null;
	}

	private final IEventChainNode mEventChainNode = new DefaultEventChainNode() {

		@Override
		protected EventObject onEvent(EventObject event) {
			return WorkbenchFrameController.this.onEvent(event);
		}
	};
	private IViewController mCurViewCtrl;

	@Override
	public JFrame getJFrame() {
		return this.mMainFrame;
	}

	@Override
	public IEJMenuBar getMenuBar() {
		return this.mCurrentMenuBar;
	}

	@Override
	public void setMenuBar(IEJMenuBar mb) {
		if (mb == null) {
			mb = this.mDefaultMenuBar;
		}
		this.mCurrentMenuBar = mb;
		this.mMainFrame.setJMenuBar(mb.toJMenuBar());
		this._linkMenuChain();
	}

	@Override
	public IViewController getCurrentView() {
		return this.mCurViewCtrl;
	}

	@Override
	public void setCurrentView(IViewController vc) {
		this.mCurViewCtrl = vc;
		if (vc != null) {
			this.setMenuBar(vc.getMenuBar());
		} else {
			this.setMenuBar(null);
		}
		this._linkMenuChain();
	}

	private void _linkMenuChain() {
		final IEJMenuBar menubar = this.mCurrentMenuBar;
		final IViewController view = this.mCurViewCtrl;
		if (menubar != null) {
			IEventChainNode nn = null;
			if (view != null) {
				nn = view.getEventChainNode();
			} else {
				nn = this.getEventChainNode();
			}
			menubar.getEventChainNode().setNextNode(nn);
		}
	}
}
