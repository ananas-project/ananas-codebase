package ananas.lib.blueprint2.element.base;

public class BpBlueprint {

	private BpBody mBody;
	private BpHead mHead;

	public BpBlueprint() {
	}

	public void setBody(BpBody body) {
		this.mBody = body;
	}

	public void setHead(BpHead head) {
		this.mHead = head;
	}

	public BpBody getBody() {
		return this.mBody;
	}

	public BpHead getHead() {
		return this.mHead;
	}

}
