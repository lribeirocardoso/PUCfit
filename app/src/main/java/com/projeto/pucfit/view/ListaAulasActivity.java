package com.projeto.pucfit.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.projeto.pucfit.R;
import com.projeto.pucfit.model.Aula;

import java.util.ArrayList;

public class ListaAulasActivity extends AppCompatActivity {

    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    private static final String TAG = "FirestoreListActivity";
    private static final String AULAS = "aulas";

    private ArrayAdapter<Aula> aAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_aulas);

        getSupportActionBar().hide();

        ListView aulas_ListView = findViewById(R.id.aulas_ListView);

        aAdapter = new AulaAdapter(this, new ArrayList<Aula>());
        aulas_ListView.setAdapter(aAdapter);

    }
    class AulaAdapter extends ArrayAdapter<Aula>{
        ArrayList<Aula> aulas;
        AulaAdapter(Context context, ArrayList<Aula> aulas){
            super(context, 0, aulas);
            this.aulas = aulas;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.aula_padrao_lista, parent, false);
            }
            TextView aulaNome = convertView.findViewById(R.id.aula_nome);
            TextView aulaScript = convertView.findViewById(R.id.txt_script);

            Aula aula = aulas.get(position);
            aulaNome.setText(aula.getName());
            aulaScript.setText(aula.getAulaScript());

            return convertView;
        }
    }
    public void adicionarAula(View view){
        EditText txt_nome_aula = findViewById(R.id.txt_nome_aula);
        EditText txt_aula_script = findViewById(R.id.txt_aula_script);

        String name = txt_nome_aula.getText().toString();
        String aulaScript = txt_aula_script.getText().toString();

        Aula aula = new Aula(name, aulaScript);

        mDb.collection(AULAS).add(aula)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ListaAulasActivity.this, "Aula adicionada com sucesso.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ListaAulasActivity.this, "Não foi possível adicionar a aula.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Gerar a Lista de Aula (sem aulas cadastradas ou ao entrar na tela) / Atualizar a Lista (caso haja alguma aula cadastrada)
    public void atualizarAula(View view){
        mDb.collection(AULAS).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    ArrayList<Aula> aulas = new ArrayList<>();
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot document: queryDocumentSnapshots){
                           Aula aula = document.toObject(Aula.class);
                           aulas.add(aula);
                        }
                        aAdapter.clear();
                        aAdapter.addAll(aulas);
                    }
                });
    }
}



