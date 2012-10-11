package ananas.lib.io;

import java.net.URI;
import java.util.List;

public interface IConnectionFactoryRegistrar {

	void registerFactory(URI uri, IConnectionFactory factory);

	IConnectionFactory getFactory(URI uri);

	void printItems();

	List<URI> listItems();

}
