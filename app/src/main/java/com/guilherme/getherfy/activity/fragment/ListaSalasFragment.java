package com.guilherme.getherfy.activity.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.guilherme.presentation.R;
import com.guilherme.getherfy.activity.SalaInfoActivity;
import com.guilherme.getherfy.sala.adapter.ListaSalasAdapter;
import com.guilherme.getherfy.sala.dao.SalaDAO;
import com.guilherme.getherfy.sala.model.Sala;

import java.util.List;

public class ListaSalasFragment extends Fragment {


    public ListaSalasFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_salas, container, false);

        ListView listaDeSalas = view.findViewById(R.id.activity_salas_lista);
        List<Sala> salas = new SalaDAO().lista();
        listaDeSalas.setAdapter(new ListaSalasAdapter(salas, view.getContext()));

        listaDeSalas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), SalaInfoActivity.class );
                startActivity(intent);
            }
        });

        return view;
    }

}
