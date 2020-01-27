package com.guilherme.presentation.activity.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.guilherme.presentation.R;
import com.guilherme.presentation.activity.SalaInfoActivity;
import com.guilherme.presentation.reserva.adapter.ListaReservasAdapter;
import com.guilherme.presentation.reserva.model.Reserva;
import com.guilherme.presentation.reserva.dao.ReservaDAO;

import java.util.List;

public class ListaReservasFragment extends Fragment {


    public ListaReservasFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);

        TextView avisoListaVazia = view.findViewById(R.id.fragment_reservas_avisoListaVazia);
        ListView listaDeReservas = view.findViewById(R.id.activity_lista_reservas);
        List<Reserva> reservas = new ReservaDAO().lista();

        if (reservas == null){
            avisoListaVazia.setText("Você ainda não possui reservas");
        } else {
            listaDeReservas.setAdapter(new ListaReservasAdapter(reservas, view.getContext()));
            listaDeReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(view.getContext(), SalaInfoActivity.class);
                    startActivity(intent);
                }
            });
        }

        return view;
    }

}
