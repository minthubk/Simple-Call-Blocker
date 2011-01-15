/*
 * Author - prajwolkumar.nakarmi@gmail.com
 * 
 */

package prajwol.app.callblocker;

import prajwol.app.callblocker.storage.StorageManager;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class Main extends ListActivity {
	private final String TAG = "Main";
	private MyListAdapter listAdapter = null;
	private StorageManager sm = App.getInstance().getStorageManager();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		listAdapter = new MyListAdapter(this, sm.getContacts(this));
		setListAdapter(listAdapter);
	}

	public void onBlockedClick(View v) {
		CheckBox cb = (CheckBox) v;
		String phoneNumber = v.getTag().toString();
		sm.updateBlockedList(phoneNumber, cb.isChecked());
		Log.i(TAG, phoneNumber + " is marked "
				+ (cb.isChecked() ? "blocked" : "allowed"));
	}
}