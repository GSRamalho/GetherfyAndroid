package com.guilherme.getherfy.ui;

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
import android.widget.Toast;

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
import com.guilherme.getherfy.ui.fragments.dateTime.DatePickerFragment;
import com.guilherme.getherfy.ui.fragments.dateTime.TimePickerFragment;
import com.guilherme.getherfy.ui.fragments.salaDetail.SalaDetailInfoFragment;
import com.guilherme.getherfy.ui.fragments.salaDetail.SalaDetailReservaFragment;
import com.guilherme.getherfy.httpService.HttpServiceReservaCadastrar;
import com.guilherme.getherfy.models.Sala;
import com.guilherme.getherfy.utils.Permissoes;
import com.guilherme.presentation.R;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SalaDetailActivity extends AppCompatActivity implements OnMapReadyCallback,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    SharedPreferences configHora;
    private boolean novaReservaVisivel = true;
    private GoogleMap mMap;
    SharedPreferences preferences;
    FloatingActionButton btnNovaReserva;
    FloatingActionButton btnSalvarReserva;

    long dateTimeInicioMilliseconds;
    long dateTimeFimMilliseconds;

    private String[] permissoes = new String[]{Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala_detail);

        Sala sala = salaAtual();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Permissoes.validarPermissoes(permissoes, this, 1);
        preferences = getSharedPreferences("USER_LOGIN", 0);
        configHora = getSharedPreferences("IS_HORA_INICIO", 0);

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

                try {

                    Button btnHoraInicio = findViewById(R.id.btn_selecionar_hora_inicio);
                    Button btnHoraFim = findViewById(R.id.btn_selecionar_hora_final);
                    TextView descricao = findViewById(R.id.reservar_campo_assunto);

                    Sala sala = salaAtual();
                    String idOrganizador = preferences.getString("userId", null);

                    formataHoras(btnHoraInicio, btnHoraFim);


                    reservaJson.put("descricao", descricao.getText());
                    reservaJson.put("id_sala", sala.getId());
                    reservaJson.put("id_usuario", idOrganizador);
                    reservaJson.put("data_hora_inicio", dateTimeInicioMilliseconds);
                    reservaJson.put("data_hora_fim", dateTimeFimMilliseconds);
                    reservaJson.put("ativo", true);


                    System.out.println(reservaJson.toString());
                    String novaReservaEncoded = Base64.encodeToString(reservaJson.toString().getBytes("UTF-8"), Base64.NO_WRAP);
                    System.out.println(novaReservaEncoded);
                    String mensagem = (new HttpServiceReservaCadastrar().execute(novaReservaEncoded).get());

                    System.out.println(mensagem);
                    mostraMensagem(mensagem);

                    if(mensagem.equalsIgnoreCase("Reserva realizada com sucesso")){
                        startActivity(new Intent(SalaDetailActivity.this, AbasActivity.class));
                        finish();
                    }

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

    private Sala salaAtual() {
        Intent intent = getIntent();
        return (Sala) intent.getSerializableExtra("salaSelecionada");
    }

    private void mostraMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }
    public void mostraBtnNovaReserva() {
        btnNovaReserva.show();
        novaReservaVisivel = true;

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        localizacaoDoUsuario();
        Sala sala = salaAtual();

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
        Button btnDia = findViewById(R.id.btn_selecionar_dia);

        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, year);
        calendario.set(Calendar.MONTH, month);
        calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String date = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendario.getTime());

        btnDia.setText(String.format(date));


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Button btnHoraInicio = findViewById(R.id.btn_selecionar_hora_inicio);
        Button btnHoraFim = findViewById(R.id.btn_selecionar_hora_final);
        boolean isHoraInicioSelecionada = SalaDetailReservaFragment.isHoraInicioSelecionada;
        String horaFormatada;
        String minutoFormatado;

        horaFormatada = String.valueOf(hourOfDay);
        minutoFormatado = String.valueOf(minute);

        if(hourOfDay<10){
            horaFormatada = "0"+hourOfDay;
        }
        if(minute<10){
            minutoFormatado = "0"+minute;
        }

        if (isHoraInicioSelecionada) {
            String hora1 = horaFormatada + ":" + minutoFormatado;
            btnHoraInicio.setText(hora1);
        } else if (!isHoraInicioSelecionada) {
            String hora2 = horaFormatada + ":" + minutoFormatado;
            btnHoraFim.setText(hora2);
        }


    }

    private void formataHoras(Button btnHoraInicio, Button btnHoraFim) {
        SimpleDateFormat dateTimeFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Button btnSelecionarDia = findViewById(R.id.btn_selecionar_dia);

        String dataStr = btnSelecionarDia.getText().toString();
        String horaInicioStr = btnHoraInicio.getText().toString();
        String horaFimStr = btnHoraFim.getText().toString();
        String dateTimeInicio = dataStr + " "+horaInicioStr+":00".trim();
        String dateTimeFim = dataStr + " "+horaFimStr+":00".trim();


        System.out.println(dateTimeInicio);
        System.out.println(dateTimeFim);
        try {

            Date dateTimeInicioParseado = dateTimeFormat.parse(dateTimeInicio);
            Date dateTimeFimParseado = dateTimeFormat.parse(dateTimeFim);
            dateTimeInicioMilliseconds=dateTimeInicioParseado.getTime();
            dateTimeFimMilliseconds = dateTimeFimParseado.getTime();


            System.out.println(dateTimeInicioMilliseconds);
            System.out.println(dateTimeFimMilliseconds);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}


