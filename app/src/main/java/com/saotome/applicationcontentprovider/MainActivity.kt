package com.saotome.applicationcontentprovider

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.saotome.applicationcontentprovider.database.NotasDatabaseHelper.Companion.TITULO_NOTAS
import com.saotome.applicationcontentprovider.database.NotasProvider.Companion.URI_NOTAS
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
        notasAdd.setOnClickListener {}

        adapter = NotasAdapter()
        // para que não tenham IDs repetidos dentro do Adapter
        adapter.setHasStableIds(true)

        notasRecyclerview = activityMainbinding.notasRecycler
        notasRecyclerview.layoutManager = LinearLayoutManager (this)
        notasRecyclerview.adapter = adapter

    }

    // Instancia o que será buscado
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(this, URI_NOTAS, null, null, null, TITULO_NOTAS)

    // Pega os dados recebidos e permite sua manipulação
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data != null) {

        }
    }

    // Mata a pesquisa em segundo plano
    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }
}