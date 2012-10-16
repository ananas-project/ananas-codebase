package ananas.lib.blueprint2.swing_ex;

import java.io.File;

import ananas.lib.blueprint2.dom.IAttr;
import ananas.lib.blueprint2.swing.JTreeWrapper;

public class JDirectoryTreeWrapper extends JTreeWrapper {

	private IAttr mBasePath;

	@Override
	public boolean setAttribute(IAttr attr) {
		String name = attr.getBlueprintClass().getLocalName();
		if (name == null) {
			return false;

		} else if (name.equals("basepath")) {
			this.mBasePath = attr;

		} else {
			return super.setAttribute(attr);
		}
		return true;
	}

	@Override
	public void onTagBegin() {
		super.onTagBegin();

		final JDirectoryTree dtree = (JDirectoryTree) this.getTarget(true);

		if (this.mBasePath != null) {
			dtree.setBasePath(new File(this.mBasePath.getValue()));
		}
	}

}
