/*
 * Author - prajwolkumar.nakarmi@gmail.com
 */

package prajwol.app.callblocker.storage;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDB extends SQLiteOpenHelper {
	private SQLiteDatabase db;

	private static final String DATABASE_NAME = "call-blocker";
	private static final int DATABASE_VERSION = 1;

	private static final String TABLE_BLOCKED = "blocked_list";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_BLOCKED = "blocked";

	private static final String DATABASE_CREATE_QUERY = "CREATE TABLE "
			+ TABLE_BLOCKED + " (" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_BLOCKED
			+ " text  null);";

	public LocalDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		open();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE_QUERY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO: migrate old blocked list
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOCKED);
		onCreate(db);
	}

	public void open() throws SQLException {
		db = getWritableDatabase();
	}

	public long insert(String phoneNumber) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(COLUMN_BLOCKED, phoneNumber);

		remove(phoneNumber);
		return db.insert(TABLE_BLOCKED, null, initialValues);
	}

	public boolean remove(String phoneNumber) {
		return db.delete(TABLE_BLOCKED, COLUMN_BLOCKED + " = ? ",
				new String[] { phoneNumber }) == 1;
	}

	public List<String> getAll() {
		List<String> blockedList = new ArrayList<String>();
		Cursor mCursor = db.query(true, TABLE_BLOCKED,
				new String[] { COLUMN_BLOCKED }, null, null, null, null, null,
				null);

		if (mCursor != null) {
			while (mCursor.moveToNext()) {
				blockedList.add(mCursor.getString(0));
			}
		}
		return blockedList;
	}

}
