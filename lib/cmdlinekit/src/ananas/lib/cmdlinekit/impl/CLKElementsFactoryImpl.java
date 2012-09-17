package ananas.lib.cmdlinekit.impl;

import ananas.lib.cmdlinekit.CLKElementsFactory;
import ananas.lib.cmdlinekit.CLKInputDialog;
import ananas.lib.cmdlinekit.CLKMutableCommandSet;
import ananas.lib.cmdlinekit.CLKMutableParameter;
import ananas.lib.cmdlinekit.CLKMutableParameterList;
import ananas.lib.cmdlinekit.CLKRunLoop;

public class CLKElementsFactoryImpl implements CLKElementsFactory {

	@Override
	public CLKMutableCommandSet newCommandSet() {
		return new CLKCommandSetImpl();
	}

	@Override
	public CLKRunLoop newRunLoop() {
		return new CLKRunLoopImpl();
	}

	@Override
	public CLKMutableParameterList newParameterList() {
		return new CLKParameterListImpl();
	}

	@Override
	public CLKMutableParameter newParameter(String name) {
		return new CLKParameterImpl(name);
	}

	@Override
	public CLKMutableParameter newParameter(String name, String value,
			boolean isOption, String description) {

		CLKParameterImpl param = new CLKParameterImpl(name);
		param.setDescription(description);
		param.setOption(isOption);
		param.setValue(value);
		return param;
	}

	@Override
	public CLKInputDialog newInputDialog() {
		return new CLKInputDialogImpl();
	}

}
