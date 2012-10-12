package ananas.lib.blueprint2.dom;

import java.util.List;

/**
 * this class MUST has a Default() constructor
 * */

public interface IElement extends INode {

	String getId();

	Object getTarget();

	Object getTarget(boolean create);

	Object createTarget();

	IDocument getOwnerDocument();

	boolean bindTarget(Object target);

	boolean bindOwnerDocument(IDocument ownerDoc);

	boolean setAttribute(IAttr attr);

	boolean appendChild(INode child);

	void tagBegin();

	void tagEnd();

	IElement getParent();

	List<INode> listChildren();

	List<IAttr> listAttributes();

	/**
	 * set new parent and return old parent.
	 * 
	 * @param parent
	 *            the new parent.
	 * @return the old parent.
	 * */
	IElement setParent(IElement parent);

}
