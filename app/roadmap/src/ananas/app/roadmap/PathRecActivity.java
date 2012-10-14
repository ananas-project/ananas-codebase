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

		Intent intent = new Intent(this, PathRecService.class);
		Uri data = Uri.parse("http://abc:123/abc?q=" + (this.mCount++));
		String type = "text/nmea";
		intent.setDataAndType(data, type);
		this.startService(intent);

	}

	protected void _stopRec() {
		Intent intent = new Intent(this, PathRecService.class);

		this.stopService(intent);

	}
}
