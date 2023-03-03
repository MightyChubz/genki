package com.theroughstallions.genki.edamam.client

class ResponseCodeException(message: String, errorCode: Int) :
    Throwable("$errorCode: $message") {
}
