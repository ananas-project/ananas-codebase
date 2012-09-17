package ananas.lib.cmdlinekit;

public class DefaultCLKPrompt implements CLKPrompt {

	private String mText;

	public DefaultCLKPrompt(String text) {
		this.mText = text;
	}

	@Override
	public String getText() {
		return this.mText;
	}

}
