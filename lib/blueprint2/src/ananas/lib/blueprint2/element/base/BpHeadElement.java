package ananas.lib.blueprint2.element.base;

import ananas.lib.blueprint2.dom.INode;

public class BpHeadElement extends BaseElement {

	@Override
	public boolean appendChild(INode child) {

		if (child == null) {
			return false;

		} else if (child instanceof BpLinkElement) {
			BpHead head = (BpHead) this.getTarget(true);
			BpLink link = (BpLink) ((BpLinkElement) child).getTarget(true);
			head.addLink(link);
			
		} else if (child instanceof BpImportElement) {
			BpHead head = (BpHead) this.getTarget(true);
			BpImport imp  = (BpImport ) ((BpImportElement) child).getTarget(true);
			head.addImport (  imp );

		} else {
			return super.appendChild(child);

		}
		return true;
	}

}
