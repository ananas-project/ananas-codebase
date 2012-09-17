package ananas.lib.cmdlinekit.command;

import ananas.lib.cmdlinekit.CLKCommand;
import ananas.lib.cmdlinekit.CLKExecuteContext;
import ananas.lib.cmdlinekit.CLKParameterList;

public class TheExitCommand implements CLKCommand {

	@Override
	public String getName() {
		return "exit";
	}

	@Override
	public String getHelpTitle() {
		return "exit the app";
	}

	@Override
	public String getHelpContent() {
		return "";
	}

	@Override
	public void execute(CLKExecuteContext context) {
		context.getRunLoop().exit();
	}

	@Override
	public CLKParameterList getParameterList() {
		return null;
	}

}
