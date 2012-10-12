package ananas.app.roadmap;

import java.util.Hashtable;

import ananas.app.roadmap.util.RecordSession;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;

public class RoadmapService extends Service {

	public static interface IRoadmapService2Binder {

		String startRecording();

		String stopRecording();

		String currentRecording();

		void setProperty(String key, String value);

		String getProperty(String key);

		void exit();
	}

	private final MyBinder mBinder = new MyBinder();
	private final Hashtable<String, String> mProperties;
	private RecordSession mCurRec;

	public RoadmapService() {
		this.mProperties = new Hashtable<String, String>();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		System.out.println(this + ".onBind()");
		this._startForeground();
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
		this._setCurRec(null);
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
		this._startForeground();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		System.out.println(this + ".onStart()");
		super.onStart(intent, startId);
		this._startForeground();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println(this + ".onStartCommand()");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		System.out.println(this + ".onUnbind()");
		return super.onUnbind(intent);
	}

	private class MyBinder extends Binder implements IRoadmapService2Binder {

		@Override
		public String startRecording() {
			try {
				RecordSession newRec = new RecordSession(RoadmapService.this);
				RoadmapService.this._setCurRec(newRec);
				return newRec.getFullPath();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public String stopRecording() {
			RecordSession rec = RoadmapService.this._setCurRec(null);
			if (rec == null)
				return null;
			return rec.getFullPath();
		}

		@Override
		public void setProperty(String key, String value) {
			if (key != null && value != null)
				RoadmapService.this.mProperties.put(key, value);
		}

		@Override
		public String getProperty(String key) {
			return RoadmapService.this.mProperties.get(key);
		}

		@Override
		public String currentRecording() {
			RecordSession rec = RoadmapService.this.mCurRec;
			if (rec == null)
				return null;
			return rec.getFullPath();
		}

		@Override
		public void exit() {
			RoadmapService.this._setCurRec(null);
			RoadmapService.this._stopForeground();
		}

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
		int id = R.drawable.ic_launcher;
		String appName = this.getString(R.string.app_name);
		Notification notification = new Notification(id, appName, 0);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, RoadmapActivity.class), 0);
		notification.setLatestEventInfo(this, appName, null, contentIntent);
		this.startForeground(id, notification);
	}

}
