package com.guilherme.getherfy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.guilherme.getherfy.activity.fragment.ListaReservasFragment;
import com.guilherme.getherfy.activity.fragment.ListaSalasFragment;
import com.guilherme.presentation.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
public class AbasActivity extends AppCompatActivity {

    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abas);
        preferences = getSharedPreferences("USER_LOGIN", 0);
        final SharedPreferences.Editor editor = preferences.edit();

        TextView nomeEmpresa = findViewById(R.id.activity_lista_salas_toolbar_nomeDaOrganizacao);
        nomeEmpresa.setText("Em " + preferences.getString("userNomeEmpresa", null));

        smartTabLayout = findViewById(R.id.viewPagerTab);
        viewPager = findViewById(R.id.viewPager);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Reservas", ListaReservasFragment.class)
                .add("Salas", ListaSalasFragment.class)
                .create());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        SmartTabLayout viewPagerTab = findViewById(R.id.viewPagerTab);
        viewPagerTab.setViewPager(viewPager);

        ImageButton botaoPerfil = findViewById(R.id.activity_abas_perfilBtn);
        botaoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                startActivity(new Intent(AbasActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}

