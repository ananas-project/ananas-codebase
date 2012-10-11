package ananas.lib.blueprint2;

public class BlueprintException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2454838720412995927L;

	public BlueprintException(String msg) {
		super(msg);
	}

	public BlueprintException(Exception msg) {
		super(msg);
	}
}
