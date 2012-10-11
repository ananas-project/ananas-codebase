package ananas.app.roadmap.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class ArmScaleOverlay extends Overlay {

	private final Paint mPaint0;
	private final Paint mPaint1;
	private ScaleProperties mScalePropertiesCache;
	private final Rect mRectCache = new Rect();

	public ArmScaleOverlay() {

		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStrokeWidth(3);
		paint.setColor(Color.WHITE);
		paint.setTextSize(16);
		this.mPaint0 = paint;

		paint = new Paint(paint);
		paint.setColor(Color.BLACK);
		this.mPaint1 = paint;

	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// super.draw(canvas, mapView, shadow);

		ScaleProperties sp = this.mScalePropertiesCache;
		sp = this.calcScaleProperties(mapView, sp);
		this.mScalePropertiesCache = sp;

		int x = 10;
		int y = 10;
		this.drawSimpleScale(canvas, sp, x - 1, y - 1, this.mPaint0);
		this.drawSimpleScale(canvas, sp, x - 1, y + 0, this.mPaint0);
		this.drawSimpleScale(canvas, sp, x - 1, y + 1, this.mPaint0);
		this.drawSimpleScale(canvas, sp, x + 0, y - 1, this.mPaint0);
		// this.drawSimpleScale(canvas, sp, x + 0, y + 0, this.mPaint0);
		this.drawSimpleScale(canvas, sp, x + 0, y + 1, this.mPaint0);
		this.drawSimpleScale(canvas, sp, x + 1, y - 1, this.mPaint0);
		this.drawSimpleScale(canvas, sp, x + 1, y + 0, this.mPaint0);
		this.drawSimpleScale(canvas, sp, x + 1, y + 1, this.mPaint0);
		this.drawSimpleScale(canvas, sp, x, y, this.mPaint1);

	}

	private void drawSimpleScale(Canvas canvas, ScaleProperties sp, int x,
			int y, Paint paint) {
		canvas.drawLine(x, y, x + sp.width, y, paint);
		String text = sp.distance + " " + sp.distanceUnit;
		Rect bounds = this.mRectCache;
		paint.getTextBounds(text, 0, text.length(), bounds);
		canvas.drawText(text, x, y + bounds.height() + 5, paint);
	}

	class ScaleProperties {

		public float width = 100;
		public int distance = 10;
		public String distanceUnit = "km";
	}

	private ScaleProperties calcScaleProperties(MapView mapView,
			ScaleProperties sp) {

		final double earth_R = 6378137;
		final double earth_C = 2 * Math.PI * earth_R;

		final int height = mapView.getHeight();
		// final int width = mapView.getWidth();
		final int full_width = 200; // width - 100;

		final Projection proj = mapView.getProjection();
		final GeoPoint gp1 = proj.fromPixels(0, height / 2);
		final GeoPoint gp2 = proj.fromPixels(full_width, height / 2);

		final double lat1 = gp1.getLatitudeE6() * 0.000001;
		final double lon1 = gp1.getLongitudeE6() * 0.000001;
		final double lat2 = gp2.getLatitudeE6() * 0.000001;
		final double lon2 = gp2.getLongitudeE6() * 0.000001;

		final double coslat = Math.cos((lat1 + lat2) * Math.PI / 360.0);

		// to meters
		double diff = Math.abs(lon1 - lon2) % 180;
		final double full_meters = coslat * earth_C * (diff / 360);

		// ////////////

		int[] scale_len = { 1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, 2000,
				5000, 10000, 20000, 50000, 100000, 200000, 500000, 1000000,
				2000000, 5000000, 10000000 };
		int scale_meters = 1;
		for (int i = scale_len.length - 1; i > 0; i--) {
			if (scale_len[i] < full_meters) {
				scale_meters = scale_len[i];
				break;
			}
		}
		if (scale_meters == 1) {
			System.err.println("meters==1");
			System.err.println("lat1:" + lat1 + " lon1:" + lon1 + " lat2:"
					+ lat2 + " lon2:" + lon2);
		}

		if (sp == null)
			sp = new ScaleProperties();

		if (scale_meters >= 1000) {
			sp.distance = scale_meters / 1000;
			sp.distanceUnit = "km";
		} else {
			sp.distance = scale_meters;
			sp.distanceUnit = "m";
		}
		sp.width = (float) ((scale_meters / full_meters) * full_width);

		return sp;
	}
}
