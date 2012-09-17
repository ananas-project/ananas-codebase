package ananas.lib.cmdlinekit;

public interface CLKCommand {

	String getName();

	String getHelpTitle();

	String getHelpContent();

	void execute(CLKExecuteContext context);

	CLKParameterList getParameterList();
}
