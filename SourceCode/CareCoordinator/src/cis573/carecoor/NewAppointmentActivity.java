package cis573.carecoor;

import java.util.Calendar;
import java.util.Locale;

import cis573.carecoor.bean.Appointment;
import cis573.carecoor.data.DataCenter;
import cis573.carecoor.reminder.ReminderCenter;
import cis573.carecoor.utils.Utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class NewAppointmentActivity extends BannerActivity {

	public static final String TAG = "NewAppointmentActivity";
	
	private TextView mTvDate;
	private TextView mTvTime;
	private TextView mTvRemind;
	private EditText mEtDetail;
	
	private Calendar mCalendar;
	private DatePickerDialog mDatePickerDialog;
	private TimePickerDialog mTimePickerDialog;
	private AlertDialog mRemindDialog;
	private String[] mRemindOptions;
	private int mRemind = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.new_appointment_activity);
		setBannerTitle(R.string.title_appointment_new);
		initCalendar();
		initViews();
		initPickerDialogs();
	}

	private void initViews() {
		mTvDate = (TextView) findViewById(R.id.new_appointment_date_text);
		mTvTime = (TextView) findViewById(R.id.new_appointment_time_text);
		mTvRemind = (TextView) findViewById(R.id.new_appointment_remind_text);
		mEtDetail = (EditText) findViewById(R.id.new_appointment_detail);
		mTvDate.setText(Utils.getDateString(mCalendar.getTime()));
		mTvTime.setText(Utils.getTimeString(mCalendar.getTime()));
		mRemindOptions = getResources().getStringArray(R.array.appointment_remind_options);
		mTvRemind.setText(mRemindOptions[mRemind]);
	}

	private void initCalendar() {
		mCalendar = Calendar.getInstance(Locale.US);
		mCalendar.set(Calendar.HOUR_OF_DAY, 10);
		mCalendar.set(Calendar.MINUTE, 0);
		mCalendar.set(Calendar.SECOND, 0);
		mCalendar.set(Calendar.MILLISECOND, 0);
	}

	private void initPickerDialogs() {
		mDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				mCalendar.set(year, monthOfYear, dayOfMonth);
				mTvDate.setText(Utils.getDateString(mCalendar.getTime()));
			}
		}, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
		
		mTimePickerDialog = new TimePickerDialog(this, new OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				mCalendar.set(Calendar.MINUTE, minute);
				mTvTime.setText(Utils.getTimeString(mCalendar.getTime()));
			}
		}, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false);
		
		mRemindDialog = new AlertDialog.Builder(this)
		.setTitle(R.string.appointment_new_remind)
		.setItems(R.array.appointment_remind_options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mRemind = which;
				mTvRemind.setText(mRemindOptions[mRemind]);
			}
		}).create();
	}

	public void onItemClick(View v) {
		int id = v.getId();
		if(id == R.id.new_appointment_date) {
			mDatePickerDialog.show();
		} else if(id == R.id.new_appointment_time) {
			mTimePickerDialog.show();
		} else if(id == R.id.new_appointment_remind) {
			mRemindDialog.show();
		}
	}
	
	public void onOkClick(View v) {
		String detail = mEtDetail.getText().toString();
		Appointment appointment = new Appointment();
		appointment.setDate(mCalendar.getTime());
		appointment.setDetail(detail);
		appointment.setRemind(mRemind);
		
		DataCenter.addAppointments(NewAppointmentActivity.this, appointment);
		ReminderCenter.addReminder(this, appointment);
		setResult(RESULT_OK);
		finish();
	}
}
