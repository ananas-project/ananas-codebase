package ananas.app.roadmap.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Vector;

import ananas.app.roadmap.RoadmapService.IRoadmapService2Binder;
import android.location.GpsStatus.NmeaListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class RecordSession implements IRoadmapService2Binder, NmeaListener {

	private final LocationManager mLM;
	private final File mFile;
	private final long mStartTime;
	private final List<String> mBuffer;
	private final String mUserName;

	private long mFlushTime;
	private int mRecCount;
	private boolean mIsOpen;
	private boolean mIsClose;
	private final LocationListener mLocationListener;

	public static RecordSession newInstance(LocationManager lm, File file,
			String user, long startTime, int recCount) {

		return new RecordSession(lm, file, user, startTime, recCount);
	}

	private RecordSession(LocationManager lm, File file, String user,
			long startTime, int recCount) {
		this.mRecCount = recCount;
		this.mStartTime = startTime;
		this.mLM = lm;
		this.mFile = file;
		this.mBuffer = new Vector<String>();
		this.mUserName = user;
		this.mLocationListener = new MyLocationAdapter((NmeaListener) this);
	}

	public void open() {
		if (this.mIsOpen) {
			return;
		} else {
			this.mIsOpen = true;
		}
		LocationManager lm = this.mLM;
		// lm.addNmeaListener(this);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5,
				this.mLocationListener);
		this._flush(System.currentTimeMillis());
	}

	private void _flush(long timestamp) {
		try {
			// make header
			if (!this.mFile.exists()) {
				this.mFile.getParentFile().mkdirs();
				this.mFile.createNewFile();
			}
			boolean noHead = (this.mFile.length() == 0);
			FileOutputStream os = new FileOutputStream(this.mFile, true);
			OutputStreamWriter wtr = new OutputStreamWriter(os);
			if (noHead) {
				wtr.append("HTDF/1.0" + "\n");
				wtr.append("Content-Type:" + "text/nmea" + "\n");
				wtr.append("File-Name:" + this.mFile.getName() + "\n");
				wtr.append("User-Name:" + this.mUserName + "\n");
				wtr.append("Start-Time:" + this.mStartTime + "\n");
				wtr.append("\n");
			}
			// append
			List<String> buf = this.mBuffer;
			for (String line : buf) {
				wtr.append(line + '\n');
			}
			wtr.flush();
			wtr.close();
			os.flush();
			os.close();
			this.mFlushTime = timestamp;
			this.mBuffer.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (!this.mIsOpen) {
			return;
		}
		if (this.mIsClose) {
			return;
		} else {
			this.mIsClose = true;
		}
		LocationManager lm = this.mLM;
		// lm.removeNmeaListener(this);
		lm.removeUpdates(this.mLocationListener);
		this._flush(System.currentTimeMillis());
	}

	@Override
	public String record(String path) {
		return this.mFile.getAbsolutePath();
	}

	@Override
	public String stop() {
		return this.mFile.getAbsolutePath();
	}

	@Override
	public String getCurrentRecording() {
		return this.mFile.getAbsolutePath();
	}

	@Override
	public void setBasePath(String basePath) {
	}

	@Override
	public String getBasePath() {
		return this.mFile.getParentFile().getAbsolutePath();
	}

	@Override
	public void setActivityURI(String uri) {
	}

	@Override
	public String getActivityURI() {
		return null;
	}

	@Override
	public long getStartTime() {
		return this.mStartTime;
	}

	@Override
	public int getRecordingCount() {
		return this.mRecCount;
	}

	@Override
	public void onNmeaReceived(long time, String line) {
		this.mRecCount++;
		this.mBuffer.add(line);
		if ((this.mFlushTime + (1000 * 20)) < time) {
			this._flush(time);
		}
		if (this.mBuffer.size() > 100) {
			this._flush(time);
		}
	}

	static class MyLocationAdapter implements LocationListener {

		private final NmeaListener mNMEAListener;

		public MyLocationAdapter(NmeaListener nmeaListener) {
			this.mNMEAListener = nmeaListener;
		}

		@Override
		public void onLocationChanged(Location loc) {

			StringBuilder sb = new StringBuilder(128);
			sb.append(loc.getTime() + ",");
			sb.append(loc.getLongitude() + ",");
			sb.append(loc.getLatitude() + ",");
			sb.append(loc.getAltitude() + ",");
			sb.append(loc.getAccuracy() + ",");
			sb.append(loc.getProvider() + ",");
			sb.append(loc.getSpeed() + ",");

			this.mNMEAListener.onNmeaReceived(loc.getTime(), sb.toString());
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
	}

}
