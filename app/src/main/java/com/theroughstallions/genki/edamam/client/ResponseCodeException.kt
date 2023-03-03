package com.theroughstallions.genki.edamam.client

/**
 * This class throws an exception when an error code is returned from the Edamam API that is not
 * 200.
 * @constructor Creates a new ResponseCodeException.
 * @property message The error message.
 * @see Response
 * @see ResponseErrorCode
 */
class ResponseCodeException(message: String, errorCode: Int) :
    Throwable("$errorCode: $message")
