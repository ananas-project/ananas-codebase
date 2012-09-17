package ananas.app.rfc_tw.ui.console;

import ananas.lib.cmdlinekit.CLKElementsFactory;
import ananas.lib.cmdlinekit.CLKMutableCommandSet;
import ananas.lib.cmdlinekit.CLKRunLoop;
import ananas.lib.cmdlinekit.DefaultCLKElementsFactory;
import ananas.lib.cmdlinekit.DefaultCLKExecuteContext;

public class ConsoleMain {

	public static void main(String arg[]) {

		System.out.println("rfc-translation-workbench");
		CLKElementsFactory factory = DefaultCLKElementsFactory.getFactory();
		CLKMutableCommandSet cmdset = factory.newCommandSet();
		CLKRunLoop runloop = factory.newRunLoop();
		DefaultCLKExecuteContext context = new DefaultCLKExecuteContext();
		context.mCommandSet = cmdset;
		{
			// cmdset.addCommand(new TheDevToolsCommand());

			// file
			cmdset.addCommand(new CmdFileSave());
			cmdset.addCommand(new CmdFileSaveAs());
			cmdset.addCommand(new CmdFileReload());
			cmdset.addCommand(new CmdFileOpen());
			cmdset.addCommand(new CmdFileNew());
			cmdset.addCommand(new CmdFileClose());
			cmdset.addCommand(new CmdFileImportOriginal());
			cmdset.addCommand(new CmdViewText());
		}
		runloop.run(context);
		System.out.println("the end.");
	}
}
