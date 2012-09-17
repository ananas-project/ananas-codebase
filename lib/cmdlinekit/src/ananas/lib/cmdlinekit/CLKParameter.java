package ananas.lib.cmdlinekit;

public interface CLKParameter {

	String getName();

	String getValue();

	String getDescription();

	boolean isOption();
}
