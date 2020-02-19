package com.guilherme.getherfy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.guilherme.getherfy.httpService.HttpServiceLogin;
import com.guilherme.getherfy.organizacao.model.Organizacao;
import com.guilherme.getherfy.usuario.model.Usuario;
import com.guilherme.presentation.R;

import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    boolean isSenhaVisivel=false;
    SharedPreferences preferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageButton voltarBtn = findViewById(R.id.activity_login_voltarBtn);
        final TextView emailEt = findViewById(R.id.activity_login_email);
        final TextView senhaEt = findViewById(R.id.activity_login_senha);

        Button loginBtn = findViewById(R.id.activity_cadastrar_btnLogin);
        ImageButton btnViewPassword = findViewById(R.id.activity_login_view_passoword);


        btnViewPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!isSenhaVisivel) {
                    senhaEt.setInputType(128);
                    isSenhaVisivel=true;
                }else if (isSenhaVisivel){
                    senhaEt.setInputType(225);
                    isSenhaVisivel=false;
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    final String emailString = emailEt.getText().toString().trim();
                    final String senhaString = senhaEt.getText().toString().trim();



                    String usuarioLogado = new HttpServiceLogin().execute(emailString, senhaString).get();
                    if (usuarioLogado.length() > 0) {
                        Toast.makeText(getApplicationContext(), "Login realizado com sucesso", Toast.LENGTH_LONG).show();

                        JSONObject usuarioJSON = new JSONObject(usuarioLogado);

                        if (usuarioJSON.has("email") && usuarioJSON.has("id") && usuarioJSON.has("nome") && usuarioJSON.has("idOrganizacao")) {
                            int id = usuarioJSON.getInt("id");
                            String nome = usuarioJSON.getString("nome");
                            String email = usuarioJSON.getString("email");

                            JSONObject organizacao = usuarioJSON.getJSONObject("idOrganizacao");
                            String nomeOrganizacao = organizacao.getString("nome");
                            String tipoOrganizacao = organizacao.getString("tipoOrganizacao");
                            int idOrganizacao = organizacao.getInt("id");


                            Usuario usuarioAuth = new Usuario();
                            usuarioAuth.setId(id);
                            usuarioAuth.setNome(nome);
                            usuarioAuth.setEmail(email);
                            usuarioAuth.setIdOrganizacao(idOrganizacao);

                            Organizacao org = new Organizacao();
                            org.setId(idOrganizacao);
                            org.setNome(nomeOrganizacao);
                            org.setTipoOrganizacao(tipoOrganizacao);

                            //Refatorar
                            preferences = getSharedPreferences("USER_LOGIN", 0);

                            SharedPreferences.Editor editor = preferences.edit();

                            editor.putString("userEmail", email);
                            editor.putString("userName", nome);
                            editor.putString("userId", Integer.toString(id));
                            editor.putString("userIdOrganizacao", Integer.toString(idOrganizacao));
                            editor.putString("userNomeEmpresa", nomeOrganizacao);
                            editor.putString("userTipoOrganizacao", tipoOrganizacao);
                            editor.commit();
                        }
                        System.out.println(preferences.getString("userEmail", null));
                        System.out.println(preferences.getString("userName", null));
                        System.out.println(preferences.getString("userId", null));
                        System.out.println(preferences.getString("userIdOrganizacao", null));
                        System.out.println(preferences.getString("userNomeEmpresa", null));
                        System.out.println(preferences.getString("userTipoEmpresa", null));

                        startActivity(new Intent(LoginActivity.this, AbasActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Login inválido!", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), " inválido", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        });


        voltarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PrimeiraTelaActivity.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}


