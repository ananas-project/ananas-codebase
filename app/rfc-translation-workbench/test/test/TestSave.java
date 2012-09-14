package test;

import java.io.IOException;

import ananas.app.rfc_tw.model.IProject;

public class TestSave extends TestProject {

	public static void main(String[] arg) {
		(new TestSave()).run();
		System.out.println("the end.");
	}

	private void run() {
		try {
			System.out.println(this + ".begin!");
			IProject prj = IProject.Factory.newProject();
			prj.setOriginalText(this.getOriginalText());
			prj.save(this.getDocumentFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
