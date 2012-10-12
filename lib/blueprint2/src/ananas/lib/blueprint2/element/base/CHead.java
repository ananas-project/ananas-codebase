package ananas.lib.blueprint2.element.base;

import java.util.ArrayList;
import java.util.List;

public class CHead {

	private final List<CLink> mLinkList;

	public CHead() {
		this.mLinkList = new ArrayList<CLink>();
	}

	public void addLink(CLink link) {
		this.mLinkList.add(link);
	}

}
