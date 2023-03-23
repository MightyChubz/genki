package com.theroughstallions.genki.edamam.client

/**
 * This class contains the search types for the Edamam API. These types are used to determine the
 * type of search that will be performed when making a request to the API.
 * @constructor Creates a new SearchTypes.
 * @property PARSER The search type for the parser.
 * @property NUTRIENTS The search type for the nutrients.
 * @property AUTO_COMPLETION The search type for the auto-completion.
 */
enum class SearchTypes {
    PARSER,
    NUTRIENTS,
    AUTO_COMPLETION
}