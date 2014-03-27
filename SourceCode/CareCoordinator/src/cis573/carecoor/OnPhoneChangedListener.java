package cis573.carecoor;

public interface OnPhoneChangedListener {
	
	void onPrimaryNumChanged(String number);
	
	void onSecondaryNumChanged(String number);
	
	void onImportFromContact(int type);
}
