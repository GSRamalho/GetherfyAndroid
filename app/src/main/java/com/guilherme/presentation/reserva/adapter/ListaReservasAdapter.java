package com.guilherme.presentation.reserva.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guilherme.presentation.R;
import com.guilherme.presentation.reserva.model.Reserva;
import com.guilherme.presentation.sala.model.Sala;

import java.util.List;

public class ListaReservasAdapter extends BaseAdapter {
    private List<Reserva> reservas;
    private Context context;

    public ListaReservasAdapter(List<Reserva> reservas, Context context) {
        this.reservas = reservas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return reservas.size();
    }

    @Override
    public Reserva getItem(int position) {
        return reservas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return reservas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_sala_layout, parent, false);

        Reserva reserva = reservas.get(position);
        TextView nomeDaSala = viewCriada.findViewById(R.id.item_sala_cardview_nome);
        nomeDaSala.setText(reserva.getSala());

        TextView enderecoDaSala = viewCriada.findViewById(R.id.item_sala_cardview_endereco);

        enderecoDaSala.setText(reserva.getOrganizador());

        TextView capacidadeDaSala = viewCriada.findViewById(R.id.item_sala_cardview_capacidade);
        capacidadeDaSala.setText(reserva.getId()+ " assentos");

        return viewCriada;


    }
}

