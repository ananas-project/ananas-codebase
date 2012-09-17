package test.cmdlinekit;

import ananas.lib.cmdlinekit.CLKElementsFactory;
import ananas.lib.cmdlinekit.CLKMutableCommandSet;
import ananas.lib.cmdlinekit.CLKRunLoop;
import ananas.lib.cmdlinekit.DefaultCLKElementsFactory;
import ananas.lib.cmdlinekit.DefaultCLKExecuteContext;
import ananas.lib.cmdlinekit.command.TheDevToolsCommand;

public class Main {

	public static void main(String[] arg) {

		System.out.println("ananas xmpp console");
		CLKElementsFactory factory = DefaultCLKElementsFactory.getFactory();
		CLKMutableCommandSet cmdset = factory.newCommandSet();
		CLKRunLoop runloop = factory.newRunLoop();
		DefaultCLKExecuteContext context = new DefaultCLKExecuteContext();
		context.mCommandSet = cmdset;
		cmdset.addCommand(new TheDevToolsCommand());
		runloop.run(context);
		System.out.println("the end.");
	}

}
