package com.theroughstallions.genki.edamam.client

/**
 * This class contains the request methods for the Edamam API.
 * @property method The request method.
 * @constructor Creates a new RequestMethod.
 */
enum class RequestMethod {
    GET,
    POST,
    PUT,
    DELETE,
    PATCH,
    HEAD,
    OPTIONS,
    TRACE,
}