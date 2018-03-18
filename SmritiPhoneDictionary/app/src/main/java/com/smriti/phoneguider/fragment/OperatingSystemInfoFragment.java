package com.smriti.phoneguider.fragment;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
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

import java.util.ArrayList;


public class OperatingSystemInfoFragment extends Fragment implements OnItemClickListener {
	private Activity mActiviy;
	private ListView lvBatteryInfo;
	ArrayList<String> OSInfoList = new ArrayList<String>();
	Builder builder;
	private InterstitialAd mInterstitialAd;
	AdRequest adRequest;
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "OS Info";

	public OperatingSystemInfoFragment() {
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
		
		displayOSInfo();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActiviy,
				R.layout.list_item,OSInfoList);
		lvBatteryInfo.setAdapter(adapter);

		return rootView;
	}

	private void displayOSInfo() {
		int ver = Build.VERSION.SDK_INT;
		String verName = Build.VERSION.CODENAME;
		String vercode = Build.VERSION.RELEASE;

		String manufaturer = Build.MANUFACTURER;
		String hardware = Build.HARDWARE;
		String device = Build.DEVICE;
		String board = Build.BOARD;

		String bootLoaderVer = Build.BOOTLOADER;
		String hardwareBrand = Build.BRAND;
		String cpu = Build.CPU_ABI;
		String cpu2 = Build.CPU_ABI2;


		String id = Build.ID;
		String model = Build.MODEL;

		String productName = Build.PRODUCT;
		String hardwareSerialNumber = Build.SERIAL;

		DisplayMetrics dm = new DisplayMetrics();
		mActiviy.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width=dm.widthPixels;
		int height=dm.heightPixels;
		int dens=dm.densityDpi;
		double wi=(double)width/(double)dens;
		double hi=(double)height/(double)dens;
		double x = Math.pow(wi,2);
		double y = Math.pow(hi,2);
		double screenInches = Math.sqrt(x+y);
		
		if(!(OSInfoList.size() >0)){
			OSInfoList.add("Resolution : "+String.valueOf(screenInches));
			OSInfoList.add("API : "+String.valueOf(ver));
			OSInfoList.add("Version : "+verName);
			OSInfoList.add("Android Version: "+vercode);
			OSInfoList.add("Hardware : "+hardware);
			OSInfoList.add("Hardware Mannufacturer: "+manufaturer);
			OSInfoList.add("Hardware Brand: "+hardwareBrand);
			OSInfoList.add("Hardware Serial Number: "+hardwareSerialNumber);
			OSInfoList.add("Product name: "+productName);
			OSInfoList.add("Industrial Design: "+device);
			OSInfoList.add("Board: "+board);
			OSInfoList.add("System bootloader version: "+bootLoaderVer);
			OSInfoList.add("Instruction Set: "+cpu);
			OSInfoList.add("Second Instruction Set: "+cpu2);
			OSInfoList.add("Label: "+id);
			OSInfoList.add("End User: "+model);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		String info = OSInfoList.get(pos);
		switch(pos){
		
		case 0:
			info = "This is your device scren resolution in inches";
			break;
		case 1:
			info = "It is user-visible SDK version of the framework";
			break;
		case 2:
			info = "It is current development codename, REL if this is a release build";
			break;
		case 3:
			info = "The user-visible version string";
			break;
		case 4:
			info = "It is name of the hardware (from the kernel command line or /proc)";
			break;
		case 5:
			info = "It is manufacturer of the product/hardware.";
			break;
		case 6:
			info = "It is the consumer-visible brand with which the product/hardware will be associated, if any";
			break;
		case 7:
			info = "A hardware serial number, if available";
			break;
		case 8:
			info = "The name of the overall product";
			break;
		case 9:
			info = "It is name of the industrial design";
			break;
		case 10:
			info = "It is the name of underlying board like ,goldfish";
			break;
		case 11:
			info = "It is the system bootloader version number.";
			break;
		case 12:
			info = "ABIs supported by this device";
			break;
		case 13:
			info = "ABIs supported by this device";
			break;
		case 14:
			info = "It is either a changelist number, or a label like , M4-rc20";
			break;
		case 15:
			info = "It is end-user-visible name for the end product.";
			break;
		}
		if (mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		} else {
			//do nothing
		}
		showDialog("Device OS Info",info);


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
