package com.saotome.applicationcontentprovider

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity?.layoutInflater?.inflate(R.layout.nota_detalhe, null)

        notaEditarTitulo = view?.findViewById(R.id.nota_editar_titulo) as EditText
        notaEditarDescricao =view?.findViewById(R.id.nota_editar_descricao) as EditText

        return super.onCreateDialog(savedInstanceState)
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        TODO("Not yet implemented")
    }
}