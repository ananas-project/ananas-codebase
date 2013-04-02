package test.dtml;

import ananas.lib.dtml.DTWorkspace;
import ananas.lib.dtml.DTWorkspaceFactory;
import ananas.lib.io.vfs.VFile;

public class TestDTML {

	public static void main(String[] arg) {

		DTWorkspaceFactory factory = (new DemoWorkspaceFactoryLoader())
				.getFactory();
		VFile file = VFile.Factory.getVFS().newFile("/tmp/test/dtml");
		DTWorkspace works = factory.createWorkspace(file);
		boolean rlt = works.init();
		System.out.println("init(" + file + ") return:" + rlt);

	}
}
