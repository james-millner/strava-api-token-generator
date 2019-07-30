package strava.util.modification

import org.json.XML

fun readFileToJson(fileAsString: String): String = formatXMLToJson(fileAsString)

fun formatXMLToJson(xml: String): String = XML.toJSONObject(xml)
        .toString()
