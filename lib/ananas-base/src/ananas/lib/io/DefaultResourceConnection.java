package ananas.lib.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class DefaultResourceConnection implements IContentConnection {

	private final InputStream mIS;

	private DefaultResourceConnection(String path) {
		this.mIS = "".getClass().getResourceAsStream(path);
	}

	public static class Factory implements IConnectionFactory {

		@Override
		public IConnection openConnection(URI uri) throws IOException {
			String path = uri.getPath();
			DefaultResourceConnection conn = new DefaultResourceConnection(path);
			InputStream is = conn.getInputStream();
			if (is == null) {
				System.err.println("cannot find resource by uri : " + uri);
			}
			return conn;
		}
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.mIS;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	public static IConnectionFactory getFactory() {
		return new Factory();
	}

}
