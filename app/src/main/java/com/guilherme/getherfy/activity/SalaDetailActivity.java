package com.guilherme.getherfy.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

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
import com.guilherme.presentation.R;

import java.text.DateFormat;
import java.util.Calendar;

public class SalaDetailActivity extends AppCompatActivity implements OnMapReadyCallback,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    DialogFragment datePicker = new DatePickerFragment();
    DialogFragment timePicker = new TimePickerFragment();
    boolean isHoraInicio;
    Dialog novaReservaPopUp;

    private GoogleMap mMap;
    SharedPreferences preferences;

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
        final SharedPreferences.Editor editor = preferences.edit();
        mostraInfos();



        TextView nomeEmpresa = findViewById(R.id.activity_info_sala_toolbar_nomeDaOrganizacao);
        nomeEmpresa.setText("Em " + preferences.getString("userNomeEmpresa", null));

        Button botaoVoltar = findViewById(R.id.activity_info_sala_botaoVoltar);

        final ImageButton dropCardBtn = findViewById(R.id.info_sala_map_down);

        final CardView cardInfo = findViewById(R.id.activity_info_sala_cardview);

        final FloatingActionButton btnNovaReserva = findViewById(R.id.activity_info_sala_novaReservaBtn);
        final FloatingActionButton btnInfo = findViewById(R.id.btn_info);

        btnNovaReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               mostrarPopUpReserva(v);
                btnNovaReserva.hide();
                btnInfo.show();

                SalaDetailReservaFragment fragment = new SalaDetailReservaFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.sala_detail_fragment, fragment).commit();

            }
        });



        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostraInfos();
                btnInfo.hide();
                btnNovaReserva.show();

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        localizacaoDoUsuario();

        LatLng wises = new LatLng(-25.4554681, -49.2590617);
        mMap.addMarker(new MarkerOptions().position(wises).title("Wise Systems - Fábrica de Softwares"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wises, 15));
    }

    public void reservarFragment(View v) {

       /* ImageButton btnCancelar;
        novaReservaPopUp.setContentView(R.layout.reservar_popup);
        btnCancelar = novaReservaPopUp.findViewById(R.id.reservar_popup_cancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novaReservaPopUp.dismiss();
            }
        });*/

    /*    ImageButton btnSelecionarDia = novaReservaPopUp.findViewById(R.id.reservar_popup_data);
        btnSelecionarDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(getSupportFragmentManager(), "Date picker");


            }
        });

        ImageButton btnSelecionaHoraInicio = novaReservaPopUp.findViewById(R.id.reservar_popup_start_time);
        btnSelecionaHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.show(getSupportFragmentManager(), "Time Picker");
                isHoraInicio=true;
            }
        });
        ImageButton btnSelecionaHoraFim = novaReservaPopUp.findViewById(R.id.reservar_popup_end_time);
        btnSelecionaHoraFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.show(getSupportFragmentManager(), "Time Picker");
                isHoraInicio=false;
            }
        });*/

/*
        novaReservaPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        novaReservaPopUp.show();*/
    }
public void mostraInfos(){
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
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_location))
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

        String hora = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);

        SalaDetailReservaFragment reserva = new SalaDetailReservaFragment();


        if (reserva.isHoraInicio()) {
            TextView horaInicioTxt = findViewById(R.id.reservar__hora_inicioTxt);
            horaInicioTxt.setText(hora);
        } else if (!reserva.isHoraInicio()) {
            TextView horaFimTxt = findViewById(R.id.reservar_hora_finalTxt);
            horaFimTxt.setText(hora);
        }
    }
}

