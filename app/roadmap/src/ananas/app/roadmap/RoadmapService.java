package ananas.app.roadmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ananas.app.roadmap.util.RecordSession;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

public class RoadmapService extends Service {

	public static interface IRoadmapService2Binder {

		String record(String path);

		String stop();

		String getCurrentRecording();

		void setBasePath(String basePath);

		String getBasePath();

		void setActivityURI(String uri);

		String getActivityURI();

		long getStartTime();

		int getRecordingCount();
	}

	private final MyBinder mBinder = new MyBinder();
	private RecordSession mCurRec;
	public String mBasePath;
	public String mActivityURI;

	public RoadmapService() {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		System.out.println(this + ".onBind()");
		// this._startForeground();
		return this.mBinder;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		System.out.println(this + ".onConfigurationChanged()");
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		System.out.println(this + ".onCreate()");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		System.out.println(this + ".onDestroy()");
		// this._setCurRec(null);
		super.onDestroy();
	}

	@Override
	public void onLowMemory() {
		System.out.println(this + ".onLowMemory()");
		super.onLowMemory();
	}

	@Override
	public void onRebind(Intent intent) {
		System.out.println(this + ".onRebind()");
		super.onRebind(intent);
		// this._startForeground();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		System.out.println(this + ".onStart()");
		super.onStart(intent, startId);
		// this._startForeground();

		this._loadStatus();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println(this + ".onStartCommand()");
		// return
		super.onStartCommand(intent, flags, startId);
		return Service.START_STICKY;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		System.out.println(this + ".onUnbind()");
		return super.onUnbind(intent);
	}

	private class MyBinder extends Binder implements IRoadmapService2Binder {

		@Override
		public String record(String path) {
			return RoadmapService.this._startRecord(path);
		}

		@Override
		public String stop() {
			return RoadmapService.this._stopRecord();
		}

		@Override
		public void setBasePath(String basePath) {
			RoadmapService.this.mBasePath = basePath;
		}

		@Override
		public String getBasePath() {
			return RoadmapService.this.mBasePath;
		}

		@Override
		public void setActivityURI(String uri) {
			RoadmapService.this.mActivityURI = uri;
		}

		@Override
		public String getActivityURI() {
			return RoadmapService.this.mActivityURI;
		}

		@Override
		public long getStartTime() {
			RecordSession rec = RoadmapService.this._getCurRec();
			if (rec == null) {
				return 0;
			} else {
				return rec.getStartTime();
			}
		}

		@Override
		public String getCurrentRecording() {
			RecordSession rec = RoadmapService.this._getCurRec();
			if (rec == null) {
				return null;
			} else {
				return rec.getCurrentRecording();
			}
		}

		@Override
		public int getRecordingCount() {
			RecordSession rec = RoadmapService.this._getCurRec();
			if (rec == null) {
				return 0;
			} else {
				return rec.getRecordingCount();
			}
		}

	}

	private RecordSession _getCurRec() {
		return this.mCurRec;
	}

	public String _startRecord(String path) {
		this._startForeground();
		LocationManager lm = (LocationManager) this
				.getSystemService(Service.LOCATION_SERVICE);
		File file = null;
		{
			final String base = this._getBasePath();
			if (path == null) {
				long time = System.currentTimeMillis();
				path = "rec_" + time + ".txt";
			}
			file = new File(base, path);
		}
		String user = "xukun";
		long startTime = System.currentTimeMillis();
		int recCount = 0;
		RecordSession pnew = RecordSession.newInstance(lm, file, user,
				startTime, recCount);
		this._setCurRec(pnew);
		this._saveStatus();
		return pnew.getCurrentRecording();
	}

	private String _getBasePath() {
		String bp = this.mBasePath;
		if (bp == null) {
			File dir = Environment.getExternalStorageDirectory();
			bp = dir.getAbsolutePath() + "/ananas/roadmap/record";
			this.mBasePath = bp;
		}
		return bp;
	}

	public String _stopRecord() {
		this._stopForeground();
		RecordSession pold = this._setCurRec(null);
		this._saveStatus();
		if (pold == null)
			return null;
		return pold.getCurrentRecording();
	}

	private RecordSession _setCurRec(final RecordSession newRec) {
		final RecordSession oldRec;
		synchronized (this) {
			oldRec = this.mCurRec;
			this.mCurRec = newRec;
		}
		if (oldRec != null) {
			oldRec.close();
		}
		if (newRec != null) {
			newRec.open();
		}
		return oldRec;
	}

