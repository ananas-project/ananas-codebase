package ananas.lib.blueprint2.awt;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BorderLayoutWrapper extends ObjectWrapper implements
		LayoutManagerWrapper {

	public static final String AFTER_LAST_LINE = "Last";
	public static final String AFTER_LINE_ENDS = "After";
	public static final String BEFORE_FIRST_LINE = "First";
	public static final String BEFORE_LINE_BEGINS = "Before";
	public static final String CENTER = "Center";
	public static final String EAST = "East";
	public static final String LINE_END = "After";
	public static final String LINE_START = "Before";
	public static final String NORTH = "North";
	public static final String PAGE_END = "Last";
	public static final String PAGE_START = "First";
	public static final String SOUTH = "South";
	public static final String WEST = "West";

	@Override
	public LayoutManager getLayoutManager(boolean create) {
		return (LayoutManager) this.getTarget(create);
	}

	@Override
	public void addComponentToContainer(Component component,
			Container container, String position) {

		String pos = this._normalizePos(position);
		container.add(component, pos);
	}

	private String _normalizePos(String pos) {
		return sPosSet.normalizePos(pos);
	}

	private final static MyPosSet sPosSet;

	static {
		MyPosSet ps = new MyPosSet();
		ps.init();
		sPosSet = ps;
	}

	private static class MyPosSet {

		private final Map<String, String> mTable;

		MyPosSet() {
			this.mTable = new HashMap<String, String>();
		}

		public String normalizePos(String pos) {
			if (pos != null) {
				pos = pos.toLowerCase();
				String value = this.mTable.get(pos);
				if (value != null) {
					return value;
				}
			}
			return BorderLayout.CENTER;
		}

		public void init() {
			Field[] fields = BorderLayout.class.getFields();
			for (Field field : fields) {

				Class<?> type = field.getType();
				if (type.equals(String.class)) {
					String name = field.getName();
					String value = this._stringValueByField(field);
					this._reg(name, value);
				}
			}
		}

		private void _reg(final String name, final String value) {
			// System.out.println("    " + this + ".reg:" + name + "=" + value);
			String name2 = name.toLowerCase();
			String value2 = value.toLowerCase();
			this.mTable.put(name2, value);
			this.mTable.put(value2, value);
		}

		private String _stringValueByField(Field field) {
			try {
				return (String) field.get(null);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
