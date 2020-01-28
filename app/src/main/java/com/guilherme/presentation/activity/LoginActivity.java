package com.guilherme.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.guilherme.presentation.HttpServiceLogin;
import com.guilherme.presentation.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView email = findViewById(R.id.activity_login_email);
        final TextView senha = findViewById(R.id.activity_login_senha);

        Button loginBtn = findViewById(R.id.activity_cadastrar_btnLogin);
        TextView linkParaCadastro = findViewById(R.id.activity_login_linkCadastrar);




        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, AbasActivity.class));

                try
                {
                    String emailString = email.getText().toString().trim();
                    String senhaString = senha.getText().toString().trim();

                    String mensagem = (new HttpServiceLogin().execute(emailString, senhaString).get());
                    mostraMensagem(mensagem);
                }
                catch (Exception e)
                {

                }

            }
        });


        linkParaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CadastrarActivity.class));
                finish();
            }
        });


        }
    public void mostraMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }
}