	private void _stopForeground() {
		System.out.println(this + "._stopForeground()");
		this.stopForeground(true);
	}

	private void _startForeground() {
		System.out.println(this + "._startForeground()");

		Intent intent = new Intent(this, RoadmapActivity.class);

		int id = R.drawable.ic_launcher;
		String appName = this.getString(R.string.app_name);
		Notification notification = new Notification(id, appName, 0);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, 0);
		notification.setLatestEventInfo(this, appName, null, contentIntent);
		this.startForeground(id, notification);
	}

	private void _loadStatus() {
		MyINI ini = new MyINI();
		ini.load();
		String fp = ini.mFilePath;
		if (fp != null) {
			if (fp.length() > 0) {

				LocationManager lm = (LocationManager) this
						.getSystemService(Service.LOCATION_SERVICE);
				File file = new File(fp);
				String user = "xukun";
				long startTime = ini.mStartTime;
				int recCount = ini.mCountLine;
				RecordSession rec = RecordSession.newInstance(lm, file, user,
						startTime, recCount);
				this._setCurRec(rec);
				this._startForeground();
			}
		}
	}

	private void _saveStatus() {
		MyINI ini = new MyINI();
		RecordSession rec = this._getCurRec();
		if (rec != null) {
			ini.mStartTime = rec.getStartTime();
			ini.mCountLine = rec.getRecordingCount();
			ini.mFilePath = rec.getCurrentRecording();
			ini.mUser = "xukun";
		}
		ini.save();
	}

	private static class MyINI {

		final Map<String, String> mTable;

		public MyINI() {
			this.mTable = new HashMap<String, String>();
		}

		public void load() {
			try {
				File file = this._getFile();
				if (!file.exists()) {
					return;
				}
				StringBuilder sb = new StringBuilder(32);
				FileInputStream is = new FileInputStream(file);
				InputStreamReader rdr = new InputStreamReader(is);
				for (int ch = rdr.read(); ch > 0; ch = rdr.read()) {
					switch (ch) {
					case 0x0a:
					case 0x0d:
						String s = sb.toString();
						sb.setLength(0);
						this.onLine(s);
						break;
					default:
						sb.append((char) ch);
						break;
					}
				}
				String s = sb.toString();
				this.onLine(s);
				rdr.close();
				is.close();
				this.memoryToTable(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void onLine(String s) {
			int index = s.indexOf(':');
			if (0 < index) {
				String key = s.substring(0, index);
				String value = s.substring(index + 1);
				this.mTable.put(key.trim(), value.trim());
			}
		}

		private File _getFile() {
			File file = Environment.getExternalStorageDirectory();
			return new File(file, "ananas/roadmap/settings/service-status.ini");
		}

		public void save() {
			try {
				this.memoryToTable(true);
				File file = this._getFile();
				file.getParentFile().mkdirs();
				file.createNewFile();
				FileOutputStream os = new FileOutputStream(file);
				OutputStreamWriter wtr = new OutputStreamWriter(os);
				Set<String> keys = this.mTable.keySet();
				for (String key : keys) {
					String value = this.mTable.get(key);
					wtr.append(key + ":" + value + "\n");
				}
				wtr.flush();
				wtr.close();
				os.flush();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public long mStartTime;
		public String mFilePath;
		public String mUser;
		public int mCountLine;

		private void memoryToTable(boolean m2t) {
			final String filePath = "file-path";
			final String startTime = "start-time";
			final String countLine = "count-line";
			final String userName = "user-name";
			if (m2t) {
				String fp = this.mFilePath;
				if (fp == null)
					fp = "";
				this.mTable.clear();
				this.mTable.put(filePath, fp + "");
				this.mTable.put(userName, this.mUser + "");
				this.mTable.put(startTime, this.mStartTime + "");
				this.mTable.put(countLine, this.mCountLine + "");
			} else {
				String fp = this.mTable.get(filePath);
				String st = this.mTable.get(startTime);
				String cl = this.mTable.get(countLine);
				String un = this.mTable.get(userName);
				if (fp.length() < 1)
					fp = null;
				this.mFilePath = fp;
				this.mCountLine = Integer.parseInt(cl);
				this.mStartTime = Long.parseLong(st);
				this.mUser = un;
			}
		}
	}

}
