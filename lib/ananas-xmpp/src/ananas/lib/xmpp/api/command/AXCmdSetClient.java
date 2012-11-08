package ananas.lib.xmpp.api.command;

import ananas.lib.xmpp.api.AXAccount;
import ananas.lib.xmpp.api.AXClient;
import ananas.lib.xmpp.api.AXClientControl;

public class AXCmdSetClient extends AbstractAXCommand implements
		AXClientControl {

	private AXAccount mAccount;
	private int mCtrl;

	public AXCmdSetClient() {
	}

	public AXCmdSetClient(int ctrl) {
		this.mCtrl = ctrl;
	}

	@Override
	public void onExecuteBy(AXClient client) {
		if (client instanceof AXClientControl) {
			AXClientControl cc = (AXClientControl) client;
			switch (this.mCtrl) {
			case AXCmdSetClient.CTRL_GET_ACCOUNT:
				this.mAccount = cc.getAccount();
				break;
			case AXCmdSetClient.CTRL_CLOSE:
				cc.close();
				break;
			case AXCmdSetClient.CTRL_CONNECT:
				cc.connect();
				break;
			case AXCmdSetClient.CTRL_DISCONNECT:
				cc.disconnect();
				break;
			default:
			}
		}
	}

	public AXAccount getAccount() {
		this.mCtrl = AXCmdSetClient.CTRL_GET_ACCOUNT;
		return this.mAccount;
	}

	public void connect() {
		this.mCtrl = AXCmdSetClient.CTRL_CONNECT;
	}

	public void disconnect() {
		this.mCtrl = AXCmdSetClient.CTRL_DISCONNECT;
	}

	public void close() {
		this.mCtrl = AXCmdSetClient.CTRL_CLOSE;
	}

}
