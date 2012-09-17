package ananas.app.rfc_tw.ui.console;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import ananas.app.rfc_tw.model.IProject;

public interface ConsoleCore {

	class Agent {

		public static ConsoleCore getCore() {
			if (sInst == null) {
				sInst = new MyImpl();
			}
			return sInst;
		}

		private static ConsoleCore sInst;

		private static class MyImpl implements ConsoleCore {

			private IProject mProject;
			private boolean mIsModified;
			private File mFile;

			@Override
			public boolean fileNew() {
				if (!this.fileClose())
					return false;
				IProject prj = IProject.Factory.newProject();
				this.mProject = prj;
				System.out.println("new project.");
				return true;
			}

			@Override
			public boolean fileSave() {
				File file = this.mFile;
				IProject prj = this.mProject;
				if (prj == null)
					return true;
				if (file == null) {
					System.out.println("no document file");
					return false;
				}
				try {
					prj.save(file);
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
				this.mFile = file;
				this.mIsModified = false;
				System.out.println("save to " + file.getAbsolutePath());
				return false;
			}

			@Override
			public boolean fileOpen(String path) {
				if (!this.fileClose())
					return false;
				File file = new File(path);
				IProject prj = IProject.Factory.newProject();
				try {
					prj.load(file);
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
				this.mFile = file;
				this.mProject = prj;
				System.out.println("open project " + path);
				return true;
			}

			@Override
			public boolean fileClose() {
				if (this.mIsModified) {
					if (this.mProject != null) {
						if (!this.fileSave()) {
							return false;
						}
					}
				}
				this.mIsModified = false;
				this.mFile = null;
				this.mProject = null;
				System.out.println("project closed.");
				return true;
			}

			@Override
			public boolean fileSaveAs(String path) {
				File file = new File(path);
				if (file.isDirectory()) {
					System.out.println("error : the path is a directory.");
					return false;
				}
				if (!file.getParentFile().exists()) {
					System.out
							.println("error : the parent directory is not existed.");
					return false;
				}
				this.mFile = file;
				return this.fileSave();
			}

			@Override
			public boolean fileImportOriginal(String path) {
				File file = new File(path);
				String text = "";
				try {
					FileInputStream fis = new FileInputStream(file);
					InputStreamReader rdr = new InputStreamReader(fis, "utf-8");
					StringBuilder sb = new StringBuilder(1024);
					char[] cbuf = new char[128];
					for (int cc = rdr.read(cbuf); cc > 0; cc = rdr.read(cbuf)) {
						sb.append(cbuf, 0, cc);
					}
					text = sb.toString();
					rdr.close();
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				IProject prj = this.mProject;
				prj.setOriginalText(text);
				prj.scanWords();
				prj.scanSentences();
				this.mIsModified = true;
				System.out.println("import original from "
						+ file.getAbsolutePath());
				return true;
			}

			@Override
			public IProject getProject() {
				return this.mProject;
			}

		}

	}

	boolean fileNew();

	boolean fileSave();

	boolean fileOpen(String path);

	boolean fileClose();

	boolean fileSaveAs(String path);

	boolean fileImportOriginal(String path);

	IProject getProject();

}
