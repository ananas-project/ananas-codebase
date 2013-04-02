package ananas.lib.jetty_lite;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyLite {

	public static void main(String[] arg) {

		JettyLite jl = new JettyLite();
		jl.run();

	}

	private void run() {
		Server server = new Server();
		try {
			Connector conn2 = new SocketConnector();
			conn2.setPort(9999);
			server.setConnectors(new Connector[] { conn2 });

			ContextHandlerCollection handler = new ContextHandlerCollection();
			ServletContextHandler servlethandler = new ServletContextHandler();
			// servlethandler.addServlet(MyServlet.class, "*.do");
			handler.addHandler(servlethandler);

			List<WebAppContext> apps = this.searchWebApps();
			for (WebAppContext app : apps) {
				handler.addHandler(app);
			}

			server.setHandler(handler);

			// XmlConfiguration conf = new
			// XmlConfiguration("./src/jetty/etc/jetty.xml");
			// conf.configure(server);

			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("EOP");

	}

	private List<WebAppContext> searchWebApps() {
		List<WebAppContext> list = new Vector<WebAppContext>();
		File dir = new File(this.getWorkingDir(), "webapps");
		System.out.println("search war in " + dir);
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.getName().endsWith(".war")) {
				list.add(this.loadWar(file));
			}
		}
		System.out.println(files.length + " war(s) has been added.");
		return list;
	}

	private File getWorkingDir() {
		URL url = this.getClass().getProtectionDomain().getCodeSource()
				.getLocation();
		String path = this.unescapeURLPath(url.getPath());
		File dir = new File(path);
		return dir.getParentFile();
	}

	private String unescapeURLPath(String path) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StringBuilder sbtmp = new StringBuilder();
		for (char ch : path.toCharArray()) {
			if (sbtmp.length() > 0) {
				sbtmp.append(ch);
				if (sbtmp.length() >= 3) {
					String s = sbtmp.substring(1);
					sbtmp.setLength(0);
					baos.write(Integer.parseInt(s, 16));
				}
			} else {
				if (ch == '%') {
					sbtmp.append(ch);
				} else {
					baos.write(ch);
				}
			}
		}
		try {
			return new String(baos.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return path;
		}
	}

	private WebAppContext loadWar(File war) {
		System.out.println("load war : " + war);
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/" + war.getName());
		webapp.setWar(war.getAbsolutePath());
		// webapp.setClassLoader(Thread.currentThread().getContextClassLoader());
		// webapp.setConfigurationDiscovered(true);
		// webapp.setParentLoaderPriority(true);
		return webapp;
	}

}
