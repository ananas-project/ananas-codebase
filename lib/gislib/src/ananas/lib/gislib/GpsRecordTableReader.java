package ananas.lib.gislib;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GpsRecordTableReader {

	String content_type = "application/gps-record-table";

	boolean hasMore();

	GpsRecord next() throws IOException;

	void close() throws IOException;

	class Factory {

		public static GpsRecordTableReader newReader(InputStream in)
				throws IOException {
			MyReader rdr = new MyReader(in);
			rdr._readHeader();
			return rdr;
		}

		private static interface FieldSetter {

			String the_empty = "";
			String the_default = "$FIELDS";

			String source = "source";
			String timestamp = "timestamp";
			String rtc_time = "rtc-time";

			String longitude = "longitude";
			String latitude = "latitude";
			String altitude = "altitude";
			String accuracy = "accuracy";
			String speed = "speed";
			String bearing = "bearing";

			void doSet(String value, MutableGpsRecord rec);

		}

		private static class MyReader implements GpsRecordTableReader {

			private final InputStream m_in;
			private GpsRecord mNext;
			private InputStreamReader m_reader;
			private int mCnt0a;
			private int mCnt0d;
			private StringBuilder mSB;
			// private String[] m_fields_array;
			private List<FieldSetter> m_fields_list;
			private Map<String, FieldSetter> m_fields_map;
			private List<String> m_string_array_buffer;

			public MyReader(InputStream in) {
				this.m_in = in;
				this.m_fields_map = this._genSetterMap();
			}

			private Map<String, FieldSetter> _genSetterMap() {
				Map<String, FieldSetter> map = new HashMap<String, FieldSetter>();

				map.put(FieldSetter.the_empty, new FieldSetter() {

					@Override
					public void doSet(String value, MutableGpsRecord rec) {
						// do nothing
					}
				});
				map.put(FieldSetter.the_default, new FieldSetter() {

					@Override
					public void doSet(String value, MutableGpsRecord rec) {
						// do nothing
					}
				});

				map.put(FieldSetter.source, new FieldSetter() {

					@Override
					public void doSet(String value, MutableGpsRecord rec) {
						rec.setSource(value);
					}
				});

				map.put(FieldSetter.longitude, new FieldSetter() {

					@Override
					public void doSet(String value, MutableGpsRecord rec) {
						rec.setLongitude(Double.parseDouble(value));
					}
				});
				map.put(FieldSetter.latitude, new FieldSetter() {

					@Override
					public void doSet(String value, MutableGpsRecord rec) {
						rec.setLatitude(Double.parseDouble(value));
					}
				});
				map.put(FieldSetter.altitude, new FieldSetter() {

					@Override
					public void doSet(String value, MutableGpsRecord rec) {
						rec.setAltitude(Double.parseDouble(value));
					}
				});
				map.put(FieldSetter.accuracy, new FieldSetter() {

					@Override
					public void doSet(String value, MutableGpsRecord rec) {
						rec.setAccuracy(Double.parseDouble(value));
					}
				});
				map.put(FieldSetter.speed, new FieldSetter() {

					@Override
					public void doSet(String value, MutableGpsRecord rec) {
						rec.setSpeed(Double.parseDouble(value));
					}
				});
				map.put(FieldSetter.bearing, new FieldSetter() {

					@Override
					public void doSet(String value, MutableGpsRecord rec) {
						rec.setBearing(Double.parseDouble(value));
					}
				});

				map.put(FieldSetter.timestamp, new FieldSetter() {

					@Override
					public void doSet(String value, MutableGpsRecord rec) {
						rec.setTimestamp(Long.parseLong(value));
					}
				});
				map.put(FieldSetter.rtc_time, new FieldSetter() {

					@Override
					public void doSet(String value, MutableGpsRecord rec) {
						rec.setRtcTime(Long.parseLong(value));
					}
				});

				return map;
			}

			private void _readHeader() throws IOException {

				Map<String, String> map = new HashMap<String, String>();
				String line = this._readLine();
				for (; line != null; line = this._readLine()) {
					line = line.trim();
					System.out.println("line : " + line);
					if (line.equals("")) {
						break;
					} else {
						int i = line.indexOf(':');
						if (i < 0) {
							// check as line0
							if (!line.equals("HTDF/1.0")) {
								throw new RuntimeException(
										"not a HTDF/1.0 document!");
							}
						} else {
							String key = line.substring(0, i).trim();
							String value = line.substring(i + 1).trim();
							map.put(key, value);
						}
					}
				}

				if (!map.get("Content-Type").equals(
						GpsRecordTableReader.content_type)) {
					throw new RuntimeException("not a type of "
							+ GpsRecordTableReader.content_type);
				}

				this.next();
			}

			private String _readLine() throws IOException {
				InputStreamReader rdr = this.m_reader;
				if (rdr == null) {
					rdr = new InputStreamReader(this.m_in, "UTF-8");
					this.m_reader = rdr;
				}
				StringBuilder sb = this.mSB;
				if (sb == null) {
					this.mSB = sb = new StringBuilder();
				}
				final int cli0 = this._currentLineIndex();
				sb.setLength(0);
				for (int ch = rdr.read();; ch = rdr.read()) {
					if (ch < 0) {
						if (sb.length() == 0) {
							return null;
						} else {
							break;
						}
					} else if (ch == 0x0a) {
						this.mCnt0a++;
						int cli = this._currentLineIndex();
						if (cli0 != cli)
							break;
					} else if (ch == 0x0d) {
						this.mCnt0d++;
						int cli = this._currentLineIndex();
						if (cli0 != cli)
							break;
					} else {
						sb.append((char) ch);
					}
				}
				return sb.toString();
			}

			private int _currentLineIndex() {
				return Math.max(this.mCnt0a, this.mCnt0d);
			}

			@Override
			public boolean hasMore() {
				return (this.mNext != null);
			}

			@Override
			public GpsRecord next() throws IOException {
				GpsRecord next = null;
				for (;;) {
					final String line = this._readLine();
					// $FIELDS,source,timestamp,rtc-time,longitude,latitude,altitude,accuracy,speed,bearing,
					// $LOCATION,gps,1366509451899,1366509451925,110.96399878333334,25.92953715,227.9,6.0,3.241,8.6,
					if (line == null) {
						// eof
						break;
					} else if (line.startsWith("$FIELDS")) {
						this._parseFields(line);
					} else if (line.startsWith("$LOCATION")) {
						next = this._parseLocation(line);
						break;
					} else {
						// ignore
					}
				}
				GpsRecord ret = this.mNext;
				this.mNext = next;
				return ret;
			}

			private void _parseFields(String line) {
				String[] array = this.toStringArray(line);
				List<FieldSetter> list = new ArrayList<FieldSetter>();
				Map<String, FieldSetter> map = this.m_fields_map;
				for (String key : array) {
					FieldSetter setter = map.get(key);
					if (setter == null) {
						System.err.println(this + ".unsupportedField : " + key);
						setter = map.get(FieldSetter.the_default);
					}
					list.add(setter);
				}
				this.m_fields_list = list;
			}

			private String[] toStringArray(String line) {
				List<String> list = this.m_string_array_buffer;
				if (list == null) {
					list = new ArrayList<String>();
					this.m_string_array_buffer = list;
				}
				list.clear();
				int from = 0;
				for (int to;; from = to + 1) {
					to = line.indexOf(',', from);
					String substr;
					if (to < 0) {
						substr = line.substring(from);
						list.add(substr);
						break;
					} else {
						substr = line.substring(from, to);
						list.add(substr);
					}
				}
				return list.toArray(new String[list.size()]);
			}

			private GpsRecord _parseLocation(String line) {
				String[] array = this.toStringArray(line);
				MyRec rec = new MyRec();
				final int len = array.length;
				for (int i = 0; i < len; i++) {
					String value = array[i];
					FieldSetter setter = this.m_fields_list.get(i);
					if (setter != null)
						setter.doSet(value, rec);
				}
				return rec;
			}

			@Override
			public void close() throws IOException {
				this.m_in.close();
			}
		}

		interface MutableGpsRecord extends GpsRecord {

			void setTimestamp(long parseLong);

			void setLongitude(double parseDouble);

			void setLatitude(double parseDouble);

			void setAltitude(double parseDouble);

			void setSource(String value);

			void setAccuracy(double parseDouble);

			void setSpeed(double parseDouble);

			void setBearing(double parseDouble);

			void setRtcTime(long parseLong);
		}

		private static class MyRec implements MutableGpsRecord {

			private double mAlt;
			private double mLat;
			private double mLon;
			private long mRtcTime;
			private long mGpsTime;
			private String mSource;
			private double mBearing;
			private double mSpeed;
			private double mAcc;

			@Override
			public String getSource() {
				return this.mSource;
			}

			@Override
			public long getGpsTime() {
				return this.mGpsTime;
			}

			@Override
			public long getRtcTime() {
				return this.mRtcTime;
			}

			@Override
			public double getLongitude() {
				return this.mLon;
			}

			@Override
			public double getLatitude() {
				return this.mLat;
			}

			@Override
			public double getAltitude() {
				return this.mAlt;
			}

			@Override
			public double getAccuracy() {
				return this.mAcc;
			}

			@Override
			public double getSpeed() {
				return this.mSpeed;
			}

			@Override
			public double getBearing() {
				return this.mBearing;
			}

			@Override
			public void setTimestamp(long value) {
				this.mGpsTime = value;
			}

			@Override
			public void setLongitude(double value) {
				this.mLon = value;
			}

			@Override
			public void setLatitude(double value) {
				this.mLat = value;
			}

			@Override
			public void setAltitude(double value) {
				this.mAlt = value;
			}

			@Override
			public void setSource(String value) {
				this.mSource = value;
			}

			@Override
			public void setAccuracy(double value) {
				this.mAcc = value;
			}

			@Override
			public void setSpeed(double value) {
				this.mSpeed = value;
			}

			@Override
			public void setBearing(double value) {
				this.mBearing = value;
			}

			@Override
			public void setRtcTime(long value) {
				this.mRtcTime = value;
			}

			public String toString() {
				StringBuilder sb = new StringBuilder();

				sb.append("[location");
				sb.append(" src:" + this.mSource);
				sb.append(" lon:" + this.mLon);
				sb.append(" lat:" + this.mLat);
				sb.append(" alt:" + this.mAlt);
				sb.append(" acc:" + this.mAcc);
				sb.append(" timeGps:" + this.mGpsTime);
				sb.append(" timeRtc:" + this.mRtcTime);
				sb.append(" speed:" + this.mSpeed);
				sb.append(" bearing:" + this.mBearing);
				sb.append("]");

				return sb.toString();
			}
		}

	}

}
