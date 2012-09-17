package ananas.app.rfc_tw.ui.console;

import ananas.lib.cmdlinekit.CLKCommand;
import ananas.lib.cmdlinekit.CLKExecuteContext;
import ananas.lib.cmdlinekit.CLKParameterList;

public class CmdFileNew implements CLKCommand {

	@Override
	public String getName() {
		return "new";
	}

	@Override
	public String getHelpTitle() {
		return "(help title)";
	}

	@Override
	public String getHelpContent() {
		return "(help content)";
	}

	@Override
	public void execute(CLKExecuteContext context) {
		ConsoleCore core = ConsoleCore.Agent.getCore();
		core.fileNew();
	}

	@Override
	public CLKParameterList getParameterList() {
		// TODO Auto-generated method stub
		return null;
	}

}
