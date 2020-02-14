package com.guilherme.getherfy.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.guilherme.getherfy.DatePickerFragment;
import com.guilherme.getherfy.Permissoes;
import com.guilherme.getherfy.TimePickerFragment;
import com.guilherme.getherfy.activity.fragment.SalaDetailInfoFragment;
import com.guilherme.getherfy.activity.fragment.SalaDetailReservaFragment;
import com.guilherme.getherfy.reserva.model.Reserva;
import com.guilherme.getherfy.sala.model.Sala;
import com.guilherme.presentation.R;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;

public class SalaDetailActivity extends AppCompatActivity implements OnMapReadyCallback,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    DialogFragment datePicker = new DatePickerFragment();
    DialogFragment timePicker = new TimePickerFragment();
    SharedPreferences configHora;

    Dialog novaReservaPopUp;
    private boolean novaReservaVisivel = true;
    private GoogleMap mMap;
    SharedPreferences preferences;
    FloatingActionButton btnNovaReserva;
    FloatingActionButton btnSalvarReserva;





    private String[] permissoes = new String[]{Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView dia = findViewById(R.id.reservar_diaTxt);
        TextView horaInicioTxt = findViewById(R.id.reservar__hora_inicioTxt);
        TextView horaFimTxt = findViewById(R.id.reservar_hora_finalTxt);
        TextView descricao = findViewById(R.id.reservar_campo_assunto);

        setContentView(R.layout.activity_sala_detail);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Permissoes.validarPermissoes(permissoes, this, 1);
        preferences = getSharedPreferences("USER_LOGIN", 0);
        configHora = getSharedPreferences("IS_HORA_INICIO", 0);

        Intent intent = getIntent();
        Sala sala = (Sala) intent.getSerializableExtra("salaSelecionada");

        final SharedPreferences.Editor editor = preferences.edit();
        mostraInfos();


        final TextView nomeEmpresa = findViewById(R.id.activity_info_sala_toolbar_nomeDaOrganizacao);
        nomeEmpresa.setText("" + sala.getNome());

        Button botaoVoltar = findViewById(R.id.activity_info_sala_botaoVoltar);

        final ImageButton dropCardBtn = findViewById(R.id.info_sala_map_down);

        final CardView cardInfo = findViewById(R.id.activity_info_sala_cardview);
        btnNovaReserva = findViewById(R.id.activity_info_sala_novaReservaBtn);
        btnSalvarReserva = findViewById(R.id.activity_sala_detail_salvar_reserva);

        btnNovaReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                novaReservaVisivel = false;
                btnNovaReserva.hide();
                btnSalvarReserva.show();
                SalaDetailReservaFragment fragment = new SalaDetailReservaFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.sala_detail_fragment, fragment).commit();


            }
        });

        dropCardBtn.setOnClickListener(new View.OnClickListener() {
            boolean cardviewBaixo = false;

            @Override
            public void onClick(View v) {

                if (!cardviewBaixo) {
                    cardInfo.setVisibility(View.GONE);
                    dropCardBtn.animate().rotation(180).start();
                    cardviewBaixo = true;
                    btnNovaReserva.hide();
                    btnSalvarReserva.hide();


                } else {
                    cardInfo.setVisibility(View.VISIBLE);
                    dropCardBtn.animate().rotation(0).start();
                    cardviewBaixo = false;

                    if (novaReservaVisivel == false) {
                        btnSalvarReserva.show();

                    } else {
                        mostraInfos();
                    }
                }
            }
        });




        //Implementar
        btnSalvarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject reservaJson = new JSONObject();
                String organizador = preferences.getString("userName", null);

                try {

                    TextView dia = findViewById(R.id.reservar_diaTxt);
                    TextView horaInicioTxt = findViewById(R.id.reservar__hora_inicioTxt);
                    TextView horaFimTxt = findViewById(R.id.reservar_hora_finalTxt);
                    TextView descricao = findViewById(R.id.reservar_campo_assunto);
                    Intent intent = getIntent();
                    Sala sala = (Sala) intent.getSerializableExtra("salaSelecionada");

                    reservaJson.put("descricao", descricao.getText());
                    reservaJson.put("id_sala", sala.getId());
                    reservaJson.put("data", dia.getText());
                    reservaJson.put("data_hora_inicio", horaInicioTxt.getText());
                    reservaJson.put("data_hora_fim",horaFimTxt.getText());


                    System.out.println(reservaJson.toString());
                    String novaReservaEncoded = Base64.encodeToString(reservaJson.toString().getBytes("UTF-8"), Base64.NO_WRAP);

                    System.out.println(novaReservaEncoded);

                }catch (Exception e){

                }
            }
        });

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void mostraBtnNovaReserva() {
        btnNovaReserva.show();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        localizacaoDoUsuario();
        Intent intent = getIntent();
        Sala sala = (Sala) intent.getSerializableExtra("salaSelecionada");

        LatLng wises = new LatLng(sala.getLatitude(), sala.getLongitude());
        mMap.addMarker(new MarkerOptions().position(wises).title("Wise Systems - Fábrica de Softwares"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wises, 15));


    }

    public void mostraInfos() {
        SalaDetailInfoFragment fragment = new SalaDetailInfoFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.sala_detail_fragment, fragment).commit();
        novaReservaVisivel = true;
        btnNovaReserva = findViewById(R.id.activity_info_sala_novaReservaBtn);
        btnNovaReserva.show();
    }

    public void localizacaoDoUsuario() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng localUsuario = new LatLng(latitude, longitude);

                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(localUsuario).title("Você está aqui")
                );

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localUsuario, 15));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

        };

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10000, 10, locationListener);
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoRessultado : grantResults) {
            if (permissaoRessultado == PackageManager.PERMISSION_DENIED) {

            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView dia = findViewById(R.id.reservar_diaTxt);

        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, year);
        calendario.set(Calendar.MONTH, month);
        calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String date = DateFormat.getDateInstance().format(calendario.getTime());

        dia.setText(date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView horaInicioTxt = findViewById(R.id.reservar__hora_inicioTxt);
        TextView horaFimTxt = findViewById(R.id.reservar_hora_finalTxt);
        Boolean isHoraInicio = configHora.getBoolean("isHoraInicio", false);

        if (isHoraInicio) {
            String hora1 = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            horaInicioTxt.setText(hora1);

        } else if (!isHoraInicio) {
            String hora2 = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            horaFimTxt.setText(hora2);
        }
    }
}


