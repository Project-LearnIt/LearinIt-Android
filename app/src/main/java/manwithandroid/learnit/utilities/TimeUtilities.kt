package manwithandroid.learnit.utilities

import java.text.SimpleDateFormat
import java.util.*

object TimeUtilities {
    const val weekMils = 604800000

    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    fun sleep(time: Long) = if (time > 0) Thread.sleep(time) else Unit

    fun getFirstDayDate(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK))

        return calendar.timeInMillis
    }

    fun asWeekBefore(time: Long) = time - weekMils

    fun clearCalender(calendar: Calendar) {
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.AM_PM, Calendar.AM)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
    }

    fun currentClearTimeMillis(): Long {
        val today = Calendar.getInstance()
        today.timeInMillis = System.currentTimeMillis()

        clearCalender(today)

        return today.timeInMillis
    }

    fun getNextFirstDay(time: Long = TimeUtilities.currentClearTimeMillis()): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time

        while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.firstDayOfWeek) {
            calendar.add(Calendar.DATE, 1)
        }

        clearCalender(calendar)

        return calendar.timeInMillis
    }
}
