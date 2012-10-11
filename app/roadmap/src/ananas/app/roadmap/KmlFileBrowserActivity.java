package ananas.app.roadmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class KmlFileBrowserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.ui_kml_browser);

		ListView lv = (ListView) this.findViewById(R.id.listViewFiles);
		lv.setAdapter(null);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 获取当前的菜单
		MenuInflater inflater = getMenuInflater();
		// 填充菜单
		inflater.inflate(R.menu.ui_sel_kml_op_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_back: {
			this.startActivity(new Intent(this, RoadmapActivity.class));
			break;
		}
		default:
		}
		return super.onOptionsItemSelected(item);
	}
}
