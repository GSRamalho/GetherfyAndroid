package com.guilherme.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.guilherme.presentation.R;
import com.guilherme.presentation.sala.ListaSalasAdapter;
import com.guilherme.presentation.sala.Sala;
import com.guilherme.presentation.sala.SalaDAO;

import java.util.ArrayList;
import java.util.List;

public class ListaSalasActivity extends AppCompatActivity{

    ListView listaDeSalas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_salas);


        ListView listaDeSalas = findViewById(R.id.activity_salas_lista);
        List<Sala> salas = new SalaDAO().lista();
        listaDeSalas.setAdapter(new ListaSalasAdapter(salas, this));


        listaDeSalas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListaSalasActivity.this, ReservaActivity.class );
                startActivity(intent);
            }
        });

    }
}

