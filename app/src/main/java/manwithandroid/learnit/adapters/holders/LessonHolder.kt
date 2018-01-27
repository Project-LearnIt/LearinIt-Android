package manwithandroid.learnit.adapters.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import manwithandroid.learnit.R
import manwithandroid.learnit.gui.NeutralCheckBox
import manwithandroid.learnit.models.Lesson

/**
 * Created by Roi Amiel on 1/27/2018.
 */
class LessonHolder(view: View) : RecyclerView.ViewHolder(view) {

    /* Views */
    private var neutralCheckBox: NeutralCheckBox = itemView.findViewById(R.id.neutralCheckBox)
    private var lessonNameTextView: TextView = itemView.findViewById(R.id.lessonNameTextView)

    fun bind(lesson: Lesson) {
        lessonNameTextView.text = lesson.name

        neutralCheckBox.setState(when (lesson.state) {
            Lesson.STATE_SUCCESSES -> NeutralCheckBox.STATE_CHECKED
            Lesson.STATE_FAILED -> NeutralCheckBox.STATE_UNCHECKED
            else -> NeutralCheckBox.STATE_NEUTRAL
        })

        itemView.setOnClickListener {
            //todo open this class
        }
    }
}