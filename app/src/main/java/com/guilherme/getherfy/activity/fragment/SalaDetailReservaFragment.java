package com.guilherme.getherfy.activity.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.guilherme.getherfy.DatePickerFragment;
import com.guilherme.getherfy.TimePickerFragment;
import com.guilherme.presentation.R;

import java.text.DateFormat;
import java.util.Calendar;

public class SalaDetailReservaFragment extends Fragment {
    DialogFragment datePicker = new DatePickerFragment();
    DialogFragment timePicker = new TimePickerFragment();

    public boolean isHoraInicio() {
        return isHoraInicio;
    }

    public void setHoraInicio(boolean horaInicio) {
        isHoraInicio = horaInicio;
    }

    private boolean isHoraInicio;

    public SalaDetailReservaFragment() {


    }


    // TODO: Rename and change types and number of parameters
    public static SalaDetailReservaFragment newInstance() {
        SalaDetailReservaFragment fragment = new SalaDetailReservaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sala_detail_reserva, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ImageButton btnSelecionarDia = getView().findViewById(R.id.btn_selecionar_dia);
        btnSelecionarDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(getFragmentManager(), "Time Picker");


            }
        });

        ImageButton btnSelecionaHoraInicio = getView().findViewById(R.id.btn_hora_inicio);
        btnSelecionaHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.show(getFragmentManager(), "Time Picker");
                setHoraInicio(true);
            }
        });
        ImageButton btnSelecionaHoraFim = getView().findViewById(R.id.btn_hora_final);
        btnSelecionaHoraFim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timePicker.show(getFragmentManager(), "Time Picker");
                setHoraInicio(false);
            }
        });

    }

}
