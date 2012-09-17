package ananas.lib.cmdlinekit.command;

import ananas.lib.cmdlinekit.CLKElementsFactory;
import ananas.lib.cmdlinekit.CLKExecuteContext;
import ananas.lib.cmdlinekit.CLKInputDialog;

public class TheSampleCommand extends AbstractCLKCommand {

	public TheSampleCommand() {
		this.registerParameter("aaa", "def-val", true, "desc a");
		this.registerParameter("bbb", null, false, "desc b");
	}

	@Override
	public String getName() {
		return "sample";
	}

	@Override
	public void execute(CLKExecuteContext context) {

		CLKElementsFactory factory = context.getElementsFactory();
		CLKInputDialog dlg = factory.newInputDialog();
		String rlt = dlg.readInput(context, "password:", "");
		context.getPrint().println("rlt is :" + rlt);
	}
}
