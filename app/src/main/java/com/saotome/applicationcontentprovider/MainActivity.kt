package com.saotome.applicationcontentprovider

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns._ID
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.saotome.applicationcontentprovider.database.NotasDatabaseHelper.Companion.TITULO_NOTAS
import com.saotome.applicationcontentprovider.database.NotasProvider.Companion.URI_NOTAS
import com.saotome.applicationcontentprovider.database.NotasProvider.Companion.BASE_URI
import com.saotome.applicationcontentprovider.databinding.ActivityMainBinding

/*
O LoaderManager é quem faz a busca em segundo plano do cursor que alimenta o RecyclerView,
evitando erros de threads dentro da aplicação
 */
class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var activityMainbinding: ActivityMainBinding
    private lateinit var notasRecyclerview : RecyclerView
    private lateinit var notasAdd : FloatingActionButton

    private lateinit var adapter: NotasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainbinding = ActivityMainBinding.inflate(layoutInflater)
        val activityMainView = activityMainbinding.root
        setContentView(activityMainView)

        notasAdd = activityMainbinding.notasAdd
        notasAdd.setOnClickListener {
            NotasDetailFragment().show(supportFragmentManager, "dialogo")
        }

        adapter = NotasAdapter(object : NotaClickedListener {
            @SuppressLint("Range")
            override fun notaClickedItem(cursor: Cursor) {
                val id: Long = cursor.getLong(cursor.getColumnIndex(_ID))
                val fragment = NotasDetailFragment.novaInstancia(id)
                fragment.show(supportFragmentManager, "dialogo")
            }

            @SuppressLint("Range")
            override fun notaRemoveItem(cursor: Cursor?) {
                val id: Long? = cursor?.getLong(cursor.getColumnIndex(_ID))
                //objeto responsável pela comunicação com os ContentProvider - incluindo nosso NotasProvider
                val uri = Uri.withAppendedPath(URI_NOTAS, id.toString())
                contentResolver.delete(uri, id.toString(), null)
            }

        })
        // para que não tenham IDs repetidos dentro do Adapter
        adapter.setHasStableIds(true)

        notasRecyclerview = activityMainbinding.notasRecycler
        notasRecyclerview.layoutManager = LinearLayoutManager (this)
        notasRecyclerview.adapter = adapter

        // Iniciando a thread no background
        LoaderManager.getInstance(this).initLoader(0, null, this)

    }

    // Instancia o que será buscado
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(this, URI_NOTAS, null, null, null, TITULO_NOTAS)

    // Pega os dados recebidos e permite sua manipulação
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data != null) {
            adapter.setCursor(data)
        }
    }

    // Mata a pesquisa em segundo plano
    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapter.setCursor(null)
    }
}