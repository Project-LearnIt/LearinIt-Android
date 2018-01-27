package manwithandroid.learnit.helpers

import manwithandroid.learnit.models.Media

/**
 * Created by roi on 1/27/18.
 */
object LessonHelper {

    // Return time of media in seconds
    fun getTimeOf(media: Media): Int {
        // todo build algoritem

        return media.data.split(" ").size * 2
    }

}