package com.guilherme.getherfy.sala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.guilherme.getherfy.activity.fragment.ListaSalasFragment;
import com.guilherme.presentation.R;
import com.guilherme.getherfy.sala.model.Sala;

import java.util.List;

public class ListaSalasAdapter extends BaseAdapter {
    private List<Sala> salas;
    private Context context;

    public ListaSalasAdapter(List<Sala> salas, Context context) {
        this.salas = salas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return salas.size();
    }

    @Override
    public Sala getItem(int position) {
        return salas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return salas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         View viewCriada = LayoutInflater.from(context)
                    .inflate(R.layout.item_sala_layout, parent, false);

            Sala sala = salas.get(position);
            TextView nomeDaSala = viewCriada.findViewById(R.id.item_sala_cardview_nome);
            nomeDaSala.setText(sala.getNome());

            TextView enderecoDaSala = viewCriada.findViewById(R.id.item_sala_cardview_endereco);

            enderecoDaSala.setText(sala.getLocalizacao());

            TextView capacidadeDaSala = viewCriada.findViewById(R.id.item_sala_cardview_capacidade);
            capacidadeDaSala.setText(sala.getCapacidade() + " assentos");

            return viewCriada;
        }
}

