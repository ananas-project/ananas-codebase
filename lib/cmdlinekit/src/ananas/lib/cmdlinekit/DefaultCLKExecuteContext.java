package ananas.lib.cmdlinekit;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class DefaultCLKExecuteContext implements CLKExecuteContext {

	public CLKCommand mCommand;
	public CLKCommandSet mCommandSet;
	public CLKElementsFactory mElementsFactory;
	public CLKRunLoop mRunLoop;
	public PrintStream mOut;
	public InputStream mIn;
	public CLKParameterList mParamList;
	public CLKPrompt mPrompt;

	public DefaultCLKExecuteContext() {
		this.mElementsFactory = DefaultCLKElementsFactory.getFactory();
		this.mOut = System.out;
		this.mIn = System.in;
		this.mPrompt = new DefaultCLKPrompt("input command:");
	}

	public DefaultCLKExecuteContext(CLKExecuteContext p) {
		this.mCommand = p.getCommand();
		this.mCommandSet = p.getCommandSet();
		this.mElementsFactory = p.getElementsFactory();
		this.mRunLoop = p.getRunLoop();
		this.mIn = p.getInput();
		this.mOut = p.getPrint();
		this.mParamList = p.getParameterList();
		this.mPrompt = p.getPrompt();
	}

	@Override
	public CLKCommand getCommand() {
		return this.mCommand;
	}

	@Override
	public CLKCommandSet getCommandSet() {
		return this.mCommandSet;
	}

	@Override
	public CLKElementsFactory getElementsFactory() {
		return this.mElementsFactory;
	}

	@Override
	public CLKRunLoop getRunLoop() {
		return this.mRunLoop;
	}

	@Override
	public InputStream getInput() {
		return this.mIn;
	}

	@Override
	public OutputStream getOutput() {
		return this.mOut;
	}

	@Override
	public PrintStream getPrint() {
		return this.mOut;
	}

	@Override
	public CLKParameterList getParameterList() {
		return this.mParamList;
	}

	@Override
	public CLKPrompt getPrompt() {
		return this.mPrompt;
	}

}
