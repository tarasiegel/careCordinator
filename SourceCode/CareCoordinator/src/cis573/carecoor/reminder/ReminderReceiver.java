package cis573.carecoor.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import cis573.carecoor.R;
import cis573.carecoor.TakeMedicineActivity;
import cis573.carecoor.bean.Appointment;
import cis573.carecoor.bean.Medicine;
import cis573.carecoor.bean.Schedule;
import cis573.carecoor.data.DataCenter;
import cis573.carecoor.utils.Const;

public class ReminderReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(Const.ACTION_REMINDER_ALARM.equals(intent.getAction())) {
			Uri data = intent.getData();
			String type = data.getHost();
			if("schedule".equals(type)) {
				Schedule schedule = (Schedule) intent.getSerializableExtra(Const.EXTRA_SCHEDULE);
				if(schedule != null) {
					showNotification(context, schedule);
				}
			} else if("appointment".equals(type)) {
				Appointment appointment = (Appointment) intent.getSerializableExtra(Const.EXTRA_APPOINTMENT);
				if(appointment != null) {
					showNotification(context, appointment);
				}
			}
		}
	}
	
	private void showNotification(Context context, Schedule schedule) {
		String medName = "";
		Medicine med = null;
		if(schedule != null) {
			med = schedule.getMedicine();
		}
		if(med != null) {
			medName = med.getName();
		}
		String text = context.getString(R.string.reminder_text, medName);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
		.setTicker(context.getString(R.string.reminder_ticker))
		.setContentTitle(context.getString(R.string.reminder_title))
		.setContentText(text)
		.setSmallIcon(R.drawable.ic_launcher)
		.setDefaults(Notification.DEFAULT_SOUND)
		.setAutoCancel(true);
		
		Intent newIntent = new Intent(context, TakeMedicineActivity.class);
		newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		newIntent.setData(Uri.parse(ReminderCenter.getReminderId(schedule)));
		newIntent.putExtra(Const.EXTRA_SCHEDULE, schedule);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, newIntent, 0);
		builder.setContentIntent(pIntent);
		
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(DataCenter.getScheduleId(context, schedule), builder.build());
	}
	
	private void showNotification(Context context, Appointment appointment) {
		String detail = appointment.getDetail();
		String text = context.getString(R.string.appointment_reminder, detail);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
		.setTicker(text)
		.setContentTitle(context.getString(R.string.appointment_reminder_title))
		.setContentText(detail)
		.setSmallIcon(R.drawable.ic_launcher)
		.setDefaults(Notification.DEFAULT_SOUND)
		.setAutoCancel(true);
		
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(DataCenter.getAppointmentId(context, appointment), builder.build());
	}
}
