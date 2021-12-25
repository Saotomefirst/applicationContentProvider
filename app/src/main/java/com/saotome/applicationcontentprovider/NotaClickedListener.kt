package com.saotome.applicationcontentprovider

import android.database.Cursor

interface NotaClickedListener {
    fun notaClickedItem (cursor: Cursor)
    fun notaRemoveItem (cursor: Cursor?)
}