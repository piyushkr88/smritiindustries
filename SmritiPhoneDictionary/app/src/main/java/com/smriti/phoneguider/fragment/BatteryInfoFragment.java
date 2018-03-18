/**
 * 
 */
package com.smriti.phoneguider.fragment;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.smriti.phoneguider.R;
import com.smriti.phoneguider.data.Constant;

import java.util.ArrayList;


/**
 * @author pikumar7
 *
 */
public class BatteryInfoFragment extends Fragment implements OnItemClickListener  {
	private Activity mActiviy;
	private ListView lvBatteryInfo;
	ArrayList<String> batterylist = new ArrayList<String>();
	Builder builder;
	String batteryHealthMessage;
	String batteryChargingMessage;
	private InterstitialAd mInterstitialAd;
	AdRequest adRequest;
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "Battery Info";

	public BatteryInfoFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_device_info,
				container, false);
		mActiviy = getActivity();
		mInterstitialAd = new InterstitialAd(mActiviy);
		mInterstitialAd.setAdUnitId("ca-app-pub-6408604709878769/8066214322");
		adRequest = new AdRequest.Builder().addTestDevice("EBACE73F58A5A4D5212B26BDE8B743E1").build();
		mInterstitialAd.loadAd(adRequest);
		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				// Load the next interstitial.
				mInterstitialAd.loadAd(adRequest);
			}

		});
		builder = new Builder(mActiviy);

		lvBatteryInfo = (ListView) rootView
				.findViewById(R.id.listBatteryInfo);
		lvBatteryInfo.setOnItemClickListener(this);
		mActiviy.registerReceiver(this.batteryInfoReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		return rootView;

	}

	private void setAdapterData() {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActiviy,
				R.layout.list_item,batterylist);
		lvBatteryInfo.setAdapter(adapter);
	}
	
	

	private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			int  health= intent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);

			//			int  icon_small= intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL,0);
			int  level= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
			int  plugged= intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
			boolean  present= intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT); 
			int  scale= intent.getIntExtra(BatteryManager.EXTRA_SCALE,0);
//			int  status= intent.getIntExtra(BatteryManager.EXTRA_STATUS,0);
			String  technology= intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
			int  temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
			int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);

			switch(health){
			case Constant.BATTERY_HEALTH_COLD:
				batteryHealthMessage = "Batter is cooling";
				break;
			case Constant.BATTERY_HEALTH_DEAD:
				batteryHealthMessage = "Your Device Battery is dead and need replacement.Please replace your battery soon.";
				break;
			case Constant.BATTERY_HEALTH_GOOD:
				batteryHealthMessage = "Your device Battery Health is Good and doesn't need replacement";
				break;
			case Constant.BATTERY_HEALTH_OVER_VOLTAGE:
				batteryHealthMessage = "Battery is overvoltage.Please remove and insert your battery or restart device";
				break;
			case Constant.BATTERY_HEALTH_OVERHEAT:
				batteryHealthMessage = "Batter is overheated.Applications are slowing down your device";
				break;
			case Constant.BATTERY_HEALTH_UNKNOWN:
				batteryHealthMessage = "Battery health cannot be known";
				break;
			case Constant.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
				batteryHealthMessage = "Battery is failing.Need to replace soon";
				break;
			default:
			}

			if(plugged == Constant.BATTERY_PLUGGED_AC){
				batteryChargingMessage = "Power source is an AC charger";
			}else if(plugged == Constant.BATTERY_PLUGGED_USB){
				batteryChargingMessage="Currently the Power source is a USB port";
			}else if(plugged == Constant.BATTERY_PLUGGED_WIRELESS){
				batteryChargingMessage="Currently Power source is a wireless";
			}else if(plugged == 0){
				batteryChargingMessage="Not charging currently ";
			}



			if(!(batterylist.size() >0)){
				batterylist.add("Health : "+String.valueOf(health));
				batterylist.add("Level  : "+String.valueOf(level));
				batterylist.add("Charging Status : "+String.valueOf(plugged));
				batterylist.add("Battery present : "+String.valueOf(present));
				batterylist.add("Maximum Battery Level : "+String.valueOf(scale));
				batterylist.add("Temperature : "+String.valueOf(temperature));
				batterylist.add("Voltage : "+String.valueOf(voltage));
				batterylist.add("Type : "+technology);
			}
			setAdapterData();
		}
	};


	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		String info = batterylist.get(pos);
		switch(pos){
		case 0:
			info = batteryHealthMessage;
			break;
		case 1:
			info = info+" is your Battery percentage currently available";
			break;
		case 2:
			info = batteryChargingMessage;
			break;
		case 3:
			info = info+" describes presence of battery inside the device";
			break;
		case 4:
			info = info+"  is Your battery maximum charge limit";
			break;
		case 5:
			info = info+"  is your current battery temperature ";
			break;
		case 6:
			info = info+"  is your current battery voltage level ";
			break;
		case 7:
			info = info+"  is your current battery technology ";
			break;
		}

		if (mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		} else {
			//do nothing
		}
		showDialog("Battery Info",info);
	}

	private void showDialog(String title,String message) {
		builder
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				dialog.dismiss();
			}
		})
		.show();
	}
}
