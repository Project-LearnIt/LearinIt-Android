package manwithandroid.learnit.helpers.models

/**
 * Created by roi-amiel on 1/9/18.
 */
class EventResults<T> {

    var isSuccessful: Boolean
    var resultObject: T?
    var message: String

    constructor(isSuccess: Boolean = false, resultObject: T? = null, message: String = "") {
        this.isSuccessful = isSuccess
        this.resultObject = resultObject
        this.message = message
    }

    constructor(message: String = "", isSuccess: Boolean = false, resultObject: T? = null) {
        this.isSuccessful = isSuccess
        this.resultObject = resultObject
        this.message = message
    }

    constructor(resultObject: T? = null, isSuccess: Boolean = false, message: String = "") {
        this.isSuccessful = isSuccess
        this.resultObject = resultObject
        this.message = message
    }
}

