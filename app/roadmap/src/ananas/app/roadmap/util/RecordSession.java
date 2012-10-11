package ananas.app.roadmap.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.TimeZone;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class RecordSession {

	private static final int s_time_step = 1000;
	private static final float s_distance_step = 5.0f;

	private final Context mContext;

	private final MyLocationListener mGpsListener = new MyLocationListener();
	private final MyLocationListener mNetListener = new MyLocationListener();
	private final File mFile;
	private FileOutputStream mFOS;
	private MyWriter mWriter;

	public RecordSession(Context context) {
		this.mContext = context;

		File basepath = this._genBasePath();
		String filename = this._genFileName();
		this.mFile = new File(basepath, filename);

	}

	private String _genFileName() {
		return ("rec_since_" + this._getCurrentTimeString() + ".txt");
	}

	private File _genBasePath() {
		return RoadmapFileManager.Factory.getInstance().getRecordDirectory();
	}

	public void open() {

		// open file

		try {
			File file = this.mFile;
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			this.mFOS = new FileOutputStream(file);

			this.mWriter = new MyWriter(this.mFOS);
			this.mWriter.writeHeader();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// listen

		LocationManager lm = (LocationManager) this.mContext
				.getSystemService(Context.LOCATION_SERVICE);

		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				RecordSession.s_time_step, RecordSession.s_distance_step,
				this.mGpsListener);

		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				RecordSession.s_time_step, RecordSession.s_distance_step,
				this.mNetListener);

	}

	public void close() {

		LocationManager lm = (LocationManager) this.mContext
				.getSystemService(Context.LOCATION_SERVICE);

		lm.removeUpdates(this.mNetListener);
		lm.removeUpdates(this.mGpsListener);

		this.mWriter.writeEnd();

		try {
			this.mFOS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getFullPath() {
		File file = this.mFile;
		if (file == null)
			return null;
		return this.mFile.getAbsolutePath();
	}

	private String _getCurrentTimeString() {
		final Calendar cale = Calendar.getInstance(TimeZone.getTimeZone(""));
		cale.setTimeInMillis(System.currentTimeMillis());
		final int yy, mm, dd, h, m, s;
		dd = cale.get(Calendar.DAY_OF_MONTH);
		mm = cale.get(Calendar.MONTH);
		yy = cale.get(Calendar.YEAR);
		h = cale.get(Calendar.HOUR_OF_DAY);
		m = cale.get(Calendar.MINUTE);
		s = cale.get(Calendar.SECOND);
		return (yy + "" + _add02(mm + 1) + "" + _add02(dd) + "-" + _add02(h)
				+ "" + _add02(m) + "" + _add02(s));
	}

	private String _add02(int n) {
		String s = n + "";
		if (s.length() == 1) {
			s = '0' + s;
		}
		return s;
	}

	class MyWriter {

		private final PrintStream mps;

		public MyWriter(OutputStream os) {
			this.mps = new PrintStream(os);
		}

		public void writeHeader() {
			String str = "";

			str += ("\"" + "timestamp" + "\"" + "\t");
			str += ("\"" + "longitude" + "\"" + "\t");
			str += ("\"" + "latitude" + "\"" + "\t");
			str += ("\"" + "altitude" + "\"" + "\t");
			str += ("\"" + "source" + "\"" + "\t");
			str += ("\"" + "accuracy" + "\"" + "\t");

			mps.println(str);
		}

		public void writeRec(Location location) {

			String source = location.getProvider();
			long time = location.getTime();
			double lon = location.getLongitude();
			double lat = location.getLatitude();
			double alt = location.getAltitude();
			float acc = location.getAccuracy();

			String str = "";
			String httime = HttpTimeStampConvertor.getInstance()
					.millisecondToString(time);

			str += ("\"" + httime + "\"" + "\t");
			str += (lon + "\t");
			str += (lat + "\t");
			str += (alt + "\t");
			str += ("\"" + source + "\"" + "\t");
			str += (acc + "\t");

			mps.println(str);
		}

		public void writeEnd() {
			mps.println("[end]");
		}
	}

	class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			RecordSession.this.mWriter.writeRec(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

}
