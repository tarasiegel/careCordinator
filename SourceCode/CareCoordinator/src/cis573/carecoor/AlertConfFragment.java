package cis573.carecoor;

import cis573.carecoor.bean.Contact;
import cis573.carecoor.utils.Const;
import cis573.carecoor.utils.Logger;
import cis573.carecoor.utils.MyToast;
import cis573.carecoor.utils.PreferenceUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class AlertConfFragment extends Fragment implements OnPhoneChangedListener {
	
	public static final String TAG = "AlertConfFragment";
	
	private static final int PRIMARY_PHONE = 0;
	private static final int SECONDARY_PHONE = 1;

	private View mPrimaryNumView;
	private View mSecondaryNumView;
	private TextView mTvPrimaryNum;
	private TextView mTvSecondaryNum;
	private CheckBox mCbCall;
	private CheckBox mCbText;
	private PhoneDialogFragment mPrimaryPhoneDialog;
	private PhoneDialogFragment mSecondaryPhoneDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPrimaryPhoneDialog = PhoneDialogFragment.newInstance(PRIMARY_PHONE);
		mSecondaryPhoneDialog = PhoneDialogFragment.newInstance(SECONDARY_PHONE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.alert_conf_fragment, container, false);
		mPrimaryNumView = view.findViewById(R.id.alert_conf_primary);
		mPrimaryNumView.setOnClickListener(mOnNumsClick);
		mSecondaryNumView = view.findViewById(R.id.alert_conf_secondary);
		mSecondaryNumView.setOnClickListener(mOnNumsClick);
		mTvPrimaryNum = (TextView) view.findViewById(R.id.alert_conf_primary_text);
		setAlertPhoneNumberText(mTvPrimaryNum, PreferenceUtil.getPrimaryAlertNumber(getActivity()));
		mTvSecondaryNum = (TextView) view.findViewById(R.id.alert_conf_secondary_text);
		setAlertPhoneNumberText(mTvSecondaryNum, PreferenceUtil.getSecondaryAlertNumber(getActivity()));
		mCbCall = (CheckBox) view.findViewById(R.id.alert_call_cb);
		mCbCall.setChecked(PreferenceUtil.getEnableAlertCall(getActivity()));
		mCbCall.setOnCheckedChangeListener(mOnCheckedChanged);
		mCbText = (CheckBox) view.findViewById(R.id.alert_text_cb);
		mCbText.setChecked(PreferenceUtil.getEnableAlertText(getActivity()));
		mCbText.setOnCheckedChangeListener(mOnCheckedChanged);
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Logger.i(TAG, String.format("Back from pick contact requestCode=%d, resultCode=%d",
				requestCode, resultCode));
		if(resultCode == Activity.RESULT_OK) {
			Contact contact = null;
			if(data != null) {
				contact = (Contact) data.getSerializableExtra(Const.EXTRA_CONTACT);
			}
			if(contact != null) {
				if(requestCode == PRIMARY_PHONE) {
					onPrimaryNumChanged(contact.getPhone());
				} else if(requestCode == SECONDARY_PHONE) {
					onSecondaryNumChanged(contact.getPhone());
				}
			}
		}
	}

	private void setAlertPhoneNumberText(TextView textView, String phone) {
		if("".equals(phone)) {
			phone = getString(R.string.alertconf_phone_unset);
		}
		textView.setText(phone);
	}
	
	private OnClickListener mOnNumsClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.alert_conf_primary:
				mPrimaryPhoneDialog.setTargetFragment(AlertConfFragment.this, 0);
				mPrimaryPhoneDialog.show(getFragmentManager(), "primary");
				break;
			case R.id.alert_conf_secondary:
				mSecondaryPhoneDialog.setTargetFragment(AlertConfFragment.this, 0);
				mSecondaryPhoneDialog.show(getFragmentManager(), "secondary");
				break;
			}
		}
	};
	
	public static class PhoneDialogFragment extends DialogFragment {

		private EditText mEtPhone;
		private Button mBtnImport;
		private int mType;
		
		public static PhoneDialogFragment newInstance(int type) {
			PhoneDialogFragment f = new PhoneDialogFragment();
			Bundle args = new Bundle();
			args.putInt("type", type);
			f.setArguments(args);
			return f;
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Bundle args = getArguments();
			if(args != null) {
				mType = args.getInt("type");
			}
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			View view = View.inflate(getActivity(), R.layout.dialog_phone_num, null);
			mEtPhone = (EditText) view.findViewById(R.id.dialog_add_contact_edittext);
			mBtnImport = (Button) view.findViewById(R.id.dialog_add_contact_import_btn);
			mBtnImport.setOnClickListener(onImportClick);
			String title = "";
			if(mType == PRIMARY_PHONE) {
				title = getActivity().getString(R.string.alertconf_primary_num);
			} else if(mType == SECONDARY_PHONE) {
				title = getActivity().getString(R.string.alertconf_secondary_num);
			}
			return new AlertDialog.Builder(getActivity()).setView(view)
				.setTitle(title)
				.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String number = mEtPhone.getText().toString();
						OnPhoneChangedListener listener = (OnPhoneChangedListener) getTargetFragment();
						if(listener != null) {
							if(mType == PRIMARY_PHONE) {
								listener.onPrimaryNumChanged(number);
							} else if(mType == SECONDARY_PHONE) {
								listener.onSecondaryNumChanged(number);
							}
						}
					}
				}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				}).create();
		}

		private OnClickListener onImportClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				OnPhoneChangedListener listener = (OnPhoneChangedListener) getTargetFragment();
				if(listener != null) {
					listener.onImportFromContact(mType);
				}
				getDialog().dismiss();
			}
		};
	}

	@Override
	public void onPrimaryNumChanged(String number) {
		if(!checkPhoneNumber(number)) {
			MyToast.show(getActivity(), R.string.msg_empty_phone_number);
			return;
		}
		PreferenceUtil.savePrimaryAlertNumber(getActivity(), number);
		mTvPrimaryNum.setText(number);
	}

	@Override
	public void onSecondaryNumChanged(String number) {
		if(!checkPhoneNumber(number)) {
			MyToast.show(getActivity(), R.string.msg_empty_phone_number);
			return;
		}
		PreferenceUtil.saveSecondaryAlertNumber(getActivity(), number);
		mTvSecondaryNum.setText(number);
	}
	
	@Override
	public void onImportFromContact(int type) {
		Intent intent = new Intent(getActivity(), PickFromContactsActivity.class);
		startActivityForResult(intent, type);
	}
	
	private static boolean checkPhoneNumber(String number) {
		return !"".equals(number);
	}

	private OnCheckedChangeListener mOnCheckedChanged = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			switch (buttonView.getId()) {
			case R.id.alert_call_cb:
				PreferenceUtil.saveEnableAlertCall(getActivity(), isChecked);
				break;
			case R.id.alert_text_cb:
				PreferenceUtil.saveEnableAlertText(getActivity(), isChecked);
				break;
			}
		}
	};
}
