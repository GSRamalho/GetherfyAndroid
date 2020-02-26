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
import com.guilherme.getherfy.httpService.HttpServiceOrganizacaoByDominio;
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
    private boolean isSenhaVisivel = false;
    private Organizacao novaOrganizacao = new Organizacao();
    private List<Organizacao> listaDeOrganizacoes = new ArrayList<>();
    private boolean marcouOrganizacao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        Button cadastrarBtn = findViewById(R.id.activity_cadastrar_btnCadastrar);
        ImageButton voltarBtn = findViewById(R.id.activity_cadastrar_voltarBtn);
        final EditText campoEmail = findViewById(R.id.activity_cadastrar_email);
        final EditText campoSenha = findViewById(R.id.activity_login_senha);

        configuraBtnOlharSenha(campoSenha);

        cadastrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText campoNome = findViewById(R.id.activity_cadastrar_nome);

                String eMail = campoEmail.getText().toString().trim();
                String name = campoNome.getText().toString().trim();
                String password = campoSenha.getText().toString().trim();

                JSONObject usuarioJson = new JSONObject();

                try {
                    usuarioJson.put("email", eMail);
                    usuarioJson.put("nome", name);
                    usuarioJson.put("senha", password);
                    usuarioJson.put("idOrganizacao", novaOrganizacao.getId());


                    System.out.println(usuarioJson.toString());
                    String novoUsuarioEncoded = Base64.encodeToString(usuarioJson.toString().getBytes("UTF-8"), Base64.NO_WRAP);

                    System.out.println(novoUsuarioEncoded);

                    String mensagem = (new HttpServiceCadastro().execute(novoUsuarioEncoded).get());
                    mostraMensagem(mensagem);

                    if (mensagem.equalsIgnoreCase("Usuário criado com sucesso")) {
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
                } catch (Exception e) {
                    mostraMensagem("Impossível conectar com o servidor");
                }
            }
        });

        campoEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pegaDominio(campoEmail);

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
                finish();
            }
        });

    }

    private String pegaDominio(EditText campoEmail) {
        String emailInvalido = "Email invalido!";
        String emailInserido = campoEmail.getText().toString().trim();
        if (emailInserido.contains("@")) {
            String[] dominioArray = emailInserido.split("@", 2);
            System.out.println("DOMINIO: " + dominioArray[1]);

            if (dominioArray[1] != null && dominioArray[0] != null) {
                if (dominioArray[1].contains(".") && dominioArray[1].length() > 3) {
                    try {
                        requestOrganizacoes(dominioArray[1]);

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return dominioArray[1];
                }
                return emailInvalido;
            }
            return emailInvalido;
        }
        return emailInserido;
    }

    private void requestOrganizacoes(String dominio) throws ExecutionException, InterruptedException {
        String lista = new HttpServiceOrganizacaoByDominio().execute(dominio).get();
        if (lista.contains(dominio)) {

            try {
                JSONArray listaJson = new JSONArray(lista);
                if (listaJson.length() > 0) {
                    String[] organizacoes = new String[listaJson.length()];

                    int[] idOrganizacoes = new int[listaJson.length()];

                    for (int i = 0; i < listaJson.length(); i++) {
                        System.out.println(listaJson);

                        JSONObject organizacaoObj = listaJson.getJSONObject(i);
                        if (organizacaoObj.has("id") &&
                                organizacaoObj.has("nome") &&
                                organizacaoObj.has("tipoOrganizacao")) {

                            int id = organizacaoObj.getInt("id");
                            String nome = organizacaoObj.getString("nome");
                            String tipoOrganizacao = organizacaoObj.getString("tipoOrganizacao");
                            novaOrganizacao.setId(id);
                            novaOrganizacao.setNome(nome);
                            novaOrganizacao.setTipoOrganizacao(tipoOrganizacao);


                            System.out.println("Qnt Orgs: " + listaJson.length());

                            if (listaJson.length() > 1) {
                                organizacoes[i] = nome;
                                idOrganizacoes[i] = id;

                                if (listaJson.length() == i + 1) {
                                    alertaDeConflitoOrganizacoes(organizacoes, idOrganizacoes);
                                }
                            } else if (listaJson.length() == 1) {
                                listaDeOrganizacoes.add(novaOrganizacao);
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void configuraBtnOlharSenha(final TextView senhaEt) {
        ImageButton btnViewPassword = findViewById(R.id.activity_cadastrar_btn_view_password);


        btnViewPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isSenhaVisivel) {
                    senhaEt.setInputType(128);
                    isSenhaVisivel = true;
                } else if (isSenhaVisivel) {
                    senhaEt.setInputType(225);
                    isSenhaVisivel = false;
                }
            }
        });
    }

    private void alertaDeConflitoOrganizacoes(final String[] organizacoes, final int[] idOrganizacoes) {
        final ImageButton warningBtn = findViewById(R.id.actitivy_cadastrar_warning);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("É preciso selecionar sua organizacão");
        builder.setSingleChoiceItems(organizacoes, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                novaOrganizacao.setNome(organizacoes[i]);
                novaOrganizacao.setId(idOrganizacoes[i]);
                listaDeOrganizacoes.add(novaOrganizacao);

            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("ID da organizacao escolhido: " + novaOrganizacao.getId());
                System.out.println("Nome da organizacao escolhida: " + novaOrganizacao.getNome());

                warningBtn.setVisibility(View.GONE);

                marcouOrganizacao = true;
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (marcouOrganizacao == false) {
                    warningBtn.setVisibility(VISIBLE);
                    warningBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alertaDeConflitoOrganizacoes(organizacoes, idOrganizacoes);
                        }
                    });
                }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
