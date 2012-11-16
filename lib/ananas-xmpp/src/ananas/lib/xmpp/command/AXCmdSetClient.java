package ananas.lib.xmpp.command;

import ananas.lib.xmpp.api.AXAccount;
import ananas.lib.xmpp.api.AXClient;
import ananas.lib.xmpp.api.AXClientControl;
import ananas.lib.xmpp.api.AXClientPhase;

public class AXCmdSetClient extends AbstractAXCommand implements
		AXClientControl {

	private AXAccount mAccount;
	private final int mCtrl;
	private Class<?> mInterfaceClass;
	private Object mInterface;
	private AXClientPhase mPhase;

	public AXCmdSetClient(int ctrl) {
		this.mCtrl = ctrl;
	}

	@Override
	public void onExecuteBy(AXClient client) {
		if (client instanceof AXClientControl) {
			AXClientControl cc = (AXClientControl) client;
			switch (this.mCtrl) {
			case AXCmdSetClient.CTRL_GET_ACCOUNT:
				if (this.mAccount == null) {
					this.mAccount = cc.getAccount();
				}
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
			case AXCmdSetClient.CTRL_BIND_ACCOUNT:
				cc.bindAccount(this.mAccount);
				break;
			case AXCmdSetClient.CTRL_GET_PHASE:
				if (this.mPhase == null) {
					this.mPhase = cc.getPhase();
				}
				break;
			default:
			}
		}

		if (this.mInterfaceClass != null) {
			if (this.mInterface == null) {
				if (this.mInterfaceClass.isInstance(client)) {
					this.mInterface = client;
				}
			}
		}

	}

	@Override
	public AXAccount getAccount() {
		return this.mAccount;
	}

	@Override
	public void connect() {
	}

	@Override
	public void disconnect() {
	}

	@Override
	public void close() {
	}

	@Override
	public void bindAccount(AXAccount account) {
		this.mAccount = account;
	}

	@Override
	public Object getInterface(Class<?> aInterface) {
		if (this.mInterfaceClass == null) {
			this.mInterfaceClass = aInterface;
		}
		return this.mInterface;
	}

	@Override
	public AXClientPhase getPhase() {
		return this.mPhase;
	}

}
