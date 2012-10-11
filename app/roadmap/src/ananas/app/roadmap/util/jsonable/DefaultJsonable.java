package ananas.app.roadmap.util.jsonable;

import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;

public class DefaultJsonable implements Jsonable {

	public static DefaultJsonable load(String data) {
		try {
			JSONObject jo = new JSONObject(data);
			String cls_name = jo.getString("class");
			Class<?> cls;
			cls = Class.forName(cls_name);
			DefaultJsonable inst = (DefaultJsonable) cls.newInstance();
			inst.onLoad(jo);
			return inst;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String save(DefaultJsonable ja) {
		try {
			JSONObject jo = new JSONObject();
			ja.onSave(jo);
			String cls_name = ja.getClass().getName();
			jo.put("class", cls_name);
			return jo.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onLoad(JSONObject jo) {
		try {
			for (final Field field : this.getClass().getFields()) {
				final String fname = field.getName();
				final Class<?> ftype = field.getType();

				// boolean
				if (ftype.equals(boolean.class)) {
					field.setBoolean(this, jo.getBoolean(fname));

					// integer
				} else if (ftype.equals(short.class)) {
					field.setShort(this, (short) jo.getInt(fname));

				} else if (ftype.equals(int.class)) {
					field.setInt(this, jo.getInt(fname));

				} else if (ftype.equals(long.class)) {
					field.setLong(this, jo.getLong(fname));

					// float
				} else if (ftype.equals(double.class)) {
					field.setDouble(this, jo.getDouble(fname));

				} else if (ftype.equals(float.class)) {
					field.setFloat(this, (float) jo.getDouble(fname));

					// string
				} else if (ftype.equals(String.class)) {
					Object str = jo.get(fname);
					if (!(str instanceof String))
						str = null;
					field.set(this, str);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSave(JSONObject jo) {
		try {
			for (final Field field : this.getClass().getFields()) {
				final String fname = field.getName();
				final Class<?> ftype = field.getType();

				// boolean
				if (ftype.equals(boolean.class)) {
					jo.put(fname, field.getBoolean(this));

					// integer
				} else if (ftype.equals(short.class)) {
					jo.put(fname, field.getShort(this));

				} else if (ftype.equals(int.class)) {
					jo.put(fname, field.getInt(this));

				} else if (ftype.equals(long.class)) {
					jo.put(fname, field.getLong(this));

					// float
				} else if (ftype.equals(float.class)) {
					jo.put(fname, field.getFloat(this));

				} else if (ftype.equals(double.class)) {
					jo.put(fname, field.getDouble(this));

					// string
				} else if (ftype.equals(String.class)) {
					Object str = field.get(this);
					if (str == null)
						str = JSONObject.NULL;
					jo.put(fname, str);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
