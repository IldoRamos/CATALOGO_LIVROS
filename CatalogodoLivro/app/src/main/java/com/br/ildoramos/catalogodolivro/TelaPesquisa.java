package com.br.ildoramos.catalogodolivro;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TelaPesquisa extends AppCompatActivity implements View.OnClickListener {

    private Button btnVoltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pesquisa);

        btnVoltar = findViewById(R.id.btnVoltar);

        btnVoltar.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent!=null){
            int tipo = intent.getIntExtra("tipo",0);
            String chave = intent.getStringExtra("chave");

            List<ContentValues> lista = new ArrayList<>();
            if (tipo==R.id.rbPesquisarPorTitulo){
                lista = new DatabaseHelper(this).pesquisarPorTitulo(chave);
            }else if (tipo==R.id.rbPesquisarPorAno){
                try {
                    lista = new DatabaseHelper(this).pesquisarPorAno(Integer.parseInt(chave));
                }catch (Exception e){
                    lista = new DatabaseHelper(this).pesquisarPorTodos();
                }
            }else{
                lista = new DatabaseHelper(this).pesquisarPorTodos();
            }

            if (lista!=null){
                if (lista.size()>0){
                    TableLayout tb = findViewById(R.id.tbPesquisa);
                    for (ContentValues cv: lista){
                        TableRow linha = new TableRow(this);
                        TextView coluna1 = new TextView(this);
                        coluna1.setText(String.valueOf(cv.getAsInteger("id")));
                        linha.addView(coluna1);

                        TextView coluna2 = new TextView(this);
                        coluna2.setText(String.valueOf(cv.getAsInteger("titulo")));
                        linha.addView(coluna2);


                        TextView coluna3 = new TextView(this);
                        coluna3.setText(String.valueOf(cv.getAsInteger("autor")));
                        linha.addView(coluna3);

                        TextView coluna4 = new TextView(this);
                        coluna4.setText(String.valueOf(cv.getAsInteger("ano")));
                        linha.addView(coluna4);

                        tb.addView(linha);


                    }
                }
            }
        }
    }



    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.btnVoltar){
            onBackPressed();
        }
    }
}
