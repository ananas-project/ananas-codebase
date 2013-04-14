package test.dtml;

import java.util.List;

import ananas.lib.dtml.DTDirectory;
import ananas.lib.dtml.DTFile;
import ananas.lib.dtml.DTNode;
import ananas.lib.dtml.DTWorkspace;
import ananas.lib.dtml.DTWorkspaceFactory;
import ananas.lib.io.vfs.VFile;

public class TestDTML {

	public static void main(String[] arg) {

		DTWorkspaceFactory factory = (new DemoWorkspaceFactoryLoader())
				.getFactory();
		VFile file = VFile.Factory.getVFS()
				.newFile("/tmp/test/dtml/.snowflake");
		DTWorkspace works = factory.createWorkspace(file);

		boolean rlt;

		rlt = works.check();
		System.out.println("check(" + file + ") return:" + rlt);

		rlt = works.init();
		System.out.println("init(" + file + ") return:" + rlt);

		List<DTNode> nodes = works.listNodes();
		for (DTNode node : nodes) {
			VFile vf = node.getFile();
			System.out.println(vf + "");
		}

		System.out.println("find:");
		DTDirectory dir2 = works.findDirectoryById("shadows_dir");
		DTFile file2 = works.findFileById("ignore_file");
		System.out.println("find: " + dir2);
		System.out.println("find: " + file2);

	}
}
