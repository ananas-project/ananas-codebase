package ananas.app.roadmap.util;

import java.io.File;

public interface RoadmapFileManager {

	File getRootDirectory();

	File getRecordDirectory();

	File getKmlDirectory();

	public class Factory {

		private static RoadmapFileManager sInst;

		public static RoadmapFileManager getInstance() {
			if (sInst == null) {
				sInst = new MyImpl();
			}
			return sInst;
		}

	}

	static class MyImpl implements RoadmapFileManager {

		private File mRootDir;
		private File mRecDir;
		private File mKmlDir;

		@Override
		public File getRootDirectory() {
			File dir = this.mRootDir;
			if (dir == null) {
				File sdcard = android.os.Environment
						.getExternalStorageDirectory();
				dir = new File(sdcard, "ananas/roadmap");
				this.mRootDir = dir;
			}
			return dir;
		}

		@Override
		public File getRecordDirectory() {
			File dir = this.mRecDir;
			if (dir == null) {
				this.mRecDir = dir = new File(this.getRootDirectory(), "record");
			}
			return dir;
		}

		@Override
		public File getKmlDirectory() {
			File dir = this.mKmlDir;
			if (dir == null) {
				this.mKmlDir = dir = new File(this.getRootDirectory(), "kml");
			}
			return dir;
		}
	}

}
