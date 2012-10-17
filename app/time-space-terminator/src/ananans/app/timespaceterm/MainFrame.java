package ananans.app.timespaceterm;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import ananas.lib.blueprint2.Blueprint2;
import ananas.lib.blueprint2.dom.IDocument;
import ananas.lib.blueprint2.dom.helper.IBlueprint;

public class MainFrame {

	private final JFrame mFrame;
	private final MyFileTreeView mFileTree;
	private final MyAddressBar mAddressBar;
	private final MyFileListView mFileList;

	public MainFrame() {
		IDocument doc;
		try {
			IBlueprint bp2 = Blueprint2.getInstance();
			doc = bp2.loadDocument("resource:///ui/"
					+ this.getClass().getSimpleName() + ".xml");
		} catch (IOException e) {
			doc = null;
			e.printStackTrace();
		}

		this.mFrame = (JFrame) doc.findTargetById("root");
		this.mFileTree = new MyFileTreeView(doc, null);
		this.mAddressBar = new MyAddressBar(doc, null);
		this.mFileList = new MyFileListView(doc, null);

		this.mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this._setListener();

	}

	private void _setListener() {

		this.mFileTree.getJTree().getSelectionModel()
				.addTreeSelectionListener(new TreeSelectionListener() {

					@Override
					public void valueChanged(TreeSelectionEvent event) {

						File file = MainFrame.this.mFileTree.getFile();
						MainFrame.this.setCurrentPath(file);

					}
				});

	}

	public void show() {
		this.mFrame.setVisible(true);
	}

	private static final FilenameFilter sFileFilter = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			File f = new File(dir, name);
			if (f.isDirectory())
				return false;
			else
				return true;
		}
	};

	final static Comparator<File> sFileComp = new Comparator<File>() {

		@Override
		public int compare(File f0, File f1) {
			String name0 = f0.getName();
			String name1 = f1.getName();
			return name0.compareTo(name1);
		}
	};

	public void setCurrentPath(File file) {
		if (file == null) {
			return;
		}
		this.mAddressBar.setFile(file);
		final File[] files;
		if (file.isDirectory()) {
			files = file.listFiles(sFileFilter);
		} else {
			files = new File[1];
			files[0] = file;
		}
		Comparator<? super File> comp = sFileComp;
		Arrays.sort(files, comp);
		this.mFileList.setFiles(files);
	}

}
