package com.guilherme.getherfy;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {
   private TextView horaTxt;
  @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar horario = Calendar.getInstance();
        int hora = horario.get(Calendar.HOUR_OF_DAY);
        int minuto = horario.get(Calendar.MINUTE);


        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener)
                getActivity(), hora, minuto, true);



    }

}
