/*
 * Author - prajwolkumar.nakarmi@gmail.com
 * 
 */

package prajwol.app.callblocker.storage;

public class MyContact {
	private String displayName;
	private String phoneNumber;
	private boolean isBlocked;

	public MyContact(String displayName, String phoneNumber, boolean isBlocked) {
		this.displayName = displayName + "(" + phoneNumber + ")";
		this.phoneNumber = phoneNumber;
		this.isBlocked = isBlocked;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName + "(" + phoneNumber + ")";
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		this.displayName = displayName + "(" + phoneNumber + ")";
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
}
