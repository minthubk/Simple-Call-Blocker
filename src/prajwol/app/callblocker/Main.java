/*
 * Author - prajwolkumar.nakarmi@gmail.com
 * 
 */

package prajwol.app.callblocker;

import java.util.List;

import prajwol.app.callblocker.storage.MyContact;
import prajwol.app.callblocker.storage.StorageManager;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class Main extends ListActivity {
	private final String TAG = "Main";
	private MyListAdapter listAdapter = null;
	private StorageManager sm = App.getInstance().getStorageManager();

	private List<MyContact> myContacts = null;
	private Message myMessage;
	private ProgressDialog busyDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		busyDialog = new ProgressDialog(this);
		busyDialog.setIndeterminate(true);
		busyDialog.setMessage("Please wait ...");

		busyDialog.show();
		new Thread() {
			public void run() {
				myContacts = sm.getContacts(getMyReference());
				myMessage = Message.obtain();
				myMessage.setTarget(myHandler);
				myMessage.sendToTarget();
			}
		}.start();
	}

	public void onBlockedClick(View v) {
		CheckBox cb = (CheckBox) v;
		String phoneNumber = v.getTag().toString();
		sm.updateBlockedList(phoneNumber, cb.isChecked());
		Log.i(TAG, phoneNumber + " is marked "
				+ (cb.isChecked() ? "blocked" : "allowed"));
	}

	private Activity getMyReference() {
		return this;
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			listAdapter = new MyListAdapter(getMyReference(), myContacts);
			setListAdapter(listAdapter);
			busyDialog.dismiss();
		}
	};
}