package ananas.lib.io;

import java.io.IOException;

public class DefaultConnector implements IConnector {

	private static IConnector sInst;

	public static IConnector getDefault() {
		IConnector inst = sInst;
		if (inst == null) {
			sInst = inst = new DefaultConnector();
		}
		return inst;
	}

	private final DefaultConnectorImpl mImpl;

	public DefaultConnector() {
		this.mImpl = new DefaultConnectorImpl();
	}

	@Override
	public IConnection open(String uri) throws IOException {
		return this.mImpl.open(uri);
	}

	@Override
	public IConnectionFactoryRegistrar getConnectionFactoryRegistrar() {
		return this.mImpl.getConnectionFactoryRegistrar();
	}

}
