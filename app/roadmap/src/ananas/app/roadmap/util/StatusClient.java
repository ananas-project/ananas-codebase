package ananas.app.roadmap.util;

import java.lang.reflect.Field;

import ananas.app.roadmap.RoadmapService;
import ananas.app.roadmap.RoadmapService.IRoadmapService2Binder;

public class StatusClient {

	class Const {

		public static final String bool_true = "yes";
		public static final String bool_false = "no";

		//
		public static final String field_rec = "rec";
		public static final String field_mypos = "mypos";

	}

	private final IRoadmapService2Binder mBinder;
	public boolean isRecording = true;
	public boolean isMyPosVisible = true;

	public StatusClient(RoadmapService.IRoadmapService2Binder binder) {
		this.mBinder = binder;
	}

	public void update() {
		Field[] fields = this.getClass().getFields();
		for (final Field field : fields) {
			try {
				String fname = field.getName();
				Class<?> ftype = field.getType();
				if (ftype.equals(boolean.class)) {
					String strVal = "" + this.mBinder.getProperty(fname);
					String strTrue = "" + true;
					field.setBoolean(this, strTrue.equals(strVal));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void commit() {
		Field[] fields = this.getClass().getFields();
		for (final Field field : fields) {
			try {
				String fname = field.getName();
				Class<?> ftype = field.getType();
				if (ftype.equals(boolean.class)) {
					boolean boolVal = field.getBoolean(this);
					String strVal = "" + boolVal;
					this.mBinder.setProperty(fname, strVal);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
