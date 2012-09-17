package ananas.lib.cmdlinekit;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public interface CLKExecuteContext {

	CLKCommand getCommand();

	CLKCommandSet getCommandSet();

	CLKElementsFactory getElementsFactory();

	CLKRunLoop getRunLoop();

	CLKParameterList getParameterList();

	InputStream getInput();

	OutputStream getOutput();

	PrintStream getPrint();

	CLKPrompt getPrompt();

}
