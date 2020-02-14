package com.guilherme.getherfy.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.guilherme.getherfy.sala.model.Sala;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = getActivity().getIntent();
        Sala sala = (Sala) intent.getSerializableExtra("salaSelecionada");
        TextView localTxt = getView().findViewById(R.id.info_sala_localTxt);
        localTxt.setText(sala.getLocalizacao());

        TextView capacidadeTxt = getView().findViewById(R.id.info_sala_capacidadeTxt);
        capacidadeTxt.setText(Integer.toString(sala.getCapacidade()));

        TextView areaTxt = getView().findViewById(R.id.info_sala_areaTxt);
        areaTxt.setText(Double.toString(sala.getArea()));


    }
}
