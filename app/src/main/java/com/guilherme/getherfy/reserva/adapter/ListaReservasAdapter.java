package com.guilherme.getherfy.reserva.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.guilherme.getherfy.reserva.model.Reserva;
import com.guilherme.presentation.R;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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


        TextView descricao = viewCriada.findViewById(R.id.fragmente_item_reserva_assunto);
        descricao.setText(reserva.getDescricao());


        TextView organizador = viewCriada.findViewById(R.id.fragment_item_reserva_organizador);
        organizador.setText(reserva.getNomeOrganizador());

/*
        TextView localizacao = viewCriada.findViewById(R.id.fragment_item_reserva_localizacao);
        localizacao.setText(reserva.getSala());*/



        SimpleDateFormat dateFormat =new SimpleDateFormat("dd/MM/yyyy");

        TextView data = viewCriada.findViewById(R.id.fragment_item_reserva_dia);
        data.setText(reserva.getDataHoraInicio());


        final ImageButton btnRemover = viewCriada.findViewById(R.id.fragment_item_reserva_remover);

        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRemover.setVisibility(View.VISIBLE);

            }
        });

        return viewCriada;
        }
    }


