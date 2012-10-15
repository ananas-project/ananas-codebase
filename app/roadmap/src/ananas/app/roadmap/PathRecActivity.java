package ananas.app.roadmap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PathRecActivity extends Activity {

	private int mCount;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.ui_path_rec);

		Button btnRec = (Button) this.findViewById(R.id.button_rec);
		btnRec.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				PathRecActivity.this._startRec();
			}
		});

		Button btnStop = (Button) this.findViewById(R.id.button_stop);
		btnStop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				PathRecActivity.this._stopRec();
			}
		});

	}

	protected void _startRec() {
		this._sendCommand("http://" + PathRecService.class.getName()
				+ "/start?q=" + (this.mCount++));
	}

	private void _sendCommand(String uri) {
		Intent intent = new Intent(this, PathRecService.class);
		Uri data = Uri.parse(uri);
		String type = "text/nmea";
		intent.setDataAndType(data, type);
		this.startService(intent);
	}

	protected void _stopRec() {
		this._sendCommand("http://" + PathRecService.class.getName()
				+ "/stop?q=" + (this.mCount++));
	}
}
