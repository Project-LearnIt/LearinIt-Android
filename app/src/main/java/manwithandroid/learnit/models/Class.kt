package manwithandroid.learnit.models

import java.util.*

/**
 * Created by roi-amiel on 1/6/18.
 */
open class Class {

    var key: String = ""

    var name: String = ""
    var description: String = ""

    var teacherId: String = ""

    var from: Date? = null
    var until: Date? = null

}