package com.guilherme.getherfy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.guilherme.getherfy.httpService.HttpServiceCadastro;
import com.guilherme.getherfy.httpService.HttpServiceOrganizacao;
import com.guilherme.getherfy.organizacao.model.Organizacao;
import com.guilherme.presentation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.view.View.VISIBLE;

public class CadastrarActivity extends AppCompatActivity {

    private int contagem;
    JSONArray organizacoesJson = new JSONArray();

    Organizacao novaOrganizacao = new Organizacao();
    int escolhaDoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        Button cadastrarBtn = findViewById(R.id.activity_cadastrar_btnCadastrar);
        ImageButton voltarBtn = findViewById(R.id.activity_cadastrar_voltarBtn);
        final EditText campoEmail = findViewById(R.id.activity_cadastrar_email);


        cadastrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        campoEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String emailAfterChange = pegaDominio(campoEmail);

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

    private String pegaDominio(EditText campoEmail) {
        String emailAfterChange = campoEmail.getText().toString().trim();
        if(emailAfterChange.contains("@")){
            String[] dominioArray = emailAfterChange.split("@", 2);
            System.out.println("DOMINIO: "+dominioArray[1]);

            if(dominioArray[1]!=null && dominioArray[0]!=null) {
                if(dominioArray[1].contains(".") && dominioArray[1].length()>3){
                    try {

                        String lista = new HttpServiceOrganizacao().execute(dominioArray[1]).get();
                        if(lista.contains(dominioArray[1])) {
/*
                            System.out.println(lista);
*/
                            try {
                                JSONArray listaJson = new JSONArray(lista);
                                List<Organizacao> listaDeOrganizacoes = new ArrayList<>();

                                if(listaJson.length() > 0){
                                    String[] organizacoes = new String[listaJson.length()];

                                    for(int i = 0; i<listaJson.length(); i++){
                                        JSONObject organizacaoObj = listaJson.getJSONObject(i);
                                        if(organizacaoObj.has("id") &&
                                                organizacaoObj.has("nome") &&
                                                organizacaoObj.has("tipoOrganizacao") ){

                                            int id = organizacaoObj.getInt("id");
                                            String nome = organizacaoObj.getString("nome");
                                            String tipoOrganizacao = organizacaoObj.getString("tipoOrganizacao");
                                            novaOrganizacao.setId(id);
                                            novaOrganizacao.setNome(nome);
                                            novaOrganizacao.setTipoOrganizacao(tipoOrganizacao);


                                            organizacoes[i]= nome;

                                        }

                                    }
                                    alertaOrganizacoes(organizacoes);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

            return "";
        }
        return emailAfterChange;
    }

    private void alertaOrganizacoes(final String[] organizacoes) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("É preciso selecionar sua organizacão");
        builder.setSingleChoiceItems(organizacoes, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ImageButton warningBtn = findViewById(R.id.actitivy_cadastrar_warning);
                warningBtn.setVisibility(VISIBLE);
                warningBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertaOrganizacoes(organizacoes);

                    }
                });


            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void mostraMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }

    private void chamaActivityLogin() {
        startActivity(new Intent(CadastrarActivity.this, LoginActivity.class));
        finish();
    }
}
