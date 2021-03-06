package com.iho.asu_iho.DisplayDataFromJSON;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iho.asu_iho.Constants.FragmentFieldsMapping;
import com.iho.asu_iho.R;


public class PerScienceViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.per_science, container, false);
        TextView textView = (TextView) v.findViewById(R.id.title);
        Intent i = getActivity().getIntent();
        textView.setText(i.getStringExtra(FragmentFieldsMapping.KEY_SCIENCE_TITLE.getColumnName()));
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

}