package ananans.app.timespaceterm;

import java.io.File;

import javax.swing.JTextField;

import ananas.lib.blueprint2.dom.IDocument;

public class MyAddressBar {

	private final JTextField mJTextField;
	private File mFile;

	public MyAddressBar(IDocument doc, String id) {
		if (id == null)
			id = this.getClass().getSimpleName();
		this.mJTextField = (JTextField) doc.findTargetById(id);
	}

	public JTextField getJTextField() {
		return this.mJTextField;
	}

	public File getFile() {
		return this.mFile;
	}

	public void setFile(File file) {
		if (file != null) {
			this.mFile = file;
			this.mJTextField.setText(file.getAbsolutePath());
		}
	}

}
