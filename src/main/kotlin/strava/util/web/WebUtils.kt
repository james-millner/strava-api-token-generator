package strava.util.web

import khttp.responses.Response

fun ifSuccessfulRequest(response: Response): Boolean =
    response.statusCode == 200

fun buildHeaders(token: String): Map<String, String> =
    mapOf("Authorization" to "Bearer $token")

fun getEndpointUrl(url: String, endpoint: String) =
    StringBuilder()
        .append(url)
        .append(endpoint)
        .toString()
