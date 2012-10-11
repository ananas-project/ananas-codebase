package ananas.lib.blueprint2;

import java.util.List;

import ananas.lib.blueprint2.dom.IAttr;
import ananas.lib.blueprint2.dom.IDocument;
import ananas.lib.blueprint2.dom.IElement;
import ananas.lib.blueprint2.dom.INode;

public class AbstractElement extends AbstractNode implements IElement {

	private IDocument mOwnerDoc;
	private IElement mParent;
	private Object mTarget;

	protected AbstractElement() {
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDocument getOwnerDocument() {
		return this.mOwnerDoc;
	}

	@Override
	public boolean bindTarget(Object target) {
		if (this.mTarget == null && target != null) {
			this.mTarget = target;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean bindOwnerDocument(IDocument ownerDoc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setAttribute(IAttr attr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void tagBegin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void tagEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public IElement getParent() {
		return this.mParent;
	}

	@Override
	public List<IAttr> listAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final IElement setParent(IElement parent) {
		IElement old = this.mParent;
		this.mParent = parent;
		return old;
	}

	@Override
	public boolean appendChild(INode child) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<INode> listChildren() {
		// TODO Auto-generated method stub
		return null;
	}

}
