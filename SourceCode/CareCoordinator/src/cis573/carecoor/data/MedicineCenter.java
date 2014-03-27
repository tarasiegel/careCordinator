package cis573.carecoor.data;

import java.util.List;
import java.util.Locale;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.res.AssetManager;
import cis573.carecoor.R;
import cis573.carecoor.bean.Medicine;
import cis573.carecoor.utils.Logger;
import cis573.carecoor.utils.ResourceKit;

public class MedicineCenter {

	public static final String TAG = "MedicineCenter";
	
	private static List<Medicine> mMedList = null;
	
	public static void init(Context context) {
		String json = ResourceKit.readAsString(context, R.raw.medicine_list);
		try {
			mMedList = JsonFactory.parseMedicineList(new JSONArray(json));
		} catch (JSONException e) {
			Logger.e(TAG, e.getMessage(), e);
		}
	}
	
	public static List<Medicine> getMedicineList(Context context) {
		if(mMedList == null) {
			init(context);
		}
		return mMedList;
	}
	
	public static int getMedicineImageRes(Context context, Medicine medicine) {
		int id = 0;
		if(medicine != null) {
			id = getMedicineImageResByName(context, medicine.getName());
		}
		return id;
	}
	
	public static int getMedicineImageResByName(Context context, String name) {
		return context.getResources().getIdentifier("drugpic_" + name.substring(0, 3).toLowerCase(Locale.US),
				"drawable", context.getPackageName());
	}
}
