package ananas.lib.cmdlinekit.command;

import java.io.PrintStream;

import ananas.lib.cmdlinekit.CLKCommand;
import ananas.lib.cmdlinekit.CLKCommandSet;
import ananas.lib.cmdlinekit.CLKExecuteContext;
import ananas.lib.cmdlinekit.CLKParameter;
import ananas.lib.cmdlinekit.CLKParameterList;


public class TheHelpCommand extends AbstractCLKCommand {

	public TheHelpCommand() {
		this.registerParameter("cmd", "", true,
				"the command name witch to show detail help.");
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getHelpTitle() {
		return "show help info";
	}

	@Override
	public String getHelpContent() {
		return ("show help info, "
				+ "use 'help -cmd <command>' to show detail help content for special command.");
	}

	@Override
	public void execute(CLKExecuteContext context) {
		CLKParameter cmd = context.getParameterList().getParameter("cmd");
		if (cmd == null) {
			this._printCommandList(context);
		} else {
			this._printCommandContent(context);
		}
	}

	private void _printCommandList(CLKExecuteContext context) {

		final PrintStream ps = context.getPrint();
		ps.println();
		ps.println("this is a list of commands witch you can use.");
		final CLKCommandSet cmdset = context.getCommandSet();
		for (CLKCommand cmd : cmdset.listCommands()) {
			String name = cmd.getName();
			String title = cmd.getHelpTitle();
			final String space = "          ";
			if (name.length() < space.length()) {
				name = (name + space).substring(0, space.length());
			}
			ps.println("    " + name + " " + title);
		}
		ps.println("use 'help -cmd <command>' to show detail help content for special command.");
		ps.println();
	}

	private void _printCommandContent(CLKExecuteContext context) {

		final String cmd_name = context.getParameterList().getParameter("cmd")
				.getValue();
		final CLKCommand cmd = context.getCommandSet().getCommand(cmd_name);

		final PrintStream ps = context.getPrint();
		ps.println();
		ps.println("this is the help for '" + cmd.getName() + "' command.");
		ps.println(cmd.getHelpTitle());
		ps.println(cmd.getHelpContent());

		CLKParameterList plist = cmd.getParameterList();
		if (plist != null) {

			ps.println("    "
					+ "<parameter> | <default-value> | <option> | <description>");

			for (CLKParameter param : plist.listParameters()) {

				String pname = "-" + param.getName();
				String pvalue = param.getValue();
				String pdesc = param.getDescription();
				String poption = param.isOption() ? "YES" : "NO";

				pname = this._normal_string_length(pname, 10);
				pvalue = this._normal_string_length(pvalue, 10);
				pdesc = this._normal_string_length(pdesc, 10);
				poption = this._normal_string_length(poption, 10);

				ps.println("    " + pname + " | " + pvalue + " | " + poption
						+ " | " + pdesc);
			}
		}
		ps.println();
		ps.println();

	}

	private String _normal_string_length(String str, int len) {
		if (str == null)
			str = "(null)";
		StringBuffer sbuf = new StringBuffer();
		for (int i = len; i > 0; i -= 4) {
			sbuf.append("    ");
		}
		final String space = sbuf.toString();
		if (str.length() < space.length()) {
			return (str + space).substring(0, space.length());
		} else {
			return str;
		}
	}

}
