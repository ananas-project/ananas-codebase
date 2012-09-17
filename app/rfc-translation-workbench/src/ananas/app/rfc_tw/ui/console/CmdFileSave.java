package ananas.app.rfc_tw.ui.console;

import ananas.lib.cmdlinekit.CLKCommand;
import ananas.lib.cmdlinekit.CLKExecuteContext;
import ananas.lib.cmdlinekit.CLKParameterList;

public class CmdFileSave implements CLKCommand {

	@Override
	public String getName() {
		return "save";
	}

	@Override
	public String getHelpTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHelpContent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(CLKExecuteContext context) {
		ConsoleCore core = ConsoleCore.Agent.getCore();
		core.fileSave();
	}

	@Override
	public CLKParameterList getParameterList() {
		// TODO Auto-generated method stub
		return null;
	}

}
