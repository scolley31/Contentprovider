package app.appworks.school.stylish

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import app.appworks.school.stylish.data.source.local.StylishDatabase
import app.appworks.school.stylish.data.source.local.StylishDatabaseDao
import app.appworks.school.stylish.util.ServiceLocator.stylishRepository

class MyContentProvider() : ContentProvider() {

    companion object {

//        const val PROVIDER_NAME = "com.scolley.provider"
//
//        const val URL = "content://$PROVIDER_NAME/users"
//
//        val CONTENT_URI = Uri.parse(URL)
//
//        const val id = "id"
//        const val name = "name"
//        const val uriCode = 1
//        const val uriCode_2 = 2

//        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)
//
//        init {
//            MATCHER.addURI(URL, "products_in_cart_table", uriCode)
//            MATCHER.addURI(URL, "products_in_cart_table", uriCode_2)
//        }

        private val values: HashMap<String, String>? = null

        init {

            //            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
//
//            uriMatcher!!.addURI(
//                PROVIDER_NAME,
//                "users",
//                uriCode
//            )
//
//            uriMatcher!!.addURI(
//                PROVIDER_NAME,
//                "users/*",
//                uriCode
//            )
        }

    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(

        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {

        val context = context ?: return null
        val database: StylishDatabase = StylishDatabase.getInstance(context)
        val data: Cursor? = database.stylishDatabaseDao.getAllProductsToCursor()
        data?.setNotificationUri(context.contentResolver, uri)
        Log.d("test","data = $data")
        return data

//        val code = MATCHER.match(uri)
//        return if (code == uriCode || code == uriCode_2) {
//            val context = context ?: return null
//            val products = StylishDatabase.getInstance(context).stylishDatabaseDao
//            val cursor: Cursor
//            cursor = if (code == uriCode) {
//                products.getAllProductsToCursor()
//            } else {
//                products.getAllProductsToCursor()
//            }
//            cursor.setNotificationUri(context.contentResolver, uri)
//            cursor
//        } else {
//            throw IllegalArgumentException("Unknown URI: $uri")
//        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

//    fun getCursor(): Cursor? {
//
//        var cursor: Cursor?
//        coroutineScope.launch {
//            val result = stylishRepository?.getAllProductsToCursor()
//            Log.d("test","result = $result")
//            cursor = result
//        }
//
//
//        return cursor
//    }

}