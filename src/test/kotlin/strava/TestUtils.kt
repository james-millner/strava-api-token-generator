package strava

import java.io.FileInputStream
import java.nio.charset.StandardCharsets
import org.apache.commons.io.IOUtils
import org.springframework.util.ResourceUtils

fun getResource(resource: String): String {
    val file = ResourceUtils.getFile(resource)
    val inputStream = FileInputStream(file)
    return IOUtils.toString(inputStream, StandardCharsets.UTF_8.name())
}
