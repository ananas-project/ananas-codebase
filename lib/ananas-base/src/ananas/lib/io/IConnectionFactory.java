package ananas.lib.io;

import java.io.IOException;
import java.net.URI;

public interface IConnectionFactory {

	IConnection openConnection(URI uri) throws IOException;

}
