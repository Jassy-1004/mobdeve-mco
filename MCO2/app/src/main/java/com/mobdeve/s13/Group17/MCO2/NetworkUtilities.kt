package com.mobdeve.s13.Group17.MCO2

import android.net.Uri
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NetworkUtilities {

    // google books api
    private val GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?"
    // search string parameter
    private val SEARCH_QUERY = "q"
    // in order to limit search results parameter is available
    private val SEARCH_RESULTS = "maximumResults"
    // filtering based in the printType
    private val PRINT_TYPE = "printType"

    // log tag class name
    private val LOG_TAG: String = NetworkUtilities::class.java.getSimpleName()

    fun getBookInformation(queryString: String?): String? {

        // variables
        var urlConnect: HttpURLConnection? = null
        var reader: BufferedReader? = null
        var bookString: String? = null

        // query bookS API
        try {

            // query Uri, and limit to 15 items and printed books
            val builtURI: Uri = Uri.parse(GOOGLE_BOOKS_URL).buildUpon()
                .appendQueryParameter(SEARCH_QUERY, queryString)
                .appendQueryParameter(SEARCH_RESULTS, "15")
                .appendQueryParameter(PRINT_TYPE, "books")
                .build()
            val requestURL = URL(builtURI.toString())

            // trying to open the network connection
            urlConnect = requestURL.openConnection() as HttpURLConnection
            urlConnect.setRequestMethod("GET")
            urlConnect.connect()

            // input stream
            val inputStream: InputStream = urlConnect.getInputStream()

            // string builder
            var a: String
            val build = StringBuilder()
            reader = BufferedReader(InputStreamReader(inputStream))

            while (reader.readLine().also { a = it } != null) {
                build.append(
                    """
                $a
                """.trimIndent()
                )
            }
            if (build.length == 0) {
                return null
            }
            bookString = build.toString()

            // for errors.
        } catch (e: IOException) {
            e.printStackTrace()

           // connection will be disconnected
        } finally {
            if (urlConnect != null) {
                urlConnect.disconnect()
            }
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return bookString
    }
}