package com.iho.asu_iho.Utilities;

/**
 * Created by Barathi on 10/18/2014.
 * Modified by Mihir Bhatt on 03/20/2017
 * Modified by Rachana Kashyap pn 11/14/2018
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.iho.asu_iho.R;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String>{
    private final Activity context;
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> imageId = new ArrayList<String>();
    Bitmap bMap;
    public CustomList(Activity context,
                      ArrayList<String> title, ArrayList<String> imageId) {
        super(context, R.layout.list_single, title);
        this.context = context;
        this.title = title;
        this.imageId = imageId;
        if (bMap != null) {
            bMap.recycle();
        }
        bMap = null;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(title.get(position));
        String imageTile = imageId.get(position);
        if (imageView != null) {
            imageView.setImageBitmap(null);
        }
        Glide.with(context).load((imageTile)).into(imageView);
        return rowView;
    }
}
