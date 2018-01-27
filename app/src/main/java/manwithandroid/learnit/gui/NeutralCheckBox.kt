package manwithandroid.learnit.gui

import android.content.Context
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_neutral_checkbox.view.*
import manwithandroid.learnit.R

/**
 * Created by roi on 1/27/18.
 */
class NeutralCheckBox : FrameLayout {

    companion object {
        const val STATE_UNCHECKED = -1
        const val STATE_NEUTRAL = 0
        const val STATE_CHECKED = 1

        private var UNCHECKED_COLOR = 0
        private var NEUTRAL_COLOR = 0
        private var CHECKED_COLOR = 0
    }

    private var state = STATE_NEUTRAL

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.NeutralCheckBox,
                0, 0)

        try {
            state = a.getInteger(R.styleable.NeutralCheckBox_state, state)
        } finally {
            a.recycle()
        }

        initView()
    }

    private fun initView() {
        View.inflate(context, R.layout.view_neutral_checkbox, this)

        UNCHECKED_COLOR = ContextCompat.getColor(context, R.color.colorRed)
        NEUTRAL_COLOR = ContextCompat.getColor(context, R.color.colorPlaceholder)
        CHECKED_COLOR = ContextCompat.getColor(context, R.color.colorGreen)

        setState(state)
    }

    fun setState(state: Int) {
        frameView.background.setColorFilter(when (state) {
            STATE_CHECKED -> CHECKED_COLOR
            STATE_UNCHECKED -> UNCHECKED_COLOR
            else -> NEUTRAL_COLOR
        }, PorterDuff.Mode.SRC_IN)

        checkView.visibility = if (state == STATE_CHECKED) View.VISIBLE else View.GONE
        closeView.visibility = if (state == STATE_UNCHECKED) View.VISIBLE else View.GONE
    }
}