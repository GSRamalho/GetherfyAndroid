package com.guilherme.getherfy.httpService;

import android.os.AsyncTask;

import com.google.android.gms.common.util.Strings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpServiceReservaCadastrar extends AsyncTask <String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        String urlWS = "http://172.30.248.132:8080/ReservaDeSala/rest/reserva/cadastrar";

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlWS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("authorization", "secret");

            conn.setRequestProperty("novaReserva", strings[0]);
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
