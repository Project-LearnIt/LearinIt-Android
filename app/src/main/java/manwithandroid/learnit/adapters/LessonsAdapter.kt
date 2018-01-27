package manwithandroid.learnit.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import manwithandroid.learnit.R
import manwithandroid.learnit.adapters.holders.LessonHolder
import manwithandroid.learnit.models.Lesson

/**
 * Created by Roi Amiel on 17/01/2018.
 */
class LessonsAdapter(val activity: Activity, private var lessonsList: List<Lesson>? = null) : RecyclerView.Adapter<LessonHolder>() {

    override fun getItemCount() = if (lessonsList != null) lessonsList?.size!! else 0

    override fun onBindViewHolder(holder: LessonHolder?, position: Int) {
        holder?.bind(lessonsList!![position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            LessonHolder(activity.layoutInflater.inflate(R.layout.item_lesson, parent, false))


    fun updateLessonsList(lessonsList: List<Lesson>?) {
        this.lessonsList = lessonsList

        notifyDataSetChanged()
    }
}
