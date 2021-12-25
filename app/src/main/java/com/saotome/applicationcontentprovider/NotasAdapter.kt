package com.saotome.applicationcontentprovider

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotasAdapter(): RecyclerView.Adapter<NotasViewHolder>() {

    // quem vai armazenar os dados que virão do LoaderManager - método onLoadFinisher
    private var mCursor: Cursor? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder =
        NotasViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.nota_item, parent, false))

    override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    fun setCursor (novoCursor: Cursor?) {
        mCursor = novoCursor
        notifyDataSetChanged()
    }
}

class NotasViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val notaTitulo = itemView.findViewById(R.id.nota_titulo) as TextView
    val notaDescricao = itemView.findViewById(R.id.nota_descricao) as TextView
    val notaBotaoExclusao = itemView.findViewById(R.id.nota_botao_exclusao) as Button
}