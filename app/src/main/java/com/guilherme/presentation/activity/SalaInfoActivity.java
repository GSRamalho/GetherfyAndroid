package com.guilherme.presentation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.guilherme.presentation.R;
import com.guilherme.presentation.sala.dao.SalaDAO;
import com.guilherme.presentation.sala.model.Sala;

public class SalaInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

    }
}
