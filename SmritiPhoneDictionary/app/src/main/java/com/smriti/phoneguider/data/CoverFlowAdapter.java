package com.smriti.phoneguider.data;

/**
 * Created by mustafid on 2/1/2017.
 */

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smriti.phoneguider.R;

import java.util.ArrayList;

public class CoverFlowAdapter extends BaseAdapter {

    private ArrayList<Book> data;
    private AppCompatActivity activity;

    public CoverFlowAdapter(AppCompatActivity context, ArrayList<Book> objects) {
        this.activity = context;
        this.data = objects;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Book getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_flow_view, null, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bookImage.setImageResource(data.get(position).getImageSource());
        viewHolder.bookName.setText(data.get(position).getName());

        return convertView;
    }


    private static class ViewHolder {
        private TextView bookName;
        private ImageView bookImage;

        public ViewHolder(View v) {
            bookImage = (ImageView) v.findViewById(R.id.image);
            bookName = (TextView) v.findViewById(R.id.name);
        }
    }
}