/*
 * Author - prajwolkumar.nakarmi@gmail.com
 * 
 */

package prajwol.app.callblocker;

import prajwol.app.callblocker.storage.StorageManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

public class CallListener extends BroadcastReceiver {
	private final String TAG = "CallListener";
	private StorageManager sm = App.getInstance().getStorageManager();
	private ITelephony telephony = App.getInstance().getTelephony();

	@Override
	public void onReceive(Context context, Intent intent) {
		String inNumber = intent.getExtras().getString(
				TelephonyManager.EXTRA_INCOMING_NUMBER);
		Log.i(TAG, inNumber + " is calling");

		// TODO use robust number checking logic
		if (sm.isBlocked(inNumber)) {
			try {
				telephony.endCall();
				Log.i(TAG, inNumber + " is blocked");
			} catch (RemoteException e) {
				Log.e(TAG, "onReceive: " + e.getMessage());
			}
		}
	}
}
