package ananas.lib.xmpp.api;

public interface AXClientPhase {

	AXClientPhase init = F._n(0, "init");
	AXClientPhase online = F._n(1, "online");
	AXClientPhase offline = F._n(2, "offline");
	AXClientPhase connect = F._n(3, "connect");
	AXClientPhase disconnect = F._n(4, "disconnect");
	AXClientPhase unknow = F._n(5, "unknow");
	AXClientPhase dropped = F._n(6, "dropped");
	AXClientPhase closed = F._n(7, "closed");
	AXClientPhase error = F._n(8, "error");

	class F {

		private static AXClientPhase _n(int code, String text) {
			return new MyImpl(code, text);
		}

		private static class MyImpl implements AXClientPhase {

			public MyImpl(int code, String text) {
				// TODO Auto-generated constructor stub
			}

		}

	}
}
