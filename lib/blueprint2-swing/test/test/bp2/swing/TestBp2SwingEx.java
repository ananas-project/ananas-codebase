package test.bp2.swing;

import javax.swing.JFrame;

import ananas.lib.blueprint2.Blueprint2;
import ananas.lib.blueprint2.dom.IDocument;
import ananas.lib.blueprint2.dom.helper.IBlueprint;

public class TestBp2SwingEx {

	public static void main(String[] args) {

		try {
			(new TestBp2SwingEx()).run();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void run() throws Exception {

		IBlueprint bp2 = Blueprint2.getInstance();

		bp2.getConnector().getConnectionFactoryRegistrar().printItems();

		IDocument doc = bp2.loadDocument("resource:///test-swing-ex.xml");
		// Object obj = doc.getRootElement().getTarget();
		bp2.saveDocument(doc);
		// doc = bp2.saveAsDocument("file:///", obj);

		JFrame frame = (JFrame) doc.findTargetById("root");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
