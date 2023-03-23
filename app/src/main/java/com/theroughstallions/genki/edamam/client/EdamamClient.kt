package com.theroughstallions.genki.edamam.client

import android.util.Log
import java.net.HttpURLConnection
import java.net.URL

/**
 * This is the main client for the Edamam API. It contains the URLs for the API endpoints and
 * provides functions to help communicate safely with the API. This class is a singleton.
 *
 * @property urls A map of the API endpoints.
 * @property httpURLConnection The HTTP connection to the API.
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
    private var currentURL: URL? = null

    /**
     * This function sends a parser request to the API.
     *
     * @param query The query to send to the API.
     * @return The response from the API.
     */
    fun sendParserRequest(query: String): Response {
        configureURL(SearchTypes.PARSER)
        currentURL?.addQueryParameter("ingr", query)
        configureConnection(RequestMethod.GET)

        Log.d("Parser Request", httpURLConnection?.url.toString())

        httpURLConnection?.requestProperties?.forEach { (key, value) ->
            Log.d("Parser Request", "$key: $value")
        }

        httpURLConnection?.headerFields?.forEach { (key, value) ->
            Log.d("Parser Request", "$key: $value")
        }

        httpURLConnection?.connect() // Open the connection.

        checkResponseCode(httpURLConnection?.responseCode ?: 0)
        val response = getResponseFromInputStream()

        httpURLConnection?.disconnect() // Close the connection.
        return response
    }

    /**
     * This function sends a nutrients request to the API as a POST request.
     *
     * @param json The JSON to send to the API.
     * @return The response from the API.
     */
    fun sendNutrientsRequest(json: String): Response {
        configureURL(SearchTypes.NUTRIENTS)
        configureConnection(RequestMethod.POST)
        httpURLConnection?.setRequestProperty("Content-Type", "application/json")
        httpURLConnection?.setRequestProperty("Content-Length", json.length.toString())
        httpURLConnection?.outputStream?.write(json.toByteArray())

        Log.d("Nutrients Request", httpURLConnection?.url.toString())

        httpURLConnection?.headerFields?.forEach { (key, value) ->
            Log.d("Nutrients Request", "$key: $value")
        }

        httpURLConnection?.connect() // Open the connection.

        checkResponseCode(httpURLConnection?.responseCode ?: 0)
        val response = getResponseFromInputStream()

        httpURLConnection?.disconnect() // Close the connection.
        return response
    }

    /**
     * This function sends an auto-completion request to the API.
     *
     * @param query The query to send to the API.
     * @return The response from the API.
     */
    fun sendAutoCompletionRequest(query: String): Response {
        configureURL(SearchTypes.AUTO_COMPLETION)
        currentURL?.addQueryParameter("q", query)
        configureConnection(RequestMethod.GET)

        Log.d("Auto Complete Request", httpURLConnection?.url.toString())

        httpURLConnection?.requestProperties?.forEach { (key, value) ->
            Log.d("Auto Complete Request", "$key: $value")
        }

        httpURLConnection?.headerFields?.forEach { (key, value) ->
            Log.d("Auto Complete Request", "$key: $value")
        }

        httpURLConnection?.connect() // Open the connection.

        checkResponseCode(httpURLConnection?.responseCode ?: 0)
        val response = getResponseFromInputStream()

        httpURLConnection?.disconnect() // Close the connection.
        return response
    }

    /**
     * Checks if the response code is valid. If it is not, then an exception is thrown.
     * @param responseCode The response code to check.
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
     * @param responseCode The response code to check.
     * @return True if the response code is valid, false otherwise.
     */
    private fun isResponseSuccessful(responseCode: Int): Boolean {
        return responseCode == 200
    }

    /**
     * This function throws an exception based on the response code.
     * @param responseCode The response code to check.
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
     * Configures the URL to send the request to.
     *
     * @param searchType The type of search to perform.
     */
    private fun configureURL(searchType: SearchTypes) {
        currentURL =
            urls[searchType]?.addQueryParameter("app_id", EdamamCredentials.APP_ID)
                ?.addQueryParameter("app_key", EdamamCredentials.APP_KEY)
    }

    /**
     * Configures the connection to send the request to.
     *
     * @param requestMethod The request method to use.
     * @param acceptGZip True if the request should accept GZip, false otherwise.
     */
    private fun configureConnection(requestMethod: RequestMethod, acceptGZip: Boolean = false) {
        httpURLConnection = currentURL?.openConnection() as HttpURLConnection
        httpURLConnection?.requestMethod = requestMethod.name
        httpURLConnection?.setRequestProperty("Accept", "application/json")
        if (acceptGZip) {
            httpURLConnection?.setRequestProperty("Accept-Encoding", "gzip")
        }
    }

    /**
     * Adds a query parameter to the URL and allows for chaining more than one query parameter.
     * @param key The key of the query parameter.
     * @param value The value of the query parameter.
     */
    private fun URL.addQueryParameter(key: String, value: String): URL {
        val url = this.toURI().toString()
        val separator = if (url.contains("?")) "&" else "?"
        return URL("$url$separator$key=$value")
    }
}