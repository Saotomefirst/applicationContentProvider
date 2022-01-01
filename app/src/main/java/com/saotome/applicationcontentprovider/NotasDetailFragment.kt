package com.saotome.applicationcontentprovider

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.saotome.applicationcontentprovider.database.NotasDatabaseHelper.Companion.DESCRICAO_NOTAS
import com.saotome.applicationcontentprovider.database.NotasDatabaseHelper.Companion.TITULO_NOTAS
import com.saotome.applicationcontentprovider.database.NotasProvider.Companion.URI_NOTAS
import com.saotome.applicationcontentprovider.databinding.NotaDetalheBinding

class NotasDetailFragment: DialogFragment(), DialogInterface.OnClickListener {

    private lateinit var notaEditarTitulo: EditText
    private lateinit var notaEditarDescricao: EditText
    private var id: Long  = 0

    companion object  {
        private const val EXTRA_ID = "ID"
        fun novaInstancia (id: Long): NotasDetailFragment {
            val bundle = Bundle()
            bundle.putLong(EXTRA_ID, id)

            val notasFragment = NotasDetailFragment()
            notasFragment.arguments = bundle

            return notasFragment
        }
    }

    @SuppressLint("Range")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.nota_detalhe, null)

        notaEditarTitulo = view?.findViewById(R.id.nota_editar_titulo) as EditText
        notaEditarDescricao =view?.findViewById(R.id.nota_editar_descricao) as EditText

        var isNovaNota = true

        if (arguments != null && arguments?.getLong(EXTRA_ID) != 0L) {
            // Temos um ID vindo da activity
            id = arguments?.getLong(EXTRA_ID) as Long
            val uri = Uri.withAppendedPath(URI_NOTAS, id.toString())
            val cursor =
                activity?.contentResolver?.query(uri, null, null, null, null)
            if (cursor?.moveToNext() as Boolean) {
                // A ID existe no banco de Dados
                isNovaNota = false
                notaEditarTitulo.setText(cursor.getString(cursor.getColumnIndex(TITULO_NOTAS)))
                notaEditarDescricao.setText(cursor.getString(cursor.getColumnIndex(DESCRICAO_NOTAS)))
            }
            cursor.close()
        }

        // Criando nosso próprio Alert
        return AlertDialog.Builder(activity as Activity)
            .setTitle(if (isNovaNota == true) "Nova Mensagem" else "Editar Mensagem")
            .setView(view)
            .setPositiveButton("Salvar", this)
            .setNegativeButton("Cancelar", this)
            .create()
    }

    // Criando a parte de imput dos dados
    override fun onClick(dialog: DialogInterface?, which: Int) {
        val valores = ContentValues()
        valores.put(TITULO_NOTAS, notaEditarTitulo.text.toString())
        valores.put(DESCRICAO_NOTAS, notaEditarDescricao.text.toString())

        if (id != 0L) {
            val uri = Uri.withAppendedPath(URI_NOTAS, id.toString())
            context?.contentResolver?.update(uri, valores, null, null)
        }
        else {
            context?.contentResolver?.insert(URI_NOTAS, valores)
        }
    }
}