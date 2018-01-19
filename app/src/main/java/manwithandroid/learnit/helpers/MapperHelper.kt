package manwithandroid.learnit.helpers

import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONObject

/**
 * Created by roi-amiel on 1/7/18.
 */
object MapperHelper {

    /** Function to create object by map from json string */
    fun <T : Any?> fromJson(json: String?, objectClass: Class<T>): T? =
            if (json != null) ObjectMapper().readValue(json, objectClass) else null

    /** Function to convert object by map to json */
    fun toJson(objectAny: Any): JSONObject = JSONObject(ObjectMapper().writeValueAsString(objectAny))

}