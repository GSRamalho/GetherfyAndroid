package com.guilherme.getherfy.activity.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.guilherme.getherfy.httpService.HttpServiceSalas;
import com.guilherme.getherfy.sala.dao.SalaDAO;
import com.guilherme.presentation.R;
import com.guilherme.getherfy.activity.SalaDetailActivity;
import com.guilherme.getherfy.sala.adapter.ListaSalasAdapter;
import com.guilherme.getherfy.sala.model.Sala;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListaSalasFragment extends Fragment {
    SharedPreferences preferences;
    Sala novaSala = new Sala();
    List<Sala> listaSalas = new ArrayList<>();

    public ListaSalasFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_salas, container, false);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("USER_LOGIN", 0);
        final SharedPreferences.Editor editor = preferences.edit();

        String idOrganizacao = preferences.getString("userIdOrganizacao", null);

        ListView listaDeSalas = view.findViewById(R.id.activity_salas_lista);
        List<Sala> salas = new SalaDAO().lista();
        listaDeSalas.setAdapter(new ListaSalasAdapter(salas, view.getContext()));

        try {
            String lista = new HttpServiceSalas().execute(idOrganizacao).get();

            System.out.println("Salas: " + lista);

            JSONArray listaJson = new JSONArray(lista);
            if (listaJson.length() > 0) {
                System.out.println("Qnt salas: " + listaJson.length());
                for (int i = 0; i < listaJson.length(); i++) {
                    System.out.println(listaJson);

                    JSONObject salaObj = listaJson.getJSONObject(i);
                    if (salaObj.has("id") && salaObj.has("nome") && salaObj.has("idOrganizacao")) {

                        int id = salaObj.getInt("id");
                        String nome = salaObj.getString("nome");
                        int capacidade = salaObj.getInt("quantidadePessoasSentadas");
                        String localizacao = salaObj.getString("localizacao");

                        JSONObject orgObj = salaObj.getJSONObject("idOrganizacao");
                        novaSala.setId(id);
                        novaSala.setNome(nome);
                        novaSala.setCapacidade(capacidade);
                        novaSala.setLocalizacao(localizacao);

                        if (orgObj.has("id")) {
                            int idOrg = orgObj.getInt("id");
                            novaSala.setIdOrganizacao(idOrg);

                        }
                        salas.add(novaSala);

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
        listaDeSalas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), SalaDetailActivity.class);
                startActivity(intent);


            }
        });

        return view;
    }
}

