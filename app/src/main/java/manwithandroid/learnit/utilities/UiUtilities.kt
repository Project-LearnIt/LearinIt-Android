package manwithandroid.learnit.utilities

object UiUtilities {

    /**
     * Function to create lighter color by factor
     * @param color The basic color
     * @param factor The lighter factor (0 -> 1)
     * @return The new lighter color
     */
    fun colorLighter(color: Int, factor: Float): Int {
        val red = ((android.graphics.Color.red(color) * (1 - factor) / 255 + factor) * 255).toInt()
        val green = ((android.graphics.Color.green(color) * (1 - factor) / 255 + factor) * 255).toInt()
        val blue = ((android.graphics.Color.blue(color) * (1 - factor) / 255 + factor) * 255).toInt()
        return android.graphics.Color.argb(android.graphics.Color.alpha(color), red, green, blue)
    }

}
