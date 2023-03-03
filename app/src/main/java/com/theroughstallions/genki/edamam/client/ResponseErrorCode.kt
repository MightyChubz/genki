package com.theroughstallions.genki.edamam.client

/**
 * This class contains the error codes for the Edamam API. These codes are used to determine the
 * type of error that occurred when making a request to the API.
 * @constructor Creates a new ResponseErrorCode.
 */
enum class ResponseErrorCode(val code: Int, val message: String) {
    API_KEY_INVALID(401, "The API key is invalid!"),
    API_INVALID_SYMBOL(403, "The API contains an invalid symbol!"),
    LOW_QUALITY_DATA(555, "The data is of low quality!"),
}