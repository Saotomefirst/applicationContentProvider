package com.saotome.applicationcontentprovider.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class NotasProvider : ContentProvider() {

    // responsável pela validação da URI do ContentProvider
    private lateinit var mUriMatcher: UriMatcher

    // Responsável pela inicialização do ContentProvider
    override fun onCreate(): Boolean {
        mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        mUriMatcher.addURI(AUTHORITY, "notas", NOTAS)
        mUriMatcher.addURI(AUTHORITY, "notas/#", NOTAS_POR_ID)
        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    // Valida uma URI (ex: imagem)
    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        TODO("Implement this to handle query requests from clients.")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }

    companion object {
        const val AUTHORITY = "com.saotome.applicationcontentprovider.provider"
        val BASE_URI = Uri.parse("content//$AUTHORITY")
        val URI_NOTAS = Uri.withAppendedPath(BASE_URI, "notas")
        // equivalente a content://com.saotome.applicationcontentprovider.provider/notas

        const val NOTAS = 1
        const val NOTAS_POR_ID = 2
    }
}