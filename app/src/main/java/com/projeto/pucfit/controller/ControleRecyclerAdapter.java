package com.projeto.pucfit.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.projeto.pucfit.R;
import com.projeto.pucfit.model.Controle;

import java.text.SimpleDateFormat;

public class ControleRecyclerAdapter extends FirestoreRecyclerAdapter<Controle, ControleRecyclerAdapter.ControleViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
    private final OnItemClickListener listener;

    public ControleRecyclerAdapter(FirestoreRecyclerOptions<Controle> options, OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }

    ControleRecyclerAdapter(FirestoreRecyclerOptions<Controle> options) {
        super(options);
        this.listener = null;
    }

    class ControleViewHolder extends RecyclerView.ViewHolder {
        final CardView view;
        final TextView idPessoa;
        final TextView nome;
        final TextView sobrenome;
        final TextView createdTime;

        ControleViewHolder(CardView v) {
            super(v);
            view = v;
            idPessoa = v.findViewById(R.id.idPessoa);
            nome = v.findViewById(R.id.txt_nome);
            sobrenome = v.findViewById(R.id.txt_sobrenome);
            createdTime = v.findViewById(R.id.txt_createdTime);
        }
    }

    // Realoca os conteúdos das views
    @Override
    public void onBindViewHolder(final ControleViewHolder holder, @NonNull int position, @NonNull final Controle pessoa) {
        // - pega o elemento do banco nesta posição;
        // - altera a posição dos conteúdos das views com o elemento.
        holder.idPessoa.setText(pessoa.getIdPessoa());
        holder.nome.setText(pessoa.getNome());
        holder.sobrenome.setText(pessoa.getSobrenome());
        holder.createdTime.setText(holder.view.getContext()
                .getString(R.string.created_on, format.format(pessoa.getCreatedTime())));
        if (listener != null) {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(holder.getAbsoluteAdapterPosition());
                }
            });
        }
    }

    // Cria novas views.
    @Override
    public ControleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.controle_lista_item, parent, false);

        return new ControleViewHolder(v);
    }
}




