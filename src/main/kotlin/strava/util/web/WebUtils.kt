package strava.util.web

import okhttp3.FormBody


fun Map<String, String>.toFormBody(): FormBody {
    val map = this
    return FormBody.Builder().apply {
        map.forEach { (key, value) -> add(key, value) }
    }.build()
}
