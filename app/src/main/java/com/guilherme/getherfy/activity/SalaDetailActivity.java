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
import com.guilherme.getherfy.sala.model.Sala;
import com.guilherme.presentation.R;

import java.text.DateFormat;
import java.util.Calendar;

public class SalaDetailActivity extends AppCompatActivity implements OnMapReadyCallback,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    DialogFragment datePicker = new DatePickerFragment();
    DialogFragment timePicker = new TimePickerFragment();
    SharedPreferences configHora;

    Dialog novaReservaPopUp;

    private GoogleMap mMap;
    SharedPreferences preferences;
    FloatingActionButton btnNovaReserva;

    private String[] permissoes = new String[]{Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_detail);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        novaReservaPopUp = new Dialog(this);
        Permissoes.validarPermissoes(permissoes, this, 1);
        preferences = getSharedPreferences("USER_LOGIN", 0);
        configHora = getSharedPreferences("IS_HORA_INICIO", 0);

        Intent intent = getIntent();
        Sala sala = (Sala) intent.getSerializableExtra("salaSelecionada");

        System.out.println("Sala selecionada: "+sala.getNome());


        TextView nomeDaSala = findViewById(R.id.info_sala_nome);
        nomeDaSala.setText(sala.getNome());

        final SharedPreferences.Editor editor = preferences.edit();
        mostraInfos();


        final TextView nomeEmpresa = findViewById(R.id.activity_info_sala_toolbar_nomeDaOrganizacao);
        nomeEmpresa.setText("Em " + preferences.getString("userNomeEmpresa", null));

        Button botaoVoltar = findViewById(R.id.activity_info_sala_botaoVoltar);

        final ImageButton dropCardBtn = findViewById(R.id.info_sala_map_down);

        final CardView cardInfo = findViewById(R.id.activity_info_sala_cardview);
        btnNovaReserva = findViewById(R.id.activity_info_sala_novaReservaBtn);


        btnNovaReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnNovaReserva.hide();

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

                } else {
                    cardInfo.setVisibility(View.VISIBLE);
                    dropCardBtn.animate().rotation(0).start();
                    cardviewBaixo = false;
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        localizacaoDoUsuario();
        Intent intent = getIntent();
        Sala sala = (Sala) intent.getSerializableExtra("salaSelecionada");

        LatLng wises = new LatLng(-25.4554681, -49.2590617);
        mMap.addMarker(new MarkerOptions().position(wises).title("Wise Systems - Fábrica de Softwares"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wises, 15));
    }

    public void mostraInfos() {
        SalaDetailInfoFragment fragment = new SalaDetailInfoFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.sala_detail_fragment, fragment).commit();

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
        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, year);
        calendario.set(Calendar.MONTH, month);
        calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String date = DateFormat.getDateInstance().format(calendario.getTime());

        TextView dia = findViewById(R.id.reservar_diaTxt);
        dia.setText(date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Boolean isHoraInicio = configHora.getBoolean("isHoraInicio", false);

        if (isHoraInicio) {
            String hora1 = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            TextView horaInicioTxt = findViewById(R.id.reservar__hora_inicioTxt);
            horaInicioTxt.setText(hora1);

        } else if (!isHoraInicio) {
            String hora2 = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            TextView horaFimTxt = findViewById(R.id.reservar_hora_finalTxt);
            horaFimTxt.setText(hora2);
        }
    }
}


