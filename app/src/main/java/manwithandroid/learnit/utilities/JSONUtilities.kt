package manwithandroid.learnit.utilities

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object JSONUtilities {

    /* Create methods */
    fun createFrom(JSONObject: String): JSONObject? {
        return try {
            JSONObject(JSONObject)

        } catch (e: JSONException) {
            null
        }

    }

    fun createFrom(JSONObject: JSONObject): JSONObject? {
        return try {
            JSONObject(JSONObject.toString())

        } catch (e: JSONException) {
            null
        }

    }

    fun createFrom(JSONArray: JSONArray): JSONArray? {
        return try {
            JSONArray(JSONArray.toString())

        } catch (e: JSONException) {
            null
        }

    }

    /* Get methods */
    fun getString(JSONObject: JSONObject, Key: String): String? {
        return try {
            JSONObject.getString(Key)

        } catch (e: JSONException) {
            null
        }

    }

    fun getString(JSONObject: JSONObject, Key: String, DefaultValue: String): String {
        return try {
            JSONObject.getString(Key)

        } catch (e: JSONException) {
            DefaultValue
        }

    }

    fun getStringArray(JSONObject: JSONObject, Key: String, DefaultValue: List<String>): List<String> {
        return try {
            val jsonArray = JSONObject.getJSONArray(Key)

            (0 until jsonArray.length()).map { jsonArray.getString(it) }

        } catch (e: JSONException) {
            DefaultValue
        }

    }

    fun getBoolean(JSONObject: JSONObject, Key: String): Boolean? {
        return try {
            JSONObject.getBoolean(Key)

        } catch (e: JSONException) {
            null
        }

    }

    fun getBoolean(JSONObject: JSONObject, Key: String, DefaultValue: Boolean?): Boolean? {
        return try {
            JSONObject.getBoolean(Key)

        } catch (e: JSONException) {
            DefaultValue
        }

    }

    fun getInt(JSONObject: JSONObject, Key: String): Int? {
        return try {
            JSONObject.getInt(Key)

        } catch (e: JSONException) {
            null
        }

    }

    fun getInt(JSONObject: JSONObject, Key: String, DefaultValue: Int?): Int? {
        return try {
            JSONObject.getInt(Key)

        } catch (e: JSONException) {
            DefaultValue
        }

    }

    fun getLong(JSONObject: JSONObject, Key: String): Long? {
        return try {
            JSONObject.getLong(Key)

        } catch (e: JSONException) {
            null
        }

    }

    fun getLong(JSONObject: JSONObject, Key: String, DefaultValue: Long?): Long? {
        return try {
            JSONObject.getLong(Key)

        } catch (e: JSONException) {
            DefaultValue
        }

    }

    fun getDouble(JSONObject: JSONObject, Key: String): Double? {
        return try {
            JSONObject.getDouble(Key)

        } catch (e: JSONException) {
            null
        }

    }

    fun getDouble(JSONObject: JSONObject, Key: String, DefaultValue: Double?): Double? {
        return try {
            JSONObject.getDouble(Key)

        } catch (e: JSONException) {
            DefaultValue
        }

    }

    operator fun get(JSONObject: JSONObject, Key: String): Any? {
        return try {
            JSONObject.get(Key)

        } catch (e: JSONException) {
            null
        }

    }

    operator fun get(JSONObject: JSONObject, Key: String, DefaultValue: Any): Any {
        return try {
            JSONObject.get(Key)

        } catch (e: JSONException) {
            DefaultValue
        }

    }

    fun getJSONObject(JSONObject: JSONObject, Key: String): JSONObject? {
        return try {
            JSONObject.getJSONObject(Key)

        } catch (e: JSONException) {
            null
        }

    }

    fun getJSONArray(JSONObject: JSONObject, Key: String): JSONArray? {
        return try {
            JSONObject.getJSONArray(Key)

        } catch (e: JSONException) {
            null
        }

    }

    fun getJSONArray(JSONArray: JSONArray, Index: Int): JSONArray? {
        return try {
            JSONArray.getJSONArray(Index)

        } catch (e: JSONException) {
            null
        }

    }

    fun getJSONArray(JSONObject: JSONObject, Key: String, DefaultValue: JSONArray): JSONArray {
        return try {
            JSONObject.getJSONArray(Key)

        } catch (e: JSONException) {
            DefaultValue
        }

    }

    fun getJSONObject(JSONArray: JSONArray, Index: Int): JSONObject? {
        return try {
            JSONArray.getJSONObject(Index)

        } catch (e: JSONException) {
            null
        }

    }

    fun getString(JSONArray: JSONArray, Index: Int): String? {
        return try {
            JSONArray.getString(Index)

        } catch (e: JSONException) {
            null
        }

    }

    /* Put methods */
    fun put(JSONObject: JSONObject, Key: String, Value: Int?) {
        try {
            if (Value == null) {
                JSONObject.remove(Key)
                return
            }

            JSONObject.put(Key, Value)

        } catch (ignored: JSONException) {

        }
    }

    fun put(JSONObject: JSONObject, Key: String, Value: Boolean?) {
        try {
            if (Value == null) {
                JSONObject.remove(Key)
                return
            }

            JSONObject.put(Key, Value)

        } catch (ignored: JSONException) {

        }

    }

    fun put(JSONObject: JSONObject, Key: String, Value: Long?) {
        try {
            if (Value == null) {
                JSONObject.remove(Key)
                return
            }

            JSONObject.put(Key, Value)

        } catch (ignored: JSONException) {

        }

    }

    fun put(JSONObject: JSONObject, Key: String, Value: Double?) {
        try {
            if (Value == null) {
                JSONObject.remove(Key)
                return
            }

            JSONObject.put(Key, Value)

        } catch (ignored: JSONException) {

        }

    }

    fun put(JSONObject: JSONObject, Key: String, Value: Any?) {
        try {
            if (Value == null) {
                JSONObject.remove(Key)
                return
            }

            JSONObject.put(Key, Value)

        } catch (ignored: JSONException) {

        }

    }

    fun put(JSONObject: JSONObject, Key: String, JSONArray: JSONArray?) {
        try {
            if (JSONArray == null) {
                JSONObject.remove(Key)
                return
            }

            JSONObject.put(Key, JSONArray)

        } catch (ignored: JSONException) {

        }

    }

    /* List to jsonarray */
    fun <E> toJSONArray(Objects: List<E>): JSONArray {
        val jsonArray = JSONArray()

        for (obj in Objects) {
            jsonArray.put(obj.toString())
        }

        return jsonArray
    }

    fun createJsonArray(str: String): JSONArray? {
        return try {
            JSONArray(str)
        } catch (e: JSONException) {
            null
        }

    }
}
