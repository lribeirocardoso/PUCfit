package com.projeto.pucfit.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.projeto.pucfit.R;
import com.projeto.pucfit.controller.ControleRecyclerAdapter;
import com.projeto.pucfit.model.Controle;

import java.util.Date;

public class ControleActivity extends AppCompatActivity {

    private static final String TAG = "DisplayActivity";
    private static final String ALUNOS = "alunos";
    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private Spinner mNomesSpinner;
    private Spinner mSobrenomesSpinner;
    private ControleRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controle_activity);

        getSupportActionBar().hide();

        // Os nomes serão colocados utilizando Spinners para poder adicionar dados rapidamente.
        mNomesSpinner = findViewById(R.id.first_names);
        ArrayAdapter<CharSequence> firstNamesAdapter = ArrayAdapter.createFromResource(this,
                R.array.nomes, android.R.layout.simple_spinner_item);
        firstNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mNomesSpinner.setAdapter(firstNamesAdapter);


        mSobrenomesSpinner = findViewById(R.id.last_names);
        ArrayAdapter<CharSequence> lastNamesAdapter = ArrayAdapter.createFromResource(this,
                R.array.sobrenomes, android.R.layout.simple_spinner_item);
        lastNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSobrenomesSpinner.setAdapter(lastNamesAdapter);


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Query query = mDb.collection(ALUNOS)
                .orderBy("createdTime", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Controle> options = new FirestoreRecyclerOptions.Builder<Controle>()
                .setQuery(query, Controle.class)
                .build();

        mAdapter = new ControleRecyclerAdapter(options, new ControleRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Controle pessoa = mAdapter.getSnapshots().getSnapshot(position).toObject(Controle.class);
                String id = mAdapter.getSnapshots().getSnapshot(position).getId();
                mDb.collection(ALUNOS).document(id).delete();

                Toast.makeText(getApplicationContext(),"Deletando " + pessoa.getIdPessoa(),Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    public void adicionarAluno(View v) {
        String nome = mNomesSpinner.getSelectedItem().toString();
        String sobrenome = mSobrenomesSpinner.getSelectedItem().toString();
        String idPessoa = (nome.charAt(0) + sobrenome).toLowerCase();

        Controle novaPessoa = new Controle(nome, sobrenome, idPessoa, new Date());

        Toast.makeText(this, "Adicionando " + nome + " " + sobrenome, Toast.LENGTH_SHORT).show();
        mDb.collection(ALUNOS)
                .add(novaPessoa)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Aluno(a) adicionado(a) com o ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Erro ao adicionar novo(a) aluno(a)", e);
                    }
                });
    }
}
