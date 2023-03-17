package com.theroughstallions.genki.edamam.client

import java.net.HttpURLConnection
import java.net.URL

/**
 * This is the main client for the Edamam API. It contains the URLs for the API endpoints and
 * provides functions to help communicate safely with the API. This class is a singleton.
 *
 * @property urls Map<String, URL> A map of the API endpoints.
 * @property httpURLConnection HttpURLConnection? The HTTP connection to the API.
 * @see SearchTypes
 * @see Response
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

        checkResponseCode(httpURLConnection?.responseCode ?: 0)
        val response = getResponseFromInputStream()

        httpURLConnection?.disconnect() // Close the connection.
        return response
    }

    /**
     * This function sends a nutrients request to the API.
     *
     * @param query String The query to send to the API.
     * @return Response The response from the API.
     */
    fun sendNutrientsRequest(query: String): Response {
        configureConnection(SearchTypes.NUTRIENTS)
        httpURLConnection?.headerFields?.put("ingredients", listOf(query))
        httpURLConnection?.connect() // Open the connection.

        checkResponseCode(httpURLConnection?.responseCode ?: 0)
        val response = getResponseFromInputStream()

        httpURLConnection?.disconnect() // Close the connection.
        return response
    }

    /**
     * This function sends an auto-completion request to the API.
     *
     * @param query String The query to send to the API.
     * @return Response The response from the API.
     */
    fun sendAutoCompletionRequest(query: String): Response {
        configureConnection(SearchTypes.AUTO_COMPLETION)
        httpURLConnection?.headerFields?.put("q", listOf(query))
        httpURLConnection?.connect() // Open the connection.

        checkResponseCode(httpURLConnection?.responseCode ?: 0)
        val response = getResponseFromInputStream()

        httpURLConnection?.disconnect() // Close the connection.
        return response
    }

    /**
     * Checks if the response code is valid. If it is not, then an exception is thrown.
     * @param responseCode Int The response code to check.
     * @throws ResponseCodeException The exception to throw.
     * @see ResponseErrorCode
     * @see ResponseCodeException
     * @see isResponseSuccessful
     * @see throwOnResponseError
     */
    private fun checkResponseCode(responseCode: Int) {
        if (!isResponseSuccessful(responseCode)) {
            throwOnResponseError(responseCode)
        }
    }

    /**
     * Checks if the response code is valid. If it is not, then an exception is thrown.
     *
     * @param responseCode Int The response code to check.
     * @return Boolean True if the response code is valid, false otherwise.
     */
    private fun isResponseSuccessful(responseCode: Int): Boolean {
        return responseCode == 200
    }

    /**
     * This function throws an exception based on the response code.
     * @param responseCode Int The response code to check.
     * @throws ResponseCodeException The exception to throw.
     * @see ResponseErrorCode
     * @see ResponseCodeException
     */
    private fun throwOnResponseError(responseCode: Int) {
        val responseErrorCode = ResponseErrorCode.values().find { it.code == responseCode }
        if (responseErrorCode != null) {
            throw ResponseCodeException(responseErrorCode.message, responseErrorCode.code)
        } else if (responseCode == 0) {
            throw ResponseCodeException("No response received!", responseCode)
        }

        throw ResponseCodeException("Unknown error occurred!", responseCode)
    }

    /**
     * This function gets the response from the input stream.
     *
     * @return The response from the API.
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
        httpURLConnection?.headerFields?.put(
            "app_id",

    /**
     * Adds a query parameter to the URL and allows for chaining more than one query parameter.
     * @param key String The key of the query parameter.
     * @param value String The value of the query parameter.
     */
    private fun URL.addQueryParameter(key: String, value: String): URL {
        val url = this.toURI().toString()
        val separator = if (url.contains("?")) "&" else "?"
        return URL("$url$separator$key=$value")
    }
}