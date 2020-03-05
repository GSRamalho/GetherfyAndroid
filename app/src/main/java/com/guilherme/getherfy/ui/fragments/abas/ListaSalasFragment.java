package com.guilherme.getherfy.ui.fragments.abas;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.guilherme.getherfy.ui.SalaDetailActivity;
import com.guilherme.getherfy.httpService.HttpServiceSalasByIdOrganizacao;
import com.guilherme.getherfy.adapter.ListaSalasAdapter;
import com.guilherme.getherfy.dao.SalaDAO;
import com.guilherme.getherfy.models.Sala;
import com.guilherme.presentation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListaSalasFragment extends Fragment {
    public ListaSalasFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_salas, container, false);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("USER_LOGIN", 0);
        final SharedPreferences.Editor editor = preferences.edit();

        String idOrganizacao = preferences.getString("userIdOrganizacao", null);

        final ListView listaDeSalas = view.findViewById(R.id.fragment_salas_lista);
        final List<Sala> salas = new SalaDAO().lista();
        listaDeSalas.setAdapter(new ListaSalasAdapter(salas, view.getContext()));

        try {
            String lista = new HttpServiceSalasByIdOrganizacao().execute(idOrganizacao).get();

            JSONArray listaJson = new JSONArray(lista);
            if (listaJson.length() > 0) {
                for (int i = 0; i < listaJson.length(); i++) {
                    Sala novaSala = new Sala();

                    JSONObject salaObj = listaJson.getJSONObject(i);


                    if (salaObj.has("id") && salaObj.has("nome") && salaObj.has("idOrganizacao")) {

                        int id = salaObj.getInt("id");
                        String nome = salaObj.getString("nome");
                        int capacidade = salaObj.getInt("quantidadePessoasSentadas");
                        String localizacao = salaObj.getString("localizacao");
                        double latitude = salaObj.getDouble("latitude");
                        double longitude = salaObj.getDouble("longitude");
                        int area = salaObj.getInt("areaDaSala");

                        JSONObject orgObj = salaObj.getJSONObject("idOrganizacao");
                        novaSala.setId(id);
                        novaSala.setNome(nome);
                        novaSala.setCapacidade(capacidade);
                        novaSala.setLocalizacao(localizacao);
                        novaSala.setArea(area);
                        novaSala.setLatitude(latitude);
                        novaSala.setLongitude(longitude);

                        Log.i("SalaObj", ""+novaSala.getNome()+novaSala.getId());

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
                intent.putExtra("salaSelecionada", salas.get(position));
                startActivity(intent);

            }
        });

        return view;
    }
}

