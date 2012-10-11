package ananas.app.roadmap.util.task;

import java.util.Hashtable;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ananas.app.roadmap.util.kml.dom.IKMLObject;
import ananas.app.roadmap.util.kml.dom.KMLDocument;
import ananas.app.roadmap.util.kml.dom.KMLFolder;
import ananas.app.roadmap.util.kml.dom.KMLPlacemark;
import ananas.app.roadmap.util.kml.dom.KMLPoint;
import ananas.app.roadmap.util.kml.dom.KML_coordinates;
import ananas.app.roadmap.util.kml.dom.KML_description;
import ananas.app.roadmap.util.kml.dom.KML_kml;
import ananas.app.roadmap.util.kml.dom.KML_name;
import ananas.app.roadmap.util.kml.dom.KML_obj_base;

public interface KMLObjectTreeBuilder {

	DefaultHandler getHandler();

	KML_kml getKmlRoot();

	public static class Factory {

		public static KMLObjectTreeBuilder newInstance() {
			return new MyImpl();
		}

	}

	class MyImpl implements KMLObjectTreeBuilder {

		private KML_kml mRoot;

		private MyImpl() {
		}

		@Override
		public DefaultHandler getHandler() {
			return new DefaultHandler() {

				private final Stack<IKMLObject> mObjectStack = new Stack<IKMLObject>();
				private final MyClassMapper mClassMapper = new MyClassMapper();

				@Override
				public void characters(char[] ch, int start, int length)
						throws SAXException {
					if (length <= 0)
						return;
					String text = new String(ch, start, length);
					text = text.trim();
					if (text.length() <= 0)
						return;
					IKMLObject obj = this.mObjectStack.peek();
					boolean rlt = obj.appendText(text);
					if (!rlt) {
						System.err.println("err:the object cannot accept text");
						System.err.println("    object = " + obj);
						System.err.println("      text = " + text);
					}
				}

				@Override
				public void endDocument() throws SAXException {

				}

				@Override
				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					final IKMLObject child = this.mObjectStack.pop();
					final IKMLObject parent;
					if (this.mObjectStack.empty())
						parent = null;
					else
						parent = this.mObjectStack.peek();
					if (parent == null) {
						MyImpl.this.mRoot = (KML_kml) child;
					} else {
						if (parent.appendChild(child)) {
							child.setParent(parent);
						} else {
							System.err
									.println("err:the parent cannot accept child");
							System.err.println("    parent = " + parent);
							System.err.println("     child = " + child);
						}
					}
				}

				@Override
				public void startDocument() throws SAXException {
					this.mObjectStack.removeAllElements();
				}

				@Override
				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					if (uri == null)
						uri = this._getDefaultNSURI();
					if (localName == null)
						localName = this._getLocalNameFromQName(qName);
					IKMLObject obj = this._createObject(uri, localName);
					this.mObjectStack.push(obj);
				}

				private IKMLObject _createObject(String uri, String localName) {
					Class<?> cls = this.mClassMapper.findClass(uri, localName);
					if (cls == null) {
						cls = KML_obj_base.class;
						System.err.println("err:cannot create kml object");
						System.err.println("    ns-uriobject = " + uri);
						System.err.println("      local-name = " + localName);
					} else {
						System.out.println("create " + cls.getName());
					}
					try {
						return (IKMLObject) cls.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new KML_obj_base();
				}

				private String _getDefaultNSURI() {
					return "";
				}

				private String _getLocalNameFromQName(String qName) {
					if (qName == null)
						return "";
					int index = qName.indexOf(':');
					if (index < 0)
						return qName;
					else
						return qName.substring(index + 1);
				}

			};
		}

		@Override
		public KML_kml getKmlRoot() {
			return this.mRoot;
		}
	}

	class MyClassMapper {

		private final Hashtable<String, Class<?>> mTable;

		private MyClassMapper() {
			this.mTable = new Hashtable<String, Class<?>>();
			this._registerAll();
		}

		public Class<?> findClass(String uri, String localName) {
			if (localName == null)
				return null;
			return this.mTable.get(localName);
		}

		private void _registerAll() {
			String uri = "http://earth.google.com/kml/2.x";

			this._registerClass(uri, "kml", /* ........ */KML_kml.class);
			this._registerClass(uri, "name", /* ....... */KML_name.class);
			this._registerClass(uri, "description", /*  */KML_description.class);
			this._registerClass(uri, "coordinates", /*  */KML_coordinates.class);

			this._registerClass(uri, "Document", /* ... */KMLDocument.class);
			this._registerClass(uri, "Folder", /* ..... */KMLFolder.class);
			this._registerClass(uri, "Placemark", /* .. */KMLPlacemark.class);
			this._registerClass(uri, "Point", /* ...... */KMLPoint.class);
		}

		private void _registerClass(String uri, String localName,
				Class<?> class1) {
			this.mTable.put(localName, class1);
		}
	}

}
