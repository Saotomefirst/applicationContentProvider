package com.saotome.applicationcontentprovider.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.UnsupportedSchemeException
import android.net.Uri
import android.provider.BaseColumns._ID
import com.saotome.applicationcontentprovider.database.NotasDatabaseHelper.Companion.TABELA_NOTAS

class NotasProvider : ContentProvider() {

    // responsável pela validação da URI do ContentProvider
    private lateinit var mUriMatcher: UriMatcher
    private lateinit var dbHelper: NotasDatabaseHelper

    // Responsável pela inicialização do ContentProvider
    override fun onCreate(): Boolean {
        mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        mUriMatcher.addURI(AUTHORITY, "notas", NOTAS)
        mUriMatcher.addURI(AUTHORITY, "notas/#", NOTAS_POR_ID)


        if (context != null) {
            dbHelper = NotasDatabaseHelper(context as Context)
        }

        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        // Exclusão no banco sempre é feita por ID
        val teste = mUriMatcher.match(uri)
        if (mUriMatcher.match(uri) == NOTAS_POR_ID) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val linhasAfetadas: Int = db.delete(TABELA_NOTAS, "$_ID = ?", arrayOf(uri.lastPathSegment))
//            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return linhasAfetadas
        }
        else {
            throw UnsupportedSchemeException("URI ${uri.toString()} <> $NOTAS_POR_ID inválida para exclusão")
        }

    }

    // Valida uma URI (ex: imagem)
    override fun getType(uri: Uri): String? {
        // Usado para requisições de arquivos, mas aqui não será usado
        throw UnsupportedSchemeException("URI não implementada")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (mUriMatcher.match(uri) == NOTAS) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val id = db.insert(TABELA_NOTAS, null, values)
            val insertURI: Uri = Uri.withAppendedPath(BASE_URI, id.toString())
//            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return insertURI
        }else {
            throw UnsupportedSchemeException("URI inválida para inserção")
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when {
            // qualquer outra coisa que não seja o ID
            mUriMatcher.match(uri) == NOTAS -> {
                val db: SQLiteDatabase = dbHelper.writableDatabase
                val cursor: Cursor = db.query(TABELA_NOTAS, projection, selection, selectionArgs, null, null, sortOrder)
                cursor.setNotificationUri((context as Context).contentResolver, uri)
//                db.close()
                cursor
            }
            mUriMatcher.match(uri) == NOTAS_POR_ID -> {
                val db: SQLiteDatabase = dbHelper.writableDatabase
                val cursor: Cursor = db.query(TABELA_NOTAS, projection, "$_ID = ?", arrayOf(uri.lastPathSegment), null, null, sortOrder)
                cursor.setNotificationUri((context as Context).contentResolver, uri)
//                db.close()
                cursor
            }
            else -> {
                throw UnsupportedSchemeException("URI ${uri.toString()} não implementada para busca")
            }
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val teste = mUriMatcher.match(uri)
        if (mUriMatcher.match(uri) == NOTAS_POR_ID) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val linhasAfetadas: Int = db.update(TABELA_NOTAS, values, "$_ID = ?", arrayOf(uri.lastPathSegment))
//            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return linhasAfetadas
        }
        else {
            throw UnsupportedSchemeException("URI não implementada para atualização")
        }
    }

    companion object {
        const val AUTHORITY = "com.saotome.applicationcontentprovider.provider"
        val BASE_URI = Uri.parse("content://$AUTHORITY")

        val URI_NOTAS = Uri.withAppendedPath(BASE_URI, "notas")
        // equivalente a content://com.saotome.applicationcontentprovider.provider/notas
        val URI_NOTAS_POR_ID = Uri.withAppendedPath(BASE_URI, "notas/#")
        // equivalente a content://com.saotome.applicationcontentprovider.provider/notas/#

        const val NOTAS = 1
        const val NOTAS_POR_ID = 2
    }
}