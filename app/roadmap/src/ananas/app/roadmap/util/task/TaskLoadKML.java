package ananas.app.roadmap.util.task;

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import ananas.app.roadmap.util.RoadmapFileManager;
import ananas.app.roadmap.util.Task;
import ananas.app.roadmap.util.kml.dom.IOverlayItemEnumerator;
import ananas.app.roadmap.util.kml.dom.KML_kml;

import com.google.android.maps.OverlayItem;

public class TaskLoadKML implements Task {

	private MyItemList mItemList;

	@Override
	public void run() {
		File dir = RoadmapFileManager.Factory.getInstance().getKmlDirectory();
		if (!dir.exists()) {
			dir.mkdirs();
		}

		MyItemList list = new MyItemList();
		this.mItemList = list;
		this._loadKmlFilesInDir(dir, 6);
	}

	private void _loadKmlFilesInDir(File dir, int depthLimit) {
		if (depthLimit <= 0) {
			System.err.println(this
					+ "._loadKmlFilesInDir() - dir is too deep!");
			return;
		}
		for (final File ch : dir.listFiles()) {
			if (ch.exists()) {
				if (ch.isDirectory()) {
					this._loadKmlFilesInDir(ch, depthLimit - 1);
				} else {
					this._loadKmlFile(ch);
				}
			}
		}
	}

	private void _loadKmlFile(File kmlfile) {

		String name = kmlfile.getName().toLowerCase();
		if (!name.endsWith(".kml")) {
			return;
		}
		System.out.println("load " + kmlfile.getAbsolutePath());

		try {
			KMLObjectTreeBuilder builder = KMLObjectTreeBuilder.Factory
					.newInstance();
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(kmlfile, builder.getHandler());

			MyItemList list = this.mItemList;
			KML_kml kml = builder.getKmlRoot();
			kml.listOverlayItems(list);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public OverlayItem[] listItems() {
		MyItemList il = this.mItemList;
		if (il == null) {
			return new OverlayItem[0];
		} else {
			return il.toArray();
		}
	}

	class MyItemList implements IOverlayItemEnumerator {

		private int mIndex;
		private final Vector<OverlayItem> mList;

		MyItemList() {
			this.mList = new Vector<OverlayItem>();
		}

		public OverlayItem[] toArray() {
			return mList.toArray(new OverlayItem[mList.size()]);
		}

		@Override
		public void append(OverlayItem item) {
			if (item == null) {
				return;
			}
			int index = (this.mIndex++);
			System.out.println("list item[" + index + "]" + item);
			this.mList.addElement(item);
		}
	}

}
