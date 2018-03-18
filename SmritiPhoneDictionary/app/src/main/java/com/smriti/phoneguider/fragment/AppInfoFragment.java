package com.smriti.phoneguider.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.smriti.phoneguider.R;
import com.smriti.phoneguider.activity.InstalledAppListActivity;

import java.util.ArrayList;
import java.util.List;


public class AppInfoFragment extends Fragment {
	TextView totalMemory;
	TextView availMemory;
	Activity mActivity;
	Button buttonLaunchAppInstalled;
	Button sysAppInstalled;
	PackageManager packageManager;

	List<PackageInfo> packageList1;
	List<PackageInfo> systemAppList;
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "Memory Info";

	public AppInfoFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_others_info,
				container, false);
		mActivity = getActivity();
		buttonLaunchAppInstalled = (Button) rootView.findViewById(R.id.installedApplications);
		sysAppInstalled = (Button) rootView.findViewById(R.id.systemApplications);
		packageManager = mActivity.getPackageManager();
		List<PackageInfo> packageList = packageManager
				.getInstalledPackages(PackageManager.GET_PERMISSIONS);

		packageList1  = new ArrayList<PackageInfo>();
		systemAppList = new ArrayList<PackageInfo>();
		/*To filter out System apps*/
		for(PackageInfo pi : packageList) {

			boolean b = isSystemPackage(pi);
			if(!b) {
				packageList1.add(pi);
			}else{
				systemAppList.add(pi);
			}
		}
		
		int installAppSize = packageList1.size();
		int systemAppSize = systemAppList.size();
		buttonLaunchAppInstalled.setText("User installed Applications("+String.valueOf(installAppSize)+ ")");
		sysAppInstalled.setText("System installed Applications("+String.valueOf(systemAppSize)+ ")");
		
		buttonLaunchAppInstalled.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mActivity,InstalledAppListActivity.class);
				intent.putExtra("APPTYPESYS", false);
				startActivity(intent);

			}
		});

		sysAppInstalled.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mActivity,InstalledAppListActivity.class);
				intent.putExtra("APPTYPESYS", true);
				startActivity(intent);

			}
		});

		return rootView;
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

}
