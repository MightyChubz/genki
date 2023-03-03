package com.theroughstallions.genki.edamam.client

/**
 * This class contains the response data for the class, which will primarily be the content body of
 * the request, typically in JSON format.
 * @constructor Creates a new Response.
 * @property body The content body of the request.
 * @see ResponseCodeException
 * @see ResponseErrorCode
 */
data class Response(
    val body: String)
