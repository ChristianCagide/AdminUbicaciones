package com.example.adminubicaciones;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> mTitulos = new ArrayList<>();
    private ArrayList<String> mMensajes = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mTitulos, ArrayList<String> mMensajes) {
        this.mTitulos = mTitulos;
        this.mMensajes = mMensajes;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mTitulo.setText(mTitulos.get(i));
        viewHolder.mMensaje.setText(mMensajes.get(i));
    }

    @Override
    public int getItemCount() {
        return mTitulos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTitulo;
        TextView mMensaje;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitulo = itemView.findViewById(R.id.textView_titulo);
            mMensaje = itemView.findViewById(R.id.textView_mensaje);
        }
    }
}
