package com.guilherme.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.guilherme.presentation.R;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        Button cadastrarBtn = findViewById(R.id.activity_cadastrar_btnCadastrar);

        cadastrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText campoEmail = findViewById(R.id.activity_cadastrar_email);
                String emailStr = campoEmail.getText().toString();
                System.out.println(emailStr);
                chamaActivityLogin();
            }
        });


        TextView linkParaLogin = findViewById(R.id.activity_cadastrar_linkLogin);

        linkParaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamaActivityLogin();
            }
        });

    }

    private void chamaActivityLogin() {
        startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
        finish();
    }
}
