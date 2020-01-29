package com.guilherme.getherfy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.guilherme.getherfy.httpService.HttpServiceCadastro;
import com.guilherme.presentation.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

public class CadastrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        Button cadastrarBtn = findViewById(R.id.activity_cadastrar_btnCadastrar);
        ImageButton voltarBtn = findViewById(R.id.activity_cadastrar_voltarBtn);

        cadastrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText campoEmail = findViewById(R.id.activity_cadastrar_email);
                EditText campoNome = findViewById(R.id.activity_cadastrar_nome);
                EditText campoSenha= findViewById(R.id.activity_cadastrar_senha);


                String eMail = campoEmail.getText().toString().trim();
                String name = campoNome.getText().toString().trim();
                String password = campoSenha.getText().toString().trim();

                JSONObject usuarioJson = new JSONObject();

                try {
                    usuarioJson.put("email", eMail);
                    usuarioJson.put("nome", name);
                    usuarioJson.put("senha", password);

                    System.out.println(usuarioJson.toString());
                    String novoUsuarioEncoded = Base64.encodeToString(usuarioJson.toString().getBytes("UTF-8"), Base64.NO_WRAP);

                    System.out.println(novoUsuarioEncoded);

                    String mensagem = (new HttpServiceCadastro().execute(novoUsuarioEncoded).get());
                    mostraMensagem(mensagem);

                    if(mensagem.equalsIgnoreCase("Usuário criado com sucesso")) {
                        startActivity(new Intent(CadastrarActivity.this, LoginActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        TextView linkParaLogin = findViewById(R.id.activity_cadastrar_linkLogin);
        linkParaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamaActivityLogin();
            }
        });

        voltarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CadastrarActivity.this, PrimeiraTelaActivity.class));
            }
        });


    }
    public void mostraMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }

    private void chamaActivityLogin() {
        startActivity(new Intent(CadastrarActivity.this, LoginActivity.class));
        finish();
    }
}
