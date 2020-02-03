package com.guilherme.getherfy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.guilherme.getherfy.httpService.HttpServiceLogin;
import com.guilherme.presentation.R;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    Gson gson = new Gson();
    JSONObject usuarioObj;
    JSONObject organizacaoObj;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageButton voltarBtn = findViewById(R.id.activity_login_voltarBtn);


        Button loginBtn = findViewById(R.id.activity_cadastrar_btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    TextView email = findViewById(R.id.activity_login_email);
                    TextView senha = findViewById(R.id.activity_login_senha);
                    String emailString = email.getText().toString().trim();
                    String senhaString = senha.getText().toString().trim();

                    String mensagem = (new HttpServiceLogin().execute(emailString, senhaString).get());
                    mostraMensagem(mensagem);

                   if(mensagem.equalsIgnoreCase("Login efetuado com sucesso!")){
                       SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginPref", 0); // 0 - for private mode
                       SharedPreferences.Editor editor = pref.edit();

                       editor.putString("email", emailString);
                       editor.putString("senha", senhaString);

                       pref.getString("email", null); // getting String

                       editor.commit();

                       System.out.println(pref.getString("email", null));

                   }


                } catch (Exception e) {
                    e.printStackTrace();
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


