package ananas.app.roadmap;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PathRecService extends Service {

	private String mLastURI;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		String last = this.mLastURI;
		String data = intent.getDataString();
		String type = intent.getType();
		String scheme = intent.getScheme();
		this.mLastURI = data;

		System.out.println("");
		System.out.println("last.data          =" + last);
		System.out.println("onStartCommand.data=" + data);
		System.out.println("onStartCommand.scheme=" + scheme);
		System.out.println("onStartCommand.type=" + type);

		super.onStartCommand(intent, flags, startId);
		return Service.START_STICKY;
	}

}
