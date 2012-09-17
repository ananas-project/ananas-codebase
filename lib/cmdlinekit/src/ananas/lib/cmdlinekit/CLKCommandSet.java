package ananas.lib.cmdlinekit;

public interface CLKCommandSet {

	CLKCommand getCommand(String name);

	CLKCommand[] listCommands();

}
