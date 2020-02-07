package com.guilherme.getherfy.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.guilherme.getherfy.Permissoes;
import com.guilherme.presentation.R;

public class SalaInfoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    SharedPreferences preferences;

    private String[] permissoes = new String[]{Manifest.permission.ACCESS_FINE_LOCATION
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sala);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Permissoes.validarPermissoes(permissoes, this, 1);
        preferences = getSharedPreferences("USER_LOGIN", 0);
        final SharedPreferences.Editor editor = preferences.edit();

        TextView nomeEmpresa = findViewById(R.id.activity_info_sala_toolbar_nomeDaOrganizacao);
        nomeEmpresa.setText("Em " + preferences.getString("userNomeEmpresa", null));

        Button botaoVoltar = findViewById(R.id.activity_info_sala_botaoVoltar);

        final ImageButton dropCardBtn = findViewById(R.id.info_sala_map_down);

        final CardView cardInfo = findViewById(R.id.activity_info_sala_cardview);

        FloatingActionButton novaReserva = findViewById(R.id.activity_info_sala_novaReservaBtn);

        novaReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        LatLng wises = new LatLng(-25.452036, -49.261828);
        mMap.addMarker(new MarkerOptions().position(wises).title("Wise Systems - Fábrica de Softwares"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wises, 15));
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
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_location)));

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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10000, 10, locationListener);
            return;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int permissaoRessultado : grantResults){
            if(permissaoRessultado==PackageManager.PERMISSION_DENIED) {

            }
        }
    }
}
