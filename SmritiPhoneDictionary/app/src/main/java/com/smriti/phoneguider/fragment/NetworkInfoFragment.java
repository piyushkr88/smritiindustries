package com.smriti.phoneguider.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
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


public class NetworkInfoFragment extends Fragment implements OnItemClickListener {
	private Activity mActiviy;
	private ListView lvBatteryInfo;
	ArrayList<String> telehonyInfoList = new ArrayList<String>();
	TelephonyManager  telephonyInfo;
	private InterstitialAd mInterstitialAd;
	AdRequest adRequest;

	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "Location Info";

	public NetworkInfoFragment() {
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
		lvBatteryInfo = (ListView) rootView
				.findViewById(R.id.listBatteryInfo);
		lvBatteryInfo.setOnItemClickListener(this);
		//Get the instance of TelephonyManager
		telephonyInfo=(TelephonyManager)mActiviy.getSystemService(Context.TELEPHONY_SERVICE);
		displayTelephonyInfo();
		return rootView;
	}

	private void displayTelephonyInfo() {

		//Calling the methods of TelephonyManager the returns the information  
		String IMEINumber=telephonyInfo.getDeviceId();  
		String subscriberID=telephonyInfo.getSubscriberId();  
		String SIMSerialNumber=telephonyInfo.getSimSerialNumber();  
		String networkCountryISO=telephonyInfo.getNetworkCountryIso();  
		String SIMCountryISO=telephonyInfo.getSimCountryIso();  
		String softwareVersion=telephonyInfo.getDeviceSoftwareVersion();  
		String voiceMailNumber=telephonyInfo.getVoiceMailNumber();
		if(voiceMailNumber != null){
			voiceMailNumber=telephonyInfo.getVoiceMailNumber();
		}else {
			voiceMailNumber = "Not Available";
		}
		String networkOperator=telephonyInfo.getNetworkOperator();  
		String simOperator=telephonyInfo.getSimOperator(); 
		String simOperatorName=telephonyInfo.getSimOperatorName(); 
		String phoneNumber=telephonyInfo.getLine1Number();
		if(phoneNumber != null){
			phoneNumber = telephonyInfo.getLine1Number();
		}else{
			phoneNumber = "Not Registered";
		}
		String networkOperatorName = telephonyInfo.getNetworkOperatorName();
		/*	if(phoneNumber.length() == 0 && phoneNumber!= null){
			phoneNumber = "Phone number not available on SIM for your operators.";
			}*/

		//Get the phone type  
		String strphoneType="";  

		int phoneType=telephonyInfo.getPhoneType();  

		switch (phoneType)   
		{  
		case (TelephonyManager.PHONE_TYPE_CDMA):  
			strphoneType="CDMA";  
		break;  
		case (TelephonyManager.PHONE_TYPE_GSM):   
			strphoneType="GSM";                
		break;  
		case (TelephonyManager.PHONE_TYPE_NONE):  
			strphoneType="NONE";                
		break;  
		}  

		//getting information if phone is in roaming  
		boolean isRoaming=telephonyInfo.isNetworkRoaming();
		String roamingStatus;
		if(isRoaming){
			roamingStatus = "Yes";
		}else{
			roamingStatus = "No";
		}

		if(!(telehonyInfoList.size() >0)){
			telehonyInfoList.add("Phone Network Type: "+strphoneType);
			telehonyInfoList.add("Phone Number: "+phoneNumber);
			telehonyInfoList.add("IMEI No : "+IMEINumber);
			telehonyInfoList.add("Subscriber ID : "+subscriberID);
			telehonyInfoList.add("SIM Serial Number: "+SIMSerialNumber);
			telehonyInfoList.add("SIM Opearator: "+simOperator);
			telehonyInfoList.add("SIM Country ISO: "+SIMCountryISO.toUpperCase());
			telehonyInfoList.add("Network Operator Name: "+networkOperatorName);
			telehonyInfoList.add("Network Operator: "+networkOperator);
			telehonyInfoList.add("Network Country ISO: "+networkCountryISO.toUpperCase());
			telehonyInfoList.add("Software Version: "+softwareVersion);
			telehonyInfoList.add("Voice Mail Number: "+voiceMailNumber);
			telehonyInfoList.add("In Roaming: "+roamingStatus);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActiviy,
				R.layout.list_item,telehonyInfoList);
		lvBatteryInfo.setAdapter(adapter);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		String info = telehonyInfoList.get(pos);
		switch(pos){
		case 0:
			info = "It is type of phone radio whether it is GSM/CDMA/SIP ";
			break;
		case 1:
			info = "Phone numbers are not available on SIM for each operators, like in india Sim dont have phone numbers in any memory, So WE cant get phone number from these connection. However, some countries, and operators have stored phone numbers on SIM, and we can get those. ";
			break;
		case 2:
			info = "It is the unique device ID,like IMEI for GSM and MEID or ESN for CDMA phones.";
			break;
		case 3:
			info = "It is the unique subscriber ID,like IMSI for a GSM phone.";
			break;
		case 4:
			info = "It is the serial number of the SIM, if applicable.";
			break;
		case 5:
			info = "It is  the MCC+MNC (mobile country code + mobile network code) of the provider of the SIM.";
			break;
		case 6:
			info = "It is the ISO country code equivalent for the SIM provider's country code.";
			break;
		case 7:
			info = "It is name of current registered operator";
			break;
		case 8:
			info = "It is the numeric name (MCC+MNC) of current registered operator";
			break;
		case 9:
			info = "It is the ISO country code equivalent of the current registered operator's MCC (Mobile Country Code).";
			break;
		case 10:
			info = "It is the software version number for the device,like IMEI/SV for GSM phones. ";
			break;
		case 11:
			info = "It is the voice mail number.";
			break;
		case 12:
			info = "Yes, if the device is considered roaming on the current network, for GSM purposes.";
			break;
		}
		if (mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		} else {
		}
		showAlert("Network Info",info);

	}


	public void showAlert(String title, String msg) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				mActiviy);
		alertDialog.setTitle(title);
		alertDialog.setMessage(msg);
		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		alertDialog.show();
	}
}


