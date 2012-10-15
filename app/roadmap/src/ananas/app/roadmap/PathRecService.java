package ananas.app.roadmap;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ananas.app.roadmap.util.HTDFIO;
import ananas.app.roadmap.util.HTDFIO.HTDFReader;
import ananas.app.roadmap.util.HTDFIO.HTDFWriter;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

public class PathRecService extends Service {

	private String mLastURI;
	private MyBuffer mBuffer;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		this.mSettings.load();
		if (this.mSettings.mFilepath != null) {
			this._startForeground();
			this._startListen(new File(this.mSettings.mFilepath));
		}
	}

	private void _startListen(File output) {
		LocationManager lm = (LocationManager) this
				.getSystemService(Service.LOCATION_SERVICE);
		final MyBuffer newBuffer;
		if (output != null) {
			String provider = LocationManager.GPS_PROVIDER;
			long minTime = 1000;
			float minDistance = 10;
			LocationListener listener = this.mLocationListener;
			lm.requestLocationUpdates(provider, minTime, minDistance, listener);
			newBuffer = new MyBuffer(output);
		} else {
			lm.removeUpdates(this.mLocationListener);
			newBuffer = null;
		}
		final MyBuffer oldBuffer;
		synchronized (this) {
			oldBuffer = this.mBuffer;
			this.mBuffer = newBuffer;
		}
		if (oldBuffer != null) {
			oldBuffer.close();
		}
		if (newBuffer != null) {
			newBuffer.open();
		}
	}

	final LocationListener mLocationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location loc) {
			MyBuffer buffer = PathRecService.this.mBuffer;
			if (buffer != null) {
				buffer.append(loc.getTime() + ", " + loc);
			}
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub

		}
	};

	class MyBuffer {

		private final File mFile;
		long mLastFlushTime;
		final List<String> mList;

		public MyBuffer(File output) {
			this.mFile = output;
			this.mList = new ArrayList<String>();
		}

		public void open() {
			HTDFWriter wtr = HTDFIO.newWritter(this.mFile, true);
			wtr.setContentType("text/nmea");
			this._flush(wtr);
		}

		public void close() {
			this._flush(null);
		}

		private void _flush(HTDFWriter wtr) {
			this.mLastFlushTime = System.currentTimeMillis();
			try {

				if (wtr == null) {
					wtr = HTDFIO.newWritter(this.mFile, true);
				}
				OutputStream os = wtr.getOutputStream();
				for (String line : this.mList) {
					os.write(line.getBytes("utf-8"));
					os.write('\n');
				}
				os.flush();
				wtr.close();
				this.mList.clear();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void flush() {

			if (!this.mList.isEmpty())
				this._flush(null);

		}

		public void append(String line) {
			this.mList.add(line);
			if (!this.mList.isEmpty()) {
				if (this.mLastFlushTime + (20 * 1000) < System
						.currentTimeMillis()) {
					this._flush(null);
				} else if (this.mList.size() > 100) {
					this._flush(null);
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		this.mSettings.save();
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		String last = this.mLastURI;
		String data = intent.getDataString();
		String type = intent.getType();
		String scheme = intent.getScheme();
		this.mLastURI = data;

		System.out.println("");
		System.out.println("last.data          =" + last);
		System.out.println("onStartCommand.data=" + data);
		System.out.println("onStartCommand.scheme=" + scheme);
		System.out.println("onStartCommand.type=" + type);

		final Uri uri = Uri.parse(data);
		final String regHost = this.getClass().getName(); // "ananas.app.roadmap.PathRecService";
		if (regHost.equals(uri.getHost())) {
			final String path = uri.getPath();
			if (path == null) {
			} else if (path.equals("/start")) {
				this._start(uri);
			} else if (path.equals("/stop")) {
				this._stop(uri);
			}
		}

		super.onStartCommand(intent, flags, startId);
		return Service.START_STICKY;
	}

	private void _start(Uri uri) {
		try {
			this._startForeground();
			File file = this._genRecFile();
			this.mSettings.mFilepath = file.getAbsolutePath();
			this.mSettings.save();
			this._startListen(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _stop(Uri uri) {
		this._stopForeground();
		this.mSettings.mFilepath = null;
		this.mSettings.save();
		this._startListen(null);
	}

	private File _genRecFile() {

		Calendar cale = Calendar.getInstance();

		int yy = cale.get(Calendar.YEAR);
		int mm = cale.get(Calendar.MONTH);
		int dd = cale.get(Calendar.DAY_OF_MONTH);
		int h = cale.get(Calendar.HOUR_OF_DAY);
		int m = cale.get(Calendar.MINUTE);
		int s = cale.get(Calendar.SECOND);

		String fileName = "rec_" + _fmtInt(yy, 4) + _fmtInt(mm, 2)
				+ _fmtInt(dd, 2) + "_" + _fmtInt(h, 2) + _fmtInt(m, 2)
				+ _fmtInt(s, 2) + ".txt";

		File dir = android.os.Environment.getExternalStorageDirectory();
		return new File(dir, "ananas/roadmap/record/" + fileName);
	}

	public String _fmtInt(int n, int w) {
		String s = n + "";
		while ((s.length()) < (w)) {
			s = "0" + s;
		}
		return s;
	}

	final MySettings mSettings = new MySettings();

	static class MySettings {

		static final String filepath = "field.filepath";

		String mFilepath;

		public MySettings() {
		}

		public void save() {
			try {
				File file = this._getSettingsFile();
				HTDFWriter wtr = HTDFIO.newWritter(file, false);
				{
					wtr.setParameter(filepath, this.mFilepath + "");
				}
				wtr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void load() {

			try {
				File file = this._getSettingsFile();
				if (!file.exists()) {
					return;
				}
				HTDFReader rdr = HTDFIO.newReader(file);
				rdr.close();

				// load values
				String fpath = rdr.getParameter(filepath);

				// adjust
				if (fpath != null) {
					if (fpath.length() < 5) {
						fpath = null;
					}
				}

				this.mFilepath = fpath;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private File _getSettingsFile() {
			File dir = android.os.Environment.getExternalStorageDirectory();
			return new File(dir, "ananas/roadmap/settings/current.txt");
		}
	}

	private void _stopForeground() {
		System.out.println(this + "._stopForeground()");
		this.stopForeground(true);
	}

	private void _startForeground() {
		System.out.println(this + "._startForeground()");

		Intent intent = new Intent(this, PathRecActivity.class);

		int id = R.drawable.ic_launcher;
		String appName = this.getString(R.string.app_name);
		Notification notification = new Notification(id, appName, 0);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, 0);
		notification.setLatestEventInfo(this, appName, null, contentIntent);
		this.startForeground(id, notification);
	}

}
