package com.guilherme.getherfy.activity.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.guilherme.getherfy.activity.SalaDetailActivity;
import com.guilherme.getherfy.httpService.HttpServiceReservasByOrganizacao;
import com.guilherme.getherfy.httpService.HttpServiceSalasByIdOrganizacao;
import com.guilherme.getherfy.reserva.adapter.ListaReservasAdapter;
import com.guilherme.getherfy.reserva.dao.ReservaDAO;
import com.guilherme.getherfy.reserva.model.Reserva;
import com.guilherme.presentation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListaReservasFragment extends Fragment {


    public ListaReservasFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("USER_LOGIN", 0);
        final SharedPreferences.Editor editor = preferences.edit();
        final String idOrganizacao = preferences.getString("userIdOrganizacao", null);


        TextView avisoListaVazia = view.findViewById(R.id.fragment_reservas_avisoListaVazia);
        ListView listaDeReservas = view.findViewById(R.id.activity_lista_reservas);
        final List<Reserva> reservas = new ReservaDAO().lista();

        if (reservas == null){
            avisoListaVazia.setText("Você ainda não possui reservas");
        } else {
            listaDeReservas.setAdapter(new ListaReservasAdapter(reservas, view.getContext()));
            listaDeReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(view.getContext(), SalaDetailActivity.class);
                    startActivity(intent);


                    try {
                            String lista = new HttpServiceReservasByOrganizacao().execute(idOrganizacao).get();

                            System.out.println(lista);

                            JSONArray listaJson = new JSONArray(lista);
                            if (listaJson.length() > 0) {
                                for (int i = 0; i < listaJson.length(); i++) {
                                    Reserva novaReserva = new Reserva();

                                    JSONObject reservaObj = listaJson.getJSONObject(i);

                                    if (reservaObj.has("id_sala") && reservaObj.has("id_usuario")) {

                                        int idReserva = reservaObj.getInt("id");
                                        int idSala = reservaObj.getInt("id_sala");
                                        int idOrganizador = reservaObj.getInt("id_usuario");
                                        boolean ativa = reservaObj.getBoolean("ativo");
                                        long dataHoraInicio = reservaObj.getLong("localizacao");
                                        long dataHoraFim = reservaObj.getLong("latitude");
                                        String descricao = reservaObj.getString("descricao");


                                        JSONObject reserva = reservaObj.getJSONObject("id_sala");
                                        novaReserva.setId(idReserva);
                                        novaReserva.setOrganizador(idOrganizador);
                                        novaReserva.setDataHoraInicio(dataHoraInicio);
                                        novaReserva.setDataHoraFim(dataHoraFim);
                                        novaReserva.setAtiva(ativa);
                                        novaReserva.setSala(idSala);

                                        }
                                        reservas.add(novaReserva);
                                    }
                                }

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                }
            });
        }
        listaDeReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), SalaDetailActivity.class);
                intent.putExtra("salaSelecionada", reservas.get(position));
                startActivity(intent);

            }
        });
        return view;
    }

}
