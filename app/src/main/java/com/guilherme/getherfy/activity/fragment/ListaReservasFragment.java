package com.guilherme.getherfy.activity.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.guilherme.getherfy.activity.SalaDetailActivity;
import com.guilherme.getherfy.httpService.HttpServiceReservasByOrganizacao;
import com.guilherme.getherfy.httpService.HttpServiceSalasByIdOrganizacao;
import com.guilherme.getherfy.reserva.adapter.ListaReservasAdapter;
import com.guilherme.getherfy.reserva.dao.ReservaDAO;
import com.guilherme.getherfy.reserva.model.Reserva;
import com.guilherme.getherfy.sala.adapter.ListaSalasAdapter;
import com.guilherme.getherfy.sala.dao.SalaDAO;
import com.guilherme.getherfy.sala.model.Sala;
import com.guilherme.presentation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListaReservasFragment extends Fragment {

    public ListaReservasFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);

        TextView naoPossuiReservasTxt = view.findViewById(R.id.fragment_reservas_avisoListaVazia);
        naoPossuiReservasTxt.setVisibility(View.VISIBLE);


        SharedPreferences preferences = this.getActivity().getSharedPreferences("USER_LOGIN", 0);
        final SharedPreferences.Editor editor = preferences.edit();

        String idOrganizacao = preferences.getString("userIdOrganizacao", null);


        final ListView listaDeReservas = view.findViewById(R.id.fragment_reservas_lista);

        final List<Reserva> reservas = new ReservaDAO().lista();
        listaDeReservas.setAdapter(new ListaReservasAdapter(reservas, view.getContext()));



        try {
           String lista = new HttpServiceReservasByOrganizacao().execute(idOrganizacao).get();

            System.out.println(lista);


            JSONArray listaJson = new JSONArray(lista);
            if (listaJson.length() > 0) {
                for (int i = 0; i < listaJson.length(); i++) {
                    Reserva novaReserva = new Reserva();

                    JSONObject reservaObj = listaJson.getJSONObject(i);


                    if (reservaObj.has("id") && reservaObj.has("idSala") && reservaObj.has("ativo")) {

                        int id = reservaObj.getInt("id");
                        int idSala = reservaObj.getInt("idSala");
                        int idUsuario = reservaObj.getInt("idUsuario");
                        String dataHoraInicio = reservaObj.getString("dataHoraInicio");
                        String dataHoraFim = reservaObj.getString("dataHoraFim");
                        boolean ativo = reservaObj.getBoolean("ativo");
                        String descricao = reservaObj.getString("descricao");
                        String nomeOrganizador = reservaObj.getString("nomeOrganizador");


                        novaReserva.setId(id);
                        novaReserva.setSala(idSala);
                        novaReserva.setNomeOrganizador(nomeOrganizador);
                        novaReserva.setDataHoraInicio(dataHoraInicio);
                        novaReserva.setDataHoraFim(dataHoraFim);
                        novaReserva.setOrganizador(idUsuario);
                        novaReserva.setDescricao(descricao);
                        novaReserva.setAtivo(ativo);


                        reservas.add(novaReserva);


                    }
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listaDeReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position:" +position);

/*                Intent intent = new Intent(view.getContext(), SalaDetailActivity.class);
                intent.putExtra("reservaSelecionada", reservas.get(position));
                startActivity(intent);*/

            }
        });

        return view;
    }
}
