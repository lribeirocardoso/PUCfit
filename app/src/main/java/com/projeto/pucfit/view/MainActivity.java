package com.projeto.pucfit.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import  com.projeto.pucfit.R;

public class MainActivity extends AppCompatActivity {

    private Button btarea_professor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        IniciarComponentes();

        btarea_professor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginProfessor.class);
                startActivity(intent);
            }
        });
    }
    private void IniciarComponentes(){
        btarea_professor = findViewById(R.id.btarea_professor);
    }
}