package strava.util.modification

import org.json.XML

fun readFileToJson(fileAsString: String): String = XML.toJSONObject(fileAsString)
        .toString()