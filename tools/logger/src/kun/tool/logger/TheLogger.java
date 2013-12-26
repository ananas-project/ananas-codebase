package kun.tool.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.TimeZone;

public class TheLogger {

	private final String _text;
	private final String _name;

	public TheLogger(String name, String text) {
		this._name = name;
		this._text = text;
	}

	public static void main(String[] arg) {

		try {

			String name = null;
			if (arg != null)
				if (arg.length > 0) {
					name = arg[0];
				}
			if (name == null) {
				System.out.println("usage: appname [name][more...]");
				return;
			}
			StringBuilder sb = new StringBuilder();
			for (String s : arg) {
				sb.append(' ');
				sb.append(s);
				// sb.append(';');
			}

			TheLogger logger = new TheLogger(name, sb.toString());
			logger.log();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void log() throws IOException {

		File file = this.getFile();
		System.out.println("log to " + file);

		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		FileOutputStream out = new FileOutputStream(file, true);

		StringBuilder sb = new StringBuilder();
		sb.append(this.getTimestampString());
		sb.append(" [");
		sb.append(this.getClass().getName());
		sb.append("]");
		sb.append(this._text);
		sb.append("\r\n");

		String text = sb.toString();
		System.out.println(text);
		out.write(text.getBytes("UTF-8"));
		out.close();
	}

	private String getTimestampString() {
		TimeZone zone = TimeZone.getTimeZone("GMT+8");
		Calendar cale = java.util.Calendar.getInstance(zone);
		StringBuilder sb = new StringBuilder();
		sb.append(cale.get(Calendar.YEAR));
		sb.append("-");
		sb.append(cale.get(Calendar.MONTH) + 1);
		sb.append("-");
		sb.append(cale.get(Calendar.DAY_OF_MONTH));
		sb.append(" ");
		sb.append(cale.get(Calendar.HOUR_OF_DAY));
		sb.append(":");
		sb.append(cale.get(Calendar.MINUTE));
		sb.append(":");
		sb.append(cale.get(Calendar.SECOND));
		sb.append(" ");
		sb.append(zone.getDisplayName());
		return sb.toString();
	}

	private File getFile() {

		try {
			URL url = this.getClass().getProtectionDomain().getCodeSource()
					.getLocation();
			final File path0 = new File(url.toURI());
			File path = path0;
			for (; path != null; path = path.getParentFile()) {
				File dir = new File(path, "log");
				if (dir.exists())
					if (dir.isDirectory()) {
						return new File(dir, this._name + ".log");
					}
			}
			System.out.println("cannot find 'log' directoy in the path: "
					+ path0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
