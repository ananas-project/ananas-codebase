package ananas.app.roadmap.util;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.TimeZone;
import java.util.Vector;

public abstract class HttpTimeStampConvertor {

	public static HttpTimeStampConvertor getInstance() {
		return sInst;
	}

	public abstract String millisecondToString(long ms);

	public abstract long stringToMillisecond(String str);

	private final static HttpTimeStampConvertor sInst = new MyImpl();

	private static class MyImpl extends HttpTimeStampConvertor {

		@Override
		public String millisecondToString(long ms) {

			// Sun, 06 Nov 1994 08:49:37 GMT ; RFC 822, updated by RFC 1123

			final Calendar cale = Calendar
					.getInstance(TimeZone.getTimeZone(""));
			cale.setTimeInMillis(ms);

			int week, DD, YY, MM, hh, mm, ss;
			week = cale.get(Calendar.DAY_OF_WEEK);
			DD = cale.get(Calendar.DAY_OF_MONTH);
			MM = cale.get(Calendar.MONTH);
			YY = cale.get(Calendar.YEAR);
			hh = cale.get(Calendar.HOUR_OF_DAY);
			mm = cale.get(Calendar.MINUTE);
			ss = cale.get(Calendar.SECOND);

			String strWeek, strMonth;
			strWeek = this._getWeekMapper().doMap(week);
			strMonth = this._getMonthMapper().doMap(MM);

			return (strWeek + ", " + _add02(DD) + " " + strMonth + " " + YY
					+ " " + _add02(hh) + ":" + _add02(mm) + ":" + _add02(ss) + " GMT");

		}

		@Override
		public long stringToMillisecond(String str) {
			MyParser parser = new MyParser(str, this._getMonthMapper(),
					this._getWeekMapper());
			return parser.toLong();
		}

		private MyIntToStringMapper mWeekMapper;
		private MyIntToStringMapper mMonthMapper;

		private String _add02(int n) {
			String s = n + "";
			if (s.length() == 1) {
				s = '0' + s;
			}
			return s;
		}

		private MyIntToStringMapper _getWeekMapper() {
			MyIntToStringMapper mapper = this.mWeekMapper;
			if (mapper == null) {
				mapper = new MyIntToStringMapper();
				mapper.regMapper(Calendar.MONDAY, "Mon");
				mapper.regMapper(Calendar.TUESDAY, "Tue");
				mapper.regMapper(Calendar.WEDNESDAY, "Wed");
				mapper.regMapper(Calendar.THURSDAY, "Thu");
				mapper.regMapper(Calendar.FRIDAY, "Fri");
				mapper.regMapper(Calendar.SATURDAY, "Sat");
				mapper.regMapper(Calendar.SUNDAY, "Sun");
				this.mWeekMapper = mapper;
			}
			return mapper;
		}

		private MyIntToStringMapper _getMonthMapper() {
			MyIntToStringMapper mapper = this.mMonthMapper;
			if (mapper == null) {
				mapper = new MyIntToStringMapper();
				mapper.regMapper(Calendar.JANUARY, "Jan");
				mapper.regMapper(Calendar.FEBRUARY, "Feb");
				mapper.regMapper(Calendar.MARCH, "Mar");
				mapper.regMapper(Calendar.APRIL, "Apr");
				mapper.regMapper(Calendar.MAY, "May");
				mapper.regMapper(Calendar.JUNE, "Jun");
				mapper.regMapper(Calendar.JULY, "Jul");
				mapper.regMapper(Calendar.AUGUST, "Aug");
				mapper.regMapper(Calendar.SEPTEMBER, "Sep");
				mapper.regMapper(Calendar.OCTOBER, "Oct");
				mapper.regMapper(Calendar.NOVEMBER, "Nov");
				mapper.regMapper(Calendar.DECEMBER, "Dec");
				this.mMonthMapper = mapper;
			}
			return mapper;
		}

	}

	private static class MyIntToStringMapper {

		final Hashtable<String, String> mTable;

		MyIntToStringMapper() {
			mTable = new Hashtable<String, String>();
		}

		void regMapper(int n, String s) {
			final String key = n + "";
			this.mTable.put(key, s);
			this.mTable.put(s, key);
		}

		String doMap(int n) {
			final String key = n + "";
			return this.mTable.get(key);
		}

		int doRemap(String s) {
			String val = this.mTable.get(s);
			return Integer.parseInt(val);
		}
	}

	private static class MyParser {

		private final String mString;
		private final MyIntToStringMapper mWeekMapper;
		private final MyIntToStringMapper mMonthMapper;

		public MyParser(String str, MyIntToStringMapper monthMapper,
				MyIntToStringMapper weekMapper) {
			this.mString = str;
			this.mMonthMapper = monthMapper;
			this.mWeekMapper = weekMapper;
		}

		public long toLong() {

			String[] array = this._toStringArray(this.mString);

			String strWeek = array[0];
			int DD = Integer.parseInt(array[1]);
			String strMonth = array[2];
			int YY = Integer.parseInt(array[3]);
			int hh = Integer.parseInt(array[4]);
			int mm = Integer.parseInt(array[5]);
			int ss = Integer.parseInt(array[6]);

			int week = this.mWeekMapper.doRemap(strWeek);
			int month = this.mMonthMapper.doRemap(strMonth);

			final Calendar cale = Calendar
					.getInstance(TimeZone.getTimeZone(""));
			cale.set(YY, month, DD, hh, mm, ss);
			if (week == 0) {
			}

			return cale.getTimeInMillis();
		}

		private String[] _toStringArray(String str) {
			// 0----1--2---3----4--5--6--7
			// Sun, 06 Nov 1994 08:49:37 GMT ; RFC 822, updated by RFC 1123

			final char[] chs = str.toCharArray();
			final Vector<String> v = new Vector<String>();
			final StringBuffer sbuf = new StringBuffer();
			boolean isPrevAlpha = true;
			for (int i = 0; i < chs.length; i++) {
				final char ch = chs[i];
				boolean isAlpha = true;
				if ('0' <= ch && ch <= '9') {
				} else if ('a' <= ch && ch <= 'z') {
				} else if ('A' <= ch && ch <= 'Z') {
				} else {
					isAlpha = false;
				}
				if (isPrevAlpha != isAlpha) {
					if (!isAlpha)
						v.addElement(sbuf.toString());
					sbuf.setLength(0);
					isPrevAlpha = isAlpha;
				}
				sbuf.append(ch);
			}
			return v.toArray(new String[0]);
		}
	}

}
