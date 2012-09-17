package ananas.lib.cmdlinekit.command;

import java.io.PrintStream;

import ananas.lib.cmdlinekit.CLKCommand;
import ananas.lib.cmdlinekit.CLKExecuteContext;
import ananas.lib.cmdlinekit.CLKParameter;
import ananas.lib.cmdlinekit.CLKParameterList;


public class TheTestCommand implements CLKCommand {

	@Override
	public String getName() {
		return "test";
	}

	@Override
	public String getHelpTitle() {
		return "title of " + this.getName();
	}

	@Override
	public String getHelpContent() {
		return "content of " + this.getName();
	}

	@Override
	public void execute(CLKExecuteContext context) {

		PrintStream ps = context.getPrint();
		ps.println("test command to list parameters");
		CLKParameterList plist = context.getParameterList();
		ps.println("        " + "<field>" + " : " + "<value>");
		if (plist != null) {
			CLKParameter[] array = plist.listParameters();
			if (array != null) {
				for (CLKParameter param : array) {
					char ch = '"';
					ps.println("        " + ch + param.getName() + ch + " : "
							+ ch + param.getValue() + ch);
				}
			}
		}
		ps.println("        " + "<the end>");
	}

	@Override
	public CLKParameterList getParameterList() {
		return null;
	}

}
