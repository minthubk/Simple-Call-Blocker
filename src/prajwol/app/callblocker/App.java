/*
 * Author - prajwolkumar.nakarmi@gmail.com
 * 
 * Acknowledgment - The logic for getting ITelephony is implemented as explained in
 * http://prasanta-paul.blogspot.com/2010/09/call-control-in-android.html
 */

package prajwol.app.callblocker;

import java.lang.reflect.Method;

import prajwol.app.callblocker.storage.StorageManager;
import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

public class App extends Application {
	private final String TAG = "App";
	private static App singleton;
	private StorageManager sm = null;
	private ITelephony telephony = null;

	@Override
	public final void onCreate() {
		super.onCreate();
		singleton = this;
		sm = new StorageManager(this);
		telephony = getTeleService(this);
	}

	private ITelephony getTeleService(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		try {
			Class<?> c = Class.forName(tm.getClass().getName());
			Method m = c.getDeclaredMethod("getITelephony");
			m.setAccessible(true);
			ITelephony telephony = (ITelephony) m.invoke(tm);
			return telephony;
		} catch (Exception e) {
			Log.e(TAG, "getTeleService: " + e.getMessage());
			return null;
		}
	}

	public static App getInstance() {
		return singleton;
	}

	public StorageManager getStorageManager() {
		return sm;
	}

	public ITelephony getTelephony() {
		return telephony;
	}
}
