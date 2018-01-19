package manwithandroid.learnit.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import manwithandroid.learnit.R
import manwithandroid.learnit.adapters.holders.ClassHolder
import manwithandroid.learnit.models.Class

/**
 * Created by Roi Amiel on 17/01/2018.
 */
class ClassesAdapter(val activity: Activity, private var classesList: List<Class>? = null) : RecyclerView.Adapter<ClassHolder>() {

    override fun getItemCount() = if (classesList != null) classesList?.size!! else 0

    override fun onBindViewHolder(holder: ClassHolder?, position: Int) {
        holder?.bind(classesList!![position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            ClassHolder(activity.layoutInflater.inflate(R.layout.item_class, parent, false))


    fun updateClassesList(classesList: List<Class>?) {
        this.classesList = classesList

        notifyDataSetChanged()
    }
}
