package com.smriti.phoneguider.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.smriti.phoneguider.R;
import com.smriti.phoneguider.data.Book;
import com.smriti.phoneguider.data.CoverFlowAdapter;

import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;


public class CarouselActivity extends AppCompatActivity {

    private FeatureCoverFlow coverFlow;
    private CoverFlowAdapter adapter;
    private ArrayList<Book> books;
    private AppCompatButton launchApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_layout);
        coverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        launchApp = (AppCompatButton)findViewById(R.id.btn_launchApp);
        launchApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent splashIntent = new Intent(CarouselActivity.this,HomeScreenActivity.class);
                startActivity(splashIntent);
            }
        });
        settingDummyData();
        adapter = new CoverFlowAdapter(this, books);
        coverFlow.setAdapter(adapter);
        coverFlow.setOnScrollPositionListener(onScrollListener());
       /* Intent i = new Intent();
        i.setAction("com.smriti.phoneguider.NK_CUSTOM_ACTION");
        i.putExtra("movie_id", "123456");
        Log.e("IntentTest", i.toUri(Intent.URI_INTENT_SCHEME));*/
    }

    private FeatureCoverFlow.OnScrollPositionListener onScrollListener() {
        return new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
            }

            @Override
            public void onScrolling() {
            }
        };
    }

    private void settingDummyData() {
        books = new ArrayList<>();
        books.add(new Book(R.mipmap.battery_screen, "Device Battery Info"));
        books.add(new Book(R.mipmap.os_screen, "Device OS Info"));
        books.add(new Book(R.mipmap.network_screen, "Phone Network Info"));
        books.add(new Book(R.mipmap.others_info_screen, "App Installed Info"));
        books.add(new Book(R.mipmap.app_installed_screen, "App Info"));
        books.add(new Book(R.mipmap.package_info, "Full installation Info"));
    }
}
