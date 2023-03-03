package com.theroughstallions.genki.edamam.client

import java.net.HttpURLConnection
import java.net.URL

/**
 * This is the main client for the Edamam API. It contains the URLs for the API endpoints and
 * provides functions to help communicate safely with the API. This class is a singleton.
 *
 * @property urls Map<String, URL> A map of the API endpoints.
 * @property httpURLConnection HttpURLConnection? The HTTP connection to the API.
 */
object EdamamClient {
    private val urls: Map<SearchTypes, URL> = mapOf(
        Pair(SearchTypes.PARSER, URL("https://api.edamam.com/api/food-database/v2/parser")),
        Pair(SearchTypes.NUTRIENTS, URL("https://api.edamam.com/api/food-database/v2/nutrients")),
        Pair(SearchTypes.AUTO_COMPLETION, URL("https://api.edamam.com/auto-complete"))
    )

    private var httpURLConnection: HttpURLConnection? = null

    /**
     * This function sends a parser request to the API.
     *
     * @param query String The query to send to the API.
     * @return Response The response from the API.
     */
    fun sendParserRequest(query: String): Response {
        configureConnection(SearchTypes.PARSER)
        httpURLConnection?.headerFields?.put("ingr", listOf(query))
        httpURLConnection?.connect() // Open the connection.

        val response = getResponseFromInputStream()

        httpURLConnection?.disconnect() // Close the connection.
        return response
    }

    /**
     * This function gets the response from the API.
     *
     * @return Response The response from the API.
     */
    private fun getResponseFromInputStream(): Response {
        return Response(httpURLConnection?.inputStream?.bufferedReader()?.readText() ?: "")
    }

    /**
     * This function configures the connection to the API.
     *
     * @param searchType SearchTypes The type of search to perform.
     */
    private fun configureConnection(searchType: SearchTypes) {
        httpURLConnection = urls[searchType]?.openConnection() as HttpURLConnection
        httpURLConnection?.requestMethod = "GET"
        httpURLConnection?.setRequestProperty("Accept-Encoding", "gzip")
        httpURLConnection?.headerFields?.put("app_id", listOf(ClientApplicationAuthentication.APP_ID))
        httpURLConnection?.headerFields?.put("app_key", listOf(ClientApplicationAuthentication.APP_KEY))
    }
}