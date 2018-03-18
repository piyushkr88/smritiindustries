package com.smriti.phoneguider.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.smriti.phoneguider.R;
import com.smriti.phoneguider.fragment.AppInfoFragment;
import com.smriti.phoneguider.fragment.BatteryInfoFragment;
import com.smriti.phoneguider.fragment.NetworkInfoFragment;
import com.smriti.phoneguider.fragment.OperatingSystemInfoFragment;
import com.smriti.phoneguider.util.SharedPreferenceUtil;

import java.util.Locale;


public class HomeScreenActivity extends AppCompatActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	private boolean isRatingProvided;
	Fragment fragment = null;
	private AdView adView;
	AdRequest adRequest;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent modifySettings=new Intent(HomeScreenActivity.this,SettingsActivity.class);
				startActivity(modifySettings);
			}
		});
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		adView = (AdView)findViewById(R.id.adView);
		adRequest = new AdRequest.Builder().addTestDevice("EBACE73F58A5A4D5212B26BDE8B743E1").build();
		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case 1:
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					mViewPager.setAdapter(mSectionsPagerAdapter);
					adView.loadAd(adRequest);
				}else{
					finish();
				}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (adView != null) {
			adView.resume();
		}
	}

	@Override
	public void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		//   bus.unregister(mActivity);
		super.onDestroy();
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.

			Fragment fragment = null;
			Bundle args;

			switch (position) {
			case 0:
				fragment = new BatteryInfoFragment();
				args = new Bundle();
				args.putInt(BatteryInfoFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			case 1:
				fragment = new OperatingSystemInfoFragment();
				args = new Bundle();	
				args.putInt(OperatingSystemInfoFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			case 2:
				fragment = new NetworkInfoFragment();
				args = new Bundle();
				args.putInt(NetworkInfoFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			case 3:
				fragment = new AppInfoFragment();
				args = new Bundle();
				args.putInt(AppInfoFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			}
			return fragment;

		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			}
			return null;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			showDialog("About us","Smriti Industries");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		showPlaystoreRateAlert(HomeScreenActivity.this, "Did you like our Application.Please provide your valuable feedback and rate us.", "Feedback Rating");

	}

	public void showDialog(String title, String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title).setMessage(message)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();	
			}
		});
		builder.show();
	}

	private void showPlaystoreRateAlert(Context context, String message, String title) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle(title);
		alert.setMessage(message);
		alert.setCancelable(false);
		alert.setPositiveButton("Rate Now", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
				try {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
				} catch (android.content.ActivityNotFoundException anfe) {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
				}
				SharedPreferenceUtil.setApplicationRatingData(HomeScreenActivity.this, true);
				isRatingProvided = SharedPreferenceUtil.getApplicationRatingData(HomeScreenActivity.this);
			}
		}).setNegativeButton("Later", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				moveTaskToBack(true);
			}
		});
		alert.show();
	}
}
