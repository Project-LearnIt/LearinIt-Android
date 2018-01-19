package manwithandroid.learnit.gui

import android.content.Context
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_progress_indicator.view.*

import manwithandroid.learnit.R
import manwithandroid.learnit.utilities.UiUtilities

class ProgressIndicatorView : LinearLayout {

    private val halfSize = 100 / 2

    private var progress = 0
    private var factor = 3.0f

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ProgressIndicatorView,
                0, 0)

        try {
            progress = a.getInteger(R.styleable.ProgressIndicatorView_progress, progress)
            factor = a.getFloat(R.styleable.ProgressIndicatorView_factor, factor)
        } finally {
            a.recycle()
        }


        initView()
    }

    private fun initView() {
        View.inflate(context, R.layout.view_progress_indicator, this)
        
        //todo change alpha
        POSITIVE_COLOR = ContextCompat.getColor(context, R.color.colorGreen)
        NEGATIVE_COLOR = ContextCompat.getColor(context, R.color.colorRed)

        POSITIVE_BAR_COLOR = UiUtilities.colorLighter(POSITIVE_COLOR, 0.4f)
        NEGATIVE_BAR_COLOR = UiUtilities.colorLighter(NEGATIVE_COLOR, 0.4f)
        PLACEHOLDER_BAR_COLOR = UiUtilities.colorLighter(ContextCompat.getColor(context, R.color.colorPlaceholder), 0.5f)

        //todo check mode
        positiveBar.background.setColorFilter(POSITIVE_BAR_COLOR, PorterDuff.Mode.SRC_IN)
        negativeBar.background.setColorFilter(NEGATIVE_BAR_COLOR, PorterDuff.Mode.SRC_IN)

        setProgress(progress)
    }

    fun setProgress(number: Int) {
        val newLocation = Math.abs(getLocationFor(number))

        setProgressBarSizes(newLocation, halfSize * 2 - newLocation)

        setNumber(number)
    }

    private fun getLocationFor(number: Int) = ((2 * halfSize) / Math.PI) * Math.atan(number.toDouble() / factor) + halfSize

    private fun setNumber(number: Int) {
        if (number == 0) {
            checkView.visibility = View.VISIBLE
            indicatorCircleTextView.visibility = View.GONE

        } else {
            checkView.visibility = View.GONE
            indicatorCircleTextView.visibility = View.VISIBLE

            indicatorCircleTextView.text = String.format("%d", number)
        }
    }

    private fun setProgressBarSizes(PositiveBar: Double, NegativeBar: Double) {
        (negativeBar.layoutParams as LinearLayout.LayoutParams).weight = NegativeBar.toFloat()
        (positiveBar.layoutParams as LinearLayout.LayoutParams).weight = PositiveBar.toFloat()

        positiveBar.background.setColorFilter(if (PositiveBar >= NegativeBar) POSITIVE_BAR_COLOR else PLACEHOLDER_BAR_COLOR, PorterDuff.Mode.SRC_IN)
        negativeBar.background.setColorFilter(if (NegativeBar > PositiveBar) NEGATIVE_BAR_COLOR else PLACEHOLDER_BAR_COLOR, PorterDuff.Mode.SRC_IN)

        indicatorCircleHolder.background.setColorFilter(if (PositiveBar >= NegativeBar) POSITIVE_COLOR else NEGATIVE_COLOR, PorterDuff.Mode.SRC_IN)
    }

    companion object {

        private var POSITIVE_COLOR = -1
        private var NEGATIVE_COLOR = -1

        private var POSITIVE_BAR_COLOR = -1
        private var NEGATIVE_BAR_COLOR = -1
        private var PLACEHOLDER_BAR_COLOR = -1
    }
}
