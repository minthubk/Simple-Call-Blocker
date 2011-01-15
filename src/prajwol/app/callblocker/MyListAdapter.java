/*
 * Author - prajwolkumar.nakarmi@gmail.com
 * 
 */

package prajwol.app.callblocker;

import java.util.ArrayList;
import java.util.List;

import prajwol.app.callblocker.storage.MyContact;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter {
	private List<MyContact> myContacts = new ArrayList<MyContact>();
	private LayoutInflater inflater;

	public MyListAdapter(Context context, List<MyContact> myContacts) {
		this.myContacts = myContacts;
		this.inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return myContacts.size();
	}

	public Object getItem(int position) {
		return myContacts.get(position);
	}

	public void setBlocked(boolean value, int position) {
		myContacts.get(position).setBlocked(value);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = inflater.inflate(R.layout.contact_row, parent, false);
		}

		MyContact contact = myContacts.get(position);
		TextView tv = (TextView) v.findViewById(R.id.textViewContactName);
		tv.setText(contact.getDisplayName());

		CheckBox cb = (CheckBox) v.findViewById(R.id.checkBoxBlocked);
		cb.setChecked(contact.isBlocked());
		cb.setTag(contact.getPhoneNumber());

		return v;
	}
}