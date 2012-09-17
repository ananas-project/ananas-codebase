package ananas.lib.cmdlinekit;

public interface CLKElementsFactory {

	CLKMutableCommandSet newCommandSet();

	CLKRunLoop newRunLoop();

	CLKMutableParameterList newParameterList();

	CLKMutableParameter newParameter(String name);

	CLKMutableParameter newParameter(String name, String value,
			boolean isOption, String description);

	CLKInputDialog newInputDialog();

}
