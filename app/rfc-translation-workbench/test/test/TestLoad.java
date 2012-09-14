package test;

import java.io.IOException;
import java.io.InputStream;

import ananas.app.rfc_tw.model.IProject;

public class TestLoad extends TestProject {

	public static void main(String[] arg) {
		(new TestLoad()).run();
		System.out.println("the end.");
	}

	private void run() {
		try {
			System.out.println(this + ".begin!");
			InputStream is = TestLoad.class
					.getResourceAsStream("openid-authentication-2_0.xml");
			IProject prj = IProject.Factory.newProject();
			prj.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
