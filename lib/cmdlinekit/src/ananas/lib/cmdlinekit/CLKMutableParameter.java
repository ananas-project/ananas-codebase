package ananas.lib.cmdlinekit;

public interface CLKMutableParameter extends CLKParameter {

	void setValue(String value);

	void setDescription(String description);

	void setOption(boolean isOption);

}
