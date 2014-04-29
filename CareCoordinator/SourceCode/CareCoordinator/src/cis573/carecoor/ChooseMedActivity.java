package cis573.carecoor;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cis573.carecoor.bean.Medicine;
import cis573.carecoor.data.MedicineCenter;
import cis573.carecoor.utils.Const;

public class ChooseMedActivity extends BannerActivity {

	public static final String TAG = "ChooseMedActivity";
	
	private ListView mLvMedicines;
	private MedicineAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_med_activity);
		setBannerTitle(R.string.title_new_schedule);
		initViews();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK) {	// Back from set schedule
			setResult(RESULT_OK);
			finish();
		}
	}

	private void initViews() {
		mLvMedicines = (ListView) findViewById(R.id.choose_med_list);
		mLvMedicines.setOnItemClickListener(onItemClick);
		mAdapter = new MedicineAdapter(ChooseMedActivity.this);
		mAdapter.setMedicineList(MedicineCenter.getMedicineList(ChooseMedActivity.this));
		mLvMedicines.setAdapter(mAdapter);
	}
	
	private OnItemClickListener onItemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Medicine item = (Medicine) parent.getItemAtPosition(position);
			Intent intent = new Intent(ChooseMedActivity.this, SetScheduleActivity.class);
			intent.putExtra(Const.EXTRA_MEDICINE, item);
			startActivityForResult(intent, 0);
		}
	};
	
	public static class MedicineAdapter extends BaseAdapter {

		private Context mContext;
		private List<Medicine> mMedicineList;
		
		public MedicineAdapter(Context context) {
			this.mContext = context;
		}

		public void setMedicineList(List<Medicine> medicineList) {
			this.mMedicineList = medicineList;
		}

		@Override
		public int getCount() {
			return mMedicineList != null ? mMedicineList.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return mMedicineList != null ? mMedicineList.get(position) : null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh;
			if(convertView == null) {
				convertView = View.inflate(mContext, R.layout.medicine_list_item, null);
				vh = new ViewHolder();
				vh.name = (TextView) convertView.findViewById(R.id.medlist_item_name);
				vh.image = (ImageView) convertView.findViewById(R.id.medlist_item_image);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			Medicine item = (Medicine) getItem(position);
			if(item != null) {
				vh.name.setText(item.getName());
				vh.image.setImageResource(MedicineCenter.getMedicineImageRes(mContext, item));
			}
			return convertView;
		}
		
		private static class ViewHolder {
			TextView name;
			ImageView image;
		}
	}
}
