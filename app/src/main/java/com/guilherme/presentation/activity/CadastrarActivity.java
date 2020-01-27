package com.guilherme.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.guilherme.presentation.R;

public class CadastrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        Button cadastrarBtn = findViewById(R.id.activity_cadastrar_btnCadastrar);

        cadastrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText campoEmail = findViewById(R.id.activity_cadastrar_email);
                String emailString = campoEmail.getText().toString();
                System.out.println("EMAIL DA PESSOA: "+emailString);

                System.out.println(emailString);
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
        startActivity(new Intent(CadastrarActivity.this, LoginActivity.class));
        finish();
    }
}
