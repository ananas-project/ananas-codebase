package ananas.lib.cmdlinekit;

import ananas.lib.cmdlinekit.impl.CLKElementsFactoryImpl;

public final class DefaultCLKElementsFactory {

	private final static CLKElementsFactoryImpl impl = new CLKElementsFactoryImpl();

	public static CLKElementsFactory getFactory() {
		return impl;
	}
}
