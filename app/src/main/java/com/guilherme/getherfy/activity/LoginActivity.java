package com.guilherme.getherfy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.guilherme.getherfy.httpService.HttpServiceLogin;
import com.guilherme.presentation.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageButton voltarBtn = findViewById(R.id.activity_login_voltarBtn);
        final TextView email = findViewById(R.id.activity_login_email);
        final TextView senha = findViewById(R.id.activity_cadastrar_senha);

        Button loginBtn = findViewById(R.id.activity_cadastrar_btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String emailString = email.getText().toString().trim();
                    String senhaString = senha.getText().toString().trim();

                    String mensagem = (new HttpServiceLogin().execute(emailString, senhaString).get());
                    mostraMensagem(mensagem);

                    if(mensagem.equalsIgnoreCase("Login efetuado com sucesso!")){
                        startActivity(new Intent(LoginActivity.this, AbasActivity.class));

                    }


                }
                catch (Exception e)
                {

                }

            }
        });

        voltarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PrimeiraTelaActivity.class));
            }
        });

    }
    public void mostraMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }
}


