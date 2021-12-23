package com.saotome.applicationcontentprovider.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID

class NotasDatabaseHelper(
    context:Context
): SQLiteOpenHelper(context, "databaseNotas", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATETABELA_$TABELA_NOTAS (" +
                "$_ID INTEGER NOT NULL PRIMARY KEY," +
                "$TITULO_NOTAS TEXT NOT NULL," +
                "$DESCRICAO_NOTAS TEXT NOT NULL" +
                ")")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // não vamos usar, mas já sabemos para que ele serve!
    }

    companion object {
        /*
        Boas práticas do SQLite:
        Nome de Tabela: Primeira letra maiúscula
        nomes dos campos: todas as letras minúsculas
         */
        const val TABELA_NOTAS:String = "Notas"
        const val TITULO_NOTAS:String = "titulo"
        const val DESCRICAO_NOTAS:String = "descricao"

    }

}