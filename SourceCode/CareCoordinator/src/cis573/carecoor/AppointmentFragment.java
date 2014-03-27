package cis573.carecoor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import cis573.carecoor.adapter.CommonAdapter;
import cis573.carecoor.bean.Appointment;
import cis573.carecoor.data.DataCenter;
import cis573.carecoor.reminder.ReminderCenter;
import cis573.carecoor.utils.Utils;

public class AppointmentFragment extends Fragment {

	public static final String TAG = "AppointmentFragment";
	
	private ListView mListView;
	private Button mBtnNew;
	
	private AppointmentAdapter mAdapter = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new AppointmentAdapter(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.appointment_fragment, container, false);
		mListView = (ListView) view.findViewById(R.id.appointment_list);
		mListView.setOnItemLongClickListener(onItemLongClick);
		TextView tvEmpty = (TextView) view.findViewById(R.id.appointment_empty);
		mListView.setEmptyView(tvEmpty);
		mBtnNew = (Button) view.findViewById(R.id.appointment_add_button);
		mBtnNew.setOnClickListener(onNewClick);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter.setData(DataCenter.getAppointments(getActivity()));
		mListView.setAdapter(mAdapter);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK) {
			mAdapter.setData(DataCenter.getAppointments(getActivity()));
			mAdapter.notifyDataSetChanged();
		}
	}
	
	private OnItemLongClickListener onItemLongClick = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			final Appointment item = (Appointment) parent.getItemAtPosition(position);
			new AlertDialog.Builder(getActivity())
			.setTitle(R.string.dialog_title_remove_appointment)
			.setMessage(R.string.msg_remove_appointment)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					DataCenter.removeAppointments(getActivity(), item);
					ReminderCenter.cancelAlarm(getActivity(), item);
					mAdapter.setData(DataCenter.getAppointments(getActivity()));
					mAdapter.notifyDataSetChanged();
				}
			}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					return;
				}
			}).show();
			return false;
		}
	};

	private OnClickListener onNewClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), NewAppointmentActivity.class);
			startActivityForResult(intent, 0);
		}
	};
	
	public static class AppointmentAdapter extends CommonAdapter<Appointment> {

		public AppointmentAdapter(Context context) {
			super(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			if(convertView == null) {
				convertView = View.inflate(mContext, R.layout.appointment_item, null);
				vh = new ViewHolder();
				vh.time = (TextView) convertView.findViewById(R.id.appointment_time_text);
				vh.remind = (TextView) convertView.findViewById(R.id.appointment_remind_text);
				vh.detail = (TextView) convertView.findViewById(R.id.appointment_detail_text);
				vh.status = (TextView) convertView.findViewById(R.id.appointment_status_text);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			
			Appointment item = (Appointment) getItem(position);
			if(item != null) {
				vh.time.setText(Utils.getDateTimeString(item.getDate()));
				vh.remind.setText(mContext.getString(R.string.appointment_item_remind,
						mContext.getResources()
						.getStringArray(R.array.appointment_remind_options)[item.getRemind()]));
				vh.detail.setText(mContext.getString(R.string.appointment_item_detail, item.getDetail()));
				int dayDiff = Utils.getDayDiffFromNow(item.getDate());
				if(dayDiff > 0) {	// days remaining
					if(dayDiff == 1) {
						vh.status.setText(R.string.appointment_status_1day);
					} else {
						vh.status.setText(mContext.getString(R.string.appointment_status_days, dayDiff));
					}
					vh.status.setTextColor(mContext.getResources().getColor(R.color.green_text));
				} else if (dayDiff == 0) {	// today
					vh.status.setText(R.string.appointment_status_today);
					vh.status.setTextColor(mContext.getResources().getColor(R.color.orange_light_text));
				} else {	// passed
					vh.status.setText(R.string.appointment_status_passed);
					vh.status.setTextColor(mContext.getResources().getColor(R.color.gray_text));
				}
			}
			return convertView;
		}
		
		private static class ViewHolder {
			TextView time;
			TextView remind;
			TextView detail;
			TextView status;
		}
	}
}
