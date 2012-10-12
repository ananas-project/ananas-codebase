package ananas.lib.blueprint2.element.base;

public class CBlueprint {

	private CBody mBody;
	private CHead mHead;

	public CBlueprint() {
	}

	public void setBody(CBody body) {
		this.mBody = body;
	}

	public void setHead(CHead head) {
		this.mHead = head;
	}

	public CBody getBody() {
		return this.mBody;
	}

	public CHead getHead() {
		return this.mHead;
	}

}
