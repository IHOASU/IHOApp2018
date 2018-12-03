package com.iho.asu_iho.DisplayDataFromJSON;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iho.asu_iho.Constants.FragmentFieldsMapping;
import com.iho.asu_iho.R;


public class
PerLecturerViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.per_lecturer, container, false);
        TextView textView = (TextView) v.findViewById(R.id.name);
        ImageView imageView = (ImageView) v.findViewById(R.id.image);
        TextView textView1 = (TextView)v.findViewById(R.id.title);
        TextView textView2 = (TextView)v.findViewById(R.id.bio);
        Intent i = getActivity().getIntent();
        Glide.with(this).load(i.getStringExtra(FragmentFieldsMapping.KEY_LECTURER_IMAGE_URL.getColumnName())).into(imageView);
        textView.setText(i.getStringExtra(FragmentFieldsMapping.KEY_LECTURER_NAME.getColumnName()));
        textView1.setText(i.getStringExtra(FragmentFieldsMapping.KEY_LECTURE_TITLE.getColumnName()));
        textView1.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView2.setText(i.getStringExtra(FragmentFieldsMapping.KEY_LECTURER_BIO.getColumnName()));
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
    }
}