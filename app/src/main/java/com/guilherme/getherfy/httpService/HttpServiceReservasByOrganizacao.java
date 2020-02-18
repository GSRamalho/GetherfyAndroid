package com.guilherme.getherfy.httpService;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guilherme.getherfy.activity.fragment.ListaReservasFragment;
import com.guilherme.presentation.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpServiceReservasByOrganizacao extends AsyncTask <String, Void, String> {

    ListaReservasFragment listaReservasFragment = new ListaReservasFragment();
    @Override
    protected String doInBackground(String... strings) {
        String urlWS = "http://172.30.248.132:8080/ReservaDeSala/rest/reserva/byIdOrganizacao";

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlWS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("authorization", "secret");

            conn.setRequestProperty("id_organizacao", strings[0]);
            conn.setConnectTimeout(2000);

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null)
            {
                result.append(line);
            }
            rd.close();
            return result.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        return result.toString();

    }
}
