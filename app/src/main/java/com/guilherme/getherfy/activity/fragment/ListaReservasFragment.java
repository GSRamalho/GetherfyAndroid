package com.guilherme.getherfy.activity.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.guilherme.getherfy.activity.AbasActivity;
import com.guilherme.getherfy.httpService.HttpServiceReservasByOrganizacao;
import com.guilherme.getherfy.reserva.adapter.ListaReservasAdapter;
import com.guilherme.getherfy.reserva.dao.ReservaDAO;
import com.guilherme.getherfy.reserva.model.Reserva;
import com.guilherme.presentation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListaReservasFragment extends Fragment implements AtualizaLista {

    private String listaStr;
    private int selecionado;
    ListaReservasAdapter listaReservasAdapter = new ListaReservasAdapter();

    String idOrganizacao ;


    public ListaReservasFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);

        idOrganizacao = ((AbasActivity)getActivity()).getIdOrganizacao();

        TextView naoPossuiReservasTxt = view.findViewById(R.id.fragment_reservas_avisoListaVazia);
        naoPossuiReservasTxt.setVisibility(View.VISIBLE);

        ImageButton btnRemover = view.findViewById(R.id.fragment_item_reserva_remover);
        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        try {
            listaStr = new HttpServiceReservasByOrganizacao().execute(idOrganizacao).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        final ListView listaDeReservas = carregaLista(view, idOrganizacao);


        return view;
    }



    public ListView carregaLista(View view, String idOrganizacao) {
        ListView listaDeReservas = view.findViewById(R.id.fragment_reservas_lista);
        List<Reserva> reservas = new ReservaDAO().lista();
        ListaReservasAdapter adapter = new ListaReservasAdapter(reservas, getContext());
        adapter.atualizaLista=this;
        listaDeReservas.setAdapter(adapter);


        try {

            JSONArray listaJson = new JSONArray(listaStr);
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

        } catch (Exception e){
            e.printStackTrace();
        }
        return listaDeReservas;
    }




    @Override
    public void atualizarLista(boolean id) {

        if(id) {
            Log.e("SalasFragment", "refresh");
            carregaLista(getView(), idOrganizacao);
        }
    }
}
