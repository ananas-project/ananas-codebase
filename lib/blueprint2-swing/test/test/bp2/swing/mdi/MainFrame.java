package test.bp2.swing.mdi;

import java.io.IOException;

import javax.swing.JFrame;

import ananas.lib.blueprint2.Blueprint2;
import ananas.lib.blueprint2.dom.IDocument;

public class MainFrame {

	public static void main(String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame mf = new MainFrame();
				mf.show();
			}
		});
	}

	private final JFrame mRoot;

	private MainFrame() {

		IDocument doc = null;
		try {
			String uri = R.file.mainframe_xml;
			doc = Blueprint2.getInstance().loadDocument(uri);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.mRoot = (JFrame) doc.findTargetById(R.id.root);

		this.mRoot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	protected void show() {
		this.mRoot.setVisible(true);
	}

}
