package ananas.app.roadmap.util.kml;

import java.io.File;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ananas.app.roadmap.util.kml.dom.IKMLObject;
import ananas.app.roadmap.util.kml.dom.KMLDocument;
import ananas.app.roadmap.util.kml.dom.KMLFolder;
import ananas.app.roadmap.util.kml.dom.KMLPlacemark;
import ananas.app.roadmap.util.kml.dom.KMLPoint;
import ananas.app.roadmap.util.kml.dom.KML_kml;

public interface KmlDoc {

	IKMLObject getKMLObjectForElement(Element element);

	KML_kml getRoot();

	class Factory {
		public static KmlDoc load(File file) {
			try {
				MyImpl doc = new MyImpl(file);
				doc._load();
				return doc;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	static class MyImpl implements KmlDoc {

		private final Hashtable<String, IKMLObject> mTargetMapper;
		private final KMLDOMFactory mDomFactory = new KMLDOMFactory();

		private final File mFile;
		private Document mDomDoc;
		private KML_kml mRoot;
		private int mTargetIdGen;

		MyImpl(File file) {
			this.mFile = file;
			this.mTargetMapper = new Hashtable<String, IKMLObject>();
			this.mDomFactory.init();
		}

		void _load() throws Exception {
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(mFile);
			this.mDomDoc = doc;
		}

		IKMLObject _createKMLObjectForElement(Element element) {
			IKMLObject kmlobj = this.mDomFactory.createKMLObject(element);
			if (kmlobj == null)
				return null;
			// kmlobj.bindTarget(this, element);
			String targetId = this._genTargetId();
			element.setAttribute("targetId", targetId);
			this.mTargetMapper.put(targetId, kmlobj);
			return kmlobj;
		}

		private synchronized String _genTargetId() {
			return ("target-" + (this.mTargetIdGen++));
		}

		@Override
		public IKMLObject getKMLObjectForElement(Element element) {
			Document owner = element.getOwnerDocument();
			if (this.mDomDoc != owner) {
				return null;
			}
			String targetId = element.getAttribute("targetId") + "";
			IKMLObject kmlobj = this.mTargetMapper.get(targetId);
			if (kmlobj == null) {
				kmlobj = this._createKMLObjectForElement(element);
			}
			return kmlobj;
		}

		@Override
		public KML_kml getRoot() {
			KML_kml root = this.mRoot;
			if (root == null) {
				Element element = this.mDomDoc.getDocumentElement();
				root = (KML_kml) this.getKMLObjectForElement(element);
				this.mRoot = root;
			}
			return root;
		}
	}

	class KMLDOMFactory {

		final static String s_ns_uri = "unused";

		private final Hashtable<String, Class<?>> mClassTable;

		public KMLDOMFactory() {
			this.mClassTable = new Hashtable<String, Class<?>>();
		}

		public void init() {
			String nsuri = s_ns_uri;

			this._regClass(nsuri, "kml",/*--------*/KML_kml.class);
			this._regClass(nsuri, "Document", /*--*/KMLDocument.class);
			this._regClass(nsuri, "Point", /*-----*/KMLPoint.class);
			this._regClass(nsuri, "Folder", /*----*/KMLFolder.class);
			this._regClass(nsuri, "Placemark",/*--*/KMLPlacemark.class);

		}

		private void _regClass(String nsuri, String localName, Class<?> class1) {
			String key = nsuri + "#" + localName;
			this.mClassTable.put(key, class1);
		}

		public IKMLObject createKMLObject(Element element) {
			try {
				String key = s_ns_uri + "#" + element.getLocalName();
				Class<?> cls = this.mClassTable.get(key);
				return (IKMLObject) cls.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
