package com.guilherme.getherfy.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.guilherme.getherfy.httpService.HttpServiceGetUsuario;
import com.guilherme.getherfy.httpService.HttpServiceLogin;
import com.guilherme.getherfy.httpService.HttpServiceOrganizacaoById;
import com.guilherme.getherfy.organizacao.model.Organizacao;
import com.guilherme.presentation.R;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    Gson gson = new Gson();
    JSONObject usuarioObj;
    JSONObject organizacaoObj;

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

                        final Account account = new Account(emailString, "com.guilherme.getherfy");
                        AccountManager accManager = AccountManager.get(getApplicationContext());
                        accManager.addAccountExplicitly(account, senhaString, null);

//                      Account[] recupera = accManager.getAccountsByType("Account");
//                       System.out.println(recupera[0].name);

                        System.out.println(accManager.getAccounts());
                        startActivity(new Intent(LoginActivity.this, AbasActivity.class));

                        String usuario = (new HttpServiceGetUsuario().execute(emailString).get());
                        System.out.println(usuario);
                        usuario = gson.toJson(usuarioObj);

                        if(usuarioObj.has("idOrganizacao")){
                            int idOrg = usuarioObj.getInt("idOrganizacao");
                            String idString = Integer.toString(idOrg);
                            String organizacao = (new HttpServiceOrganizacaoById().execute(idString).get());
                            organizacao = gson.toJson(organizacaoObj);

                            String organizacaoNome = organizacaoObj.getString("nome");
                            System.out.println("Organizacao"+organizacao);

                            Organizacao org = new Organizacao();
                            org.setNome(organizacaoNome);

                        }

                    }
                }
                catch (Exception e)
                {
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


