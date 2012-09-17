package ananas.lib.cmdlinekit.command;

import java.io.PrintStream;

import ananas.lib.cmdlinekit.CLKElementsFactory;
import ananas.lib.cmdlinekit.CLKExecuteContext;
import ananas.lib.cmdlinekit.CLKMutableCommandSet;
import ananas.lib.cmdlinekit.CLKRunLoop;
import ananas.lib.cmdlinekit.DefaultCLKElementsFactory;
import ananas.lib.cmdlinekit.DefaultCLKExecuteContext;
import ananas.lib.cmdlinekit.DefaultCLKPrompt;


public class TheDevToolsCommand extends AbstractCLKCommand {

	public TheDevToolsCommand() {
	}

	@Override
	public String getName() {
		return "dev-tools";
	}

	@Override
	public void execute(CLKExecuteContext context) {

		final PrintStream ps = context.getPrint();
		ps.println("ACLCDT - ananas command line console develop tools");
		CLKElementsFactory factory = DefaultCLKElementsFactory.getFactory();
		CLKMutableCommandSet cmdset = factory.newCommandSet();
		CLKRunLoop runloop = factory.newRunLoop();
		DefaultCLKExecuteContext context2 = new DefaultCLKExecuteContext();
		context2.mCommandSet = cmdset;
		context2.mPrompt = new DefaultCLKPrompt("[ACLCDT]:");
		cmdset.addCommand(new TheSampleCommand());
		cmdset.addCommand(new TheTestCommand());
		runloop.run(context2);

	}
}
