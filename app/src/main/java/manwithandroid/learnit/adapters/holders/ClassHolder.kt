package manwithandroid.learnit.adapters.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import manwithandroid.learnit.R
import manwithandroid.learnit.gui.ProgressIndicatorView
import manwithandroid.learnit.models.Class

/**
 * Created by Roi Amiel on 17/01/2018.
 */
class ClassHolder(view: View) : RecyclerView.ViewHolder(view) {

    /* Views */
    private var classNameTextView: TextView = itemView.findViewById(R.id.classNameTextView)
    private var classImageView: ImageView = itemView.findViewById(R.id.classImageView)
    private var progressIndicator: ProgressIndicatorView = itemView.findViewById(R.id.progressIndicator)

    fun bind(classObject: Class) {
        classNameTextView.text = classObject.name

        progressIndicator.setPositiveProgress(50)
        progressIndicator.setNumber(0)

        itemView.setOnClickListener {
            //todo open this class
        }
    }
}