package ananas.lib.blueprint2.element.base;

import java.util.ArrayList;
import java.util.List;

public class BpHead {

	private final List<BpLink> mLinkList;
	private final List<BpImport> mImportList;

	public BpHead() {
		this.mLinkList = new ArrayList<BpLink>();
		this.mImportList = new ArrayList<BpImport>();
	}

	public void addLink(BpLink link) {
		this.mLinkList.add(link);
	}

	public void addImport(BpImport imp) {
		this.mImportList.add(imp);
	}

}
