/*
 * Author - prajwolkumar.nakarmi@gmail.com
 */

package prajwol.app.callblocker.storage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class StorageManager {
	private LocalDB localDB;

	public StorageManager(Context ctx) {
		localDB = new LocalDB(ctx);
	}

	public List<String> getMyBlockedList() {
		return localDB.getAll();
	}

	public boolean isBlocked(String phoneNumber) {
		// TODO check effectively, i.e without loading all list
		return getMyBlockedList().contains(phoneNumber);
	}

	public void updateBlockedList(String phoneNumber, boolean isBlocked) {
		if (isBlocked) {
			localDB.insert(phoneNumber);
		} else {
			localDB.remove(phoneNumber);
		}
	}

	public List<MyContact> getContacts(Activity activity) {
		String[] projection = new String[] { Contacts._ID,
				Contacts.DISPLAY_NAME };
		String sortOrder = Contacts.DISPLAY_NAME + " ASC";

		List<MyContact> myContacts = new ArrayList<MyContact>();

		Cursor cc = activity.managedQuery(Contacts.CONTENT_URI, projection,
				null, null, sortOrder);

		while (cc.moveToNext()) {
			String _ID = cc.getString(cc.getColumnIndex(Contacts._ID));
			String displayName = cc.getString(cc
					.getColumnIndex(Contacts.DISPLAY_NAME));

			Cursor pc = activity.managedQuery(Phone.CONTENT_URI, null,
					Phone.CONTACT_ID + " = " + _ID, null, null);

			while (pc.moveToNext()) {
				String phoneNumber = pc.getString(pc
						.getColumnIndex(Phone.NUMBER));
				myContacts.add(new MyContact(displayName, phoneNumber,
						isBlocked(phoneNumber)));
			}
			pc.close();
		}
		cc.close();

		return myContacts;
	}
}
