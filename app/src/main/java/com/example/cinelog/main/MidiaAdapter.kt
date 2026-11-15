package com.example.cinelog.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinelog.R
import com.example.cinelog.model.MidiaItem

class MidiaAdapter(private val lista: List<MidiaItem>) :
    RecyclerView.Adapter<MidiaAdapter.MidiaViewHolder>() {

    class MidiaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.findViewById(R.id.tvTituloItem)
        val tvTipoNota: TextView = view.findViewById(R.id.tvTipoNotaItem)
        val tvComentario: TextView = view.findViewById(R.id.tvComentarioItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MidiaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_midia, parent, false)
        return MidiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MidiaViewHolder, position: Int) {
        val item = lista[position]
        holder.tvTitulo.text = item.title
        holder.tvTipoNota.text = "${item.type} • Nota: ${item.rating} ★"
        holder.tvComentario.text = item.review
    }

    override fun getItemCount(): Int = lista.size
}