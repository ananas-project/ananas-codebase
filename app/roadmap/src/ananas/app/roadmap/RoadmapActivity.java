package ananas.app.roadmap;

import ananas.app.roadmap.RoadmapService.IRoadmapService2Binder;
import ananas.app.roadmap.util.ArmScaleOverlay;
import ananas.app.roadmap.util.StatusClient;
import ananas.app.roadmap.util.TaskRunner;
import ananas.app.roadmap.util.kml.ArmKmlOverlay;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class RoadmapActivity extends MapActivity {

	private MapView mMapView;
	private MyLocationOverlay mMyLocOver;
	private TextView mStatusView;
	private TaskRunner mTaskRunner;
	private ArmKmlOverlay mKmlOverlay;

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.ui_maps);

		//
		this.mMapView = (MapView) this.findViewById(R.id.mapview);
		this.mStatusView = (TextView) this.findViewById(R.id.textViewStatus);

		this._initMapView();
	}

	private void _initMapView() {

		this.mMapView = (MapView) findViewById(R.id.mapview);
		this.mMapView.setBuiltInZoomControls(true);
		MyLocationOverlay myloc = new MyLocationOverlay(this, this.mMapView);
		Drawable icon = this.getResources().getDrawable(
				R.drawable.default_point);
		ArmKmlOverlay kmlOverlay = new ArmKmlOverlay(this, this.mMapView, icon);
		this.mKmlOverlay = kmlOverlay;
		// myloc.enableMyLocation();
		this.mMapView.getOverlays().add(kmlOverlay);
		this.mMapView.getOverlays().add(myloc);
		this.mMapView.getOverlays().add(new ArmScaleOverlay());
		this.mMyLocOver = myloc;

	}

	@Override
	protected void onStart() {
		System.out.println(this + ".onStart()");
		super.onStart();
		this._startService();
		this._bindService();
	}

	@Override
	protected void onStop() {
		System.out.println(this + ".onStop()");
		this.mMyLocOver.disableCompass();
		this.mMyLocOver.disableMyLocation();
		this._unbindService();
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// new
		// 获取当前的菜单
		MenuInflater inflater = getMenuInflater();
		// 填充菜单
		inflater.inflate(R.menu.ui_maps_op_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_jump_to_mypos: {
			RoadmapActivity.this._gotoMyPos();
			break;
		}
		case R.id.menu_item_show_mypos_onoff: {
			RoadmapActivity.this._showMyPos();
			break;
		}
		case R.id.menu_item_record_mypos_onoff: {
			RoadmapActivity.this._recMyPos();
			break;
		}
		case R.id.menu_item_select_maptype_plain: {
			RoadmapActivity.this.mMapView.setSatellite(false);
			break;
		}
		case R.id.menu_item_select_maptype_sat: {
			RoadmapActivity.this.mMapView.setSatellite(true);
			RoadmapActivity.this.mMapView.setStreetView(false);
			RoadmapActivity.this.mMapView.setTraffic(false);
			break;
		}
		case R.id.menu_item_select_kml: {
			this.startActivity(new Intent(this, KmlFileBrowserActivity.class));
			break;
		}
		case R.id.menu_item_load_kml: {
			RoadmapActivity.this._loadKML();
			break;
		}
		case R.id.menu_item_exit_app: {
			RoadmapActivity.this._showExitAppDialog();
			break;
		}
		default:
		}

		this._updateStatus();

		return super.onOptionsItemSelected(item);
	}

	private void _loadKML() {
		/*
		 * TaskRunner runner = this._getTaskRunner(); Task task = new
		 * TaskLoadKML(); runner.addTask(task);
		 */

		this.mKmlOverlay.startLoad();
	}

	public TaskRunner getTaskRunner() {
		TaskRunner runner = this.mTaskRunner;
		if (runner == null) {
			this.mTaskRunner = runner = TaskRunner.Factory.newInstance();
		}
		return runner;
	}

	private void _showExitAppDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to exit?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// MyActivity.this.finish();
								RoadmapActivity.this._exitApp();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	private void _exitApp() {

		this.mMyLocOver.disableMyLocation();
		this.mMyLocOver.disableCompass();

		this.mBinder.exit();
		this._stopService();

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}

	private void _recMyPos() {
		StatusClient sc = new StatusClient(this.mBinder);
		sc.update();
		final boolean newVal = !sc.isRecording;
		sc.isRecording = newVal;
		sc.commit();
		if (newVal) {
			this.mBinder.startRecording();
		} else {
			this.mBinder.stopRecording();
		}
	}

	private void _gotoMyPos() {
		GeoPoint point = this.mMyLocOver.getMyLocation();
		if (point == null)
			return;
		this.mMapView.getController().animateTo(point);
	}

	private void _initShowMyPos() {
		StatusClient sc = new StatusClient(this.mBinder);
		sc.update();
		if (sc.isMyPosVisible) {
			this.mMyLocOver.enableMyLocation();
			this.mMyLocOver.enableCompass();
		} else {
			this.mMyLocOver.disableCompass();
			this.mMyLocOver.disableMyLocation();
		}
	}

	private void _showMyPos() {
		StatusClient sc = new StatusClient(this.mBinder);
		sc.update();
		final boolean newVal = !sc.isMyPosVisible;
		sc.isMyPosVisible = newVal;
		sc.commit();
		if (newVal) {
			this.mMyLocOver.enableMyLocation();
			this.mMyLocOver.enableCompass();
		} else {
			this.mMyLocOver.disableCompass();
			this.mMyLocOver.disableMyLocation();
		}
	}

	private void _startService() {
		Intent intent = new Intent(this, RoadmapService.class);
		this.startService(intent);
	}

	private void _stopService() {
		Intent intent = new Intent(this, RoadmapService.class);
		this.stopService(intent);
	}

	private void _bindService() {
		Intent intent = new Intent(this, RoadmapService.class);
		this.bindService(intent, this.mServConn, 0);
	}

	private void _unbindService() {
		this.unbindService(this.mServConn);
	}

	private RoadmapService.IRoadmapService2Binder mBinder;

	private final ServiceConnection mServConn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			RoadmapService.IRoadmapService2Binder binder = (IRoadmapService2Binder) service;
			RoadmapActivity.this.mBinder = binder;

			RoadmapActivity.this._updateStatus();
			RoadmapActivity.this._initShowMyPos();
			// RoadmapActivity.this._loadKML();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			RoadmapActivity.this.mBinder = null;
		}
	};

	private void _updateStatus() {
		IRoadmapService2Binder binder = this.mBinder;
		if (binder == null)
			return;
		StatusClient sc = new StatusClient(binder);
		sc.update();
		String strStatus = "";
		if (sc.isRecording) {
			strStatus += "[Rec]";
		}
		if (sc.isMyPosVisible) {
			strStatus += "[myPos]";
		}
		this.mStatusView.setText(strStatus);
	}

}
