package ananas.lib.io;

import java.io.IOException;

public interface IConnector {

	IConnection open(String uri) throws IOException;

	IConnectionFactoryRegistrar getConnectionFactoryRegistrar();
}
