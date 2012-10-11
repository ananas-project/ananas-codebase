package ananas.app.roadmap.util.kml;

import ananas.app.roadmap.util.task.TaskLoadKML;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class ArmKmlOverlay extends ItemizedOverlay<OverlayItem> {

	private MyLoadTask mCurTask;
	private final MapActivity mActivity;
	private OverlayItem[] mItemArray;
	private final MapView mMapView;

	public ArmKmlOverlay(MapActivity mapActivity, MapView mapView, Drawable icon) {
		super(boundCenterBottom(icon));
		this.mActivity = mapActivity;
		this.mMapView = mapView;
		this._setItems(null);
	}

	@Override
	protected OverlayItem createItem(int i) {
		return this.mItemArray[i];
	}

	@Override
	public int size() {
		return this.mItemArray.length;
	}

	public void startLoad() {

		MyLoadTask task = new MyLoadTask();
		Runnable runn = task.getWorkerRunnable();
		this._setCurrentTask(task);
		(new Thread(runn)).start();
	}

	private MyLoadTask _setCurrentTask(MyLoadTask task) {
		MyLoadTask pnew = task;
		MyLoadTask pold = null;
		synchronized (this) {
			pold = this.mCurTask;
			this.mCurTask = pnew;
		}
		return pold;
	}

	class MyLoadTask {

		private OverlayItem[] mArray;

		public Runnable getWorkerRunnable() {
			return new Runnable() {

				@Override
				public void run() {
					MyLoadTask.this._doLoad();
				}
			};
		}

		protected void _doLoad() {
			this._notifyBegin();
			try {
				TaskLoadKML task = new TaskLoadKML();
				task.run();
				OverlayItem[] array = task.listItems();
				this.mArray = array;
			} catch (Exception e) {
				e.printStackTrace();
			}
			this._notifyFinish();
		}

		private void _notifyBegin() {
			Runnable runn = new Runnable() {

				@Override
				public void run() {
					MyLoadTask.this._onBegin();
				}
			};
			ArmKmlOverlay.this.mActivity.runOnUiThread(runn);

		}

		private void _notifyFinish() {
			Runnable runn = new Runnable() {

				@Override
				public void run() {
					MyLoadTask.this._onFinish();
				}
			};
			ArmKmlOverlay.this.mActivity.runOnUiThread(runn);
		}

		private void _onBegin() {
			System.out.println(this + ".onBegin");
		}

		private void _onFinish() {
			System.out.println(this + ".onFinish");
			OverlayItem[] array = this.mArray;
			ArmKmlOverlay.this._setItems(array);
			ArmKmlOverlay.this.mMapView.invalidate();
		}
	}

	private void _setItems(OverlayItem[] array) {
		// must main thread call
		if (array == null) {
			array = new OverlayItem[0];
		}
		this.mItemArray = array;
		this.populate();
	}

	@Override
	public boolean onTap(int index) {
		OverlayItem item = this.mItemArray[index];
		AlertDialog.Builder dialog = new AlertDialog.Builder(this.mActivity);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}
}
