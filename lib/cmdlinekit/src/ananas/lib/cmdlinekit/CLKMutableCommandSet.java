package ananas.lib.cmdlinekit;

public interface CLKMutableCommandSet extends CLKCommandSet {

	void addCommand(CLKCommand cmd);

	void removeCommand(CLKCommand cmd);

}
