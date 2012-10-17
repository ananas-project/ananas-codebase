package ananans.app.timespaceterm;

import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import ananas.lib.blueprint2.dom.IDocument;

public class MyFileListView {

	private final JList mJList;
	private File[] mFiles;

	public MyFileListView(IDocument doc, String id) {
		if (id == null)
			id = this.getClass().getSimpleName();
		this.mJList = (JList) doc.findTargetById(id);
	}

	public JList getJList() {
		return this.mJList;
	}

	public File[] getFiles() {
		return this.mFiles;
	}

	public void setFiles(File[] files) {
		if (files != null) {
			DefaultListModel model = new DefaultListModel();
			for (File file : files) {
				if (file != null) {
					MyFileWrapper fw = new MyFileWrapper(file);
					model.addElement(fw);
				}
			}
			this.mJList.setModel(model);
		}
	}

	class MyFileWrapper {

		private final File mFile;

		public MyFileWrapper(File file) {
			this.mFile = file;
		}

		public String toString() {
			return this.mFile.getName();
		}
	}

}
