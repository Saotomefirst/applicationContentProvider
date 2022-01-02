package com.saotome.applicationcontentprovider

import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saotome.applicationcontentprovider.database.NotasDatabaseHelper.Companion.DESCRICAO_NOTAS
import com.saotome.applicationcontentprovider.database.NotasDatabaseHelper.Companion.TITULO_NOTAS

class NotasAdapter(private val listener: NotaClickedListener): RecyclerView.Adapter<NotasViewHolder>() {

    // quem vai armazenar os dados que virão do LoaderManager - método onLoadFinisher
    private var mCursor: Cursor? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder =
        NotasViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.nota_item, parent, false))

    override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
        mCursor?.moveToPosition(position)

        holder.notaTitulo.text = mCursor?.getString(mCursor?.getColumnIndex(TITULO_NOTAS) as Int)
        holder.notaDescricao.text = mCursor?.getString(mCursor?.getColumnIndex(DESCRICAO_NOTAS) as Int)

        holder.notaBotaoExclusao.setOnClickListener {
            mCursor?.moveToPosition(position)
            listener.notaRemoveItem(mCursor)
            notifyDataSetChanged()
        }

        holder.itemView.setOnClickListener {
            mCursor?.moveToPosition(position)
            listener.notaClickedItem(mCursor as Cursor)
        }
    }

    override fun getItemCount(): Int {
        if (mCursor != null) {
            return mCursor?.count as Int
        }
        else {
            return 0
        }
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