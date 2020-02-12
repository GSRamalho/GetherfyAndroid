package com.guilherme.getherfy.activity.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guilherme.presentation.R;


public class SalaDetailInfoFragment extends Fragment {

    public SalaDetailInfoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SalaDetailInfoFragment newInstance() {
        SalaDetailInfoFragment fragment = new SalaDetailInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sala_detail_info, container, false);
    }

}
