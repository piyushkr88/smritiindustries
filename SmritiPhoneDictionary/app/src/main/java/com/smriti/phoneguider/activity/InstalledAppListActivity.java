package com.smriti.phoneguider.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.smriti.phoneguider.R;
import com.smriti.phoneguider.adapter.InstalledAppListAdapter;
import com.smriti.phoneguider.data.AppData;

import java.util.ArrayList;
import java.util.List;

public class InstalledAppListActivity extends Activity implements OnItemClickListener {
	PackageManager packageManager;
	boolean appType;
	ListView apkList;
	List<PackageInfo> packageList1 = new ArrayList<PackageInfo>();
	List<PackageInfo> systemAppList = new ArrayList<PackageInfo>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apk_list_activity);
		appType = getIntent().getBooleanExtra("APPTYPESYS", true);
		System.out.println(appType);
		packageManager = getPackageManager();
		List<PackageInfo> packageList = packageManager
				.getInstalledPackages(PackageManager.GET_PERMISSIONS);


		/*To filter out System apps*/
		for(PackageInfo pi : packageList) {
			boolean b = isSystemPackage(pi);
			if(!b) {
				packageList1.add(pi);
			}else{
				systemAppList.add(pi);
			}
		}
		
		if(appType){
			packageList1 = systemAppList;
		}
		apkList = (ListView) findViewById(R.id.applist);
		apkList.setAdapter(new InstalledAppListAdapter(this, packageList1, packageManager));
		
		apkList.setOnItemClickListener(this);
	}

	/**
	 * Return whether the given PackgeInfo represents a system package or not.
	 * User-installed packages (Market or otherwise) should not be denoted as
	 * system packages.
	 * 
	 * @param pkgInfo
	 * @return boolean
	 */
	private boolean isSystemPackage(PackageInfo pkgInfo) {
		return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
				: false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long row) {
		PackageInfo packageInfo = (PackageInfo) parent
				.getItemAtPosition(position);
		AppData appData = (AppData)this. getApplicationContext();
		appData.setPackageInfo(packageInfo);

		Intent appInfo = new Intent(this, ApplicationInformationActivity.class);
		startActivity(appInfo);
	}


}