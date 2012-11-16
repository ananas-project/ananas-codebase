package ananas.lib.xmpp.component;

import ananas.lib.xmpp.api.AXClientListener;
import ananas.lib.xmpp.command.AXCommand;
import ananas.lib.xmpp.event.AXEvent;

public class AbstractAXClient implements AXClientComponent, AXClientListener {

	private AXClientListener mListener1;
	private AXClientComponent mChild1;

	@Override
	public void addListener(AXClientListener listener) {
		this.mListener1 = listener;
	}

	@Override
	public void removeListener(AXClientListener listener) {
		this.mListener1 = null;
	}

	@Override
	public void execute(AXCommand command) {
		AXClientComponent ch = this.mChild1;
		if (ch != null) {
			ch.execute(command);
		}
	}

	@Override
	public void onEvent(AXEvent event) {
		AXClientListener l = this.mListener1;
		if (l != null) {
			l.onEvent(event);
		}
	}

	@Override
	public void addChild(AXClientComponent child) {
		if (child != null) {
			this.mChild1 = child;
			child.addListener(this);
		}
	}

}
