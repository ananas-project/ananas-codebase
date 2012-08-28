package test.blueprint;

import ananas.lib.blueprint.tools.ResourceIdGen;

public class MyResourceIdGen {

	/**
	 * @param arg
	 *            in command-line call this with the parameter of
	 *            "-base-dir ${project_loc} -res-dir res -gen-dir gen -R-class test.blueprint.R -accept-file .xml+.png"
	 * */

	public static void main(String[] arg) {
		ResourceIdGen.main(arg);
	}

}
