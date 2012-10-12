package ananas.lib.blueprint2.dom;

import ananas.lib.blueprint2.dom.helper.IImplementation;

public interface IDocument extends INode {

	IElement findElementById(String id);

	Object findTargetById(String id);

	void registerElement(IElement element);

	IElement getRootElement();

	void setRootElement(IElement root);

	IImplementation getImplementation();

	String getDocumentURI();

	IAttr createAttribute(String uri, String localName);

	IElement createElement(String uri, String localName);

	IElement createElement(Object target);

	IText createText(String data);

}
