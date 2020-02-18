package com.guilherme.getherfy.reserva.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.guilherme.getherfy.reserva.model.Reserva;
import com.guilherme.presentation.R;

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
                    .inflate(R.layout.item_reserva_layout, parent, false);
            Reserva reserva = reservas.get(position);

            
            return viewCriada;
        }
    }


