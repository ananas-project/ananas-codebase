package test.blueprint;

import ananas.lib.blueprint.tools.ResourceIdGen;

public class MyResourceIdGen {

	/**
	 * @param arg
	 *            in command-line call this with the parameter of
	 *            "-base-dir ${project_loc} -res-dir test -gen-dir gen -R-class test.blueprint.R -accept-file .xml+.png -accept-attr id"
	 * */

	public static void main(String[] arg) {
		ResourceIdGen.main(arg);
	}

}
