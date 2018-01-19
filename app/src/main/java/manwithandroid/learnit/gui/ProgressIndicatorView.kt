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
    
    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
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

        setPositiveProgress(60)
        setNumber(2)
    }

    fun setNumber(Number: Int) {
        indicatorCircleTextView.text = String.format("%d", Number)
    }

    fun setPositiveProgress(Value: Int) {
        setProgressBarSizes(Value, 100 - Value)

    }

    fun setNegativeProgress(Value: Int) {
        setProgressBarSizes(100 - Value, Value)
    }

    private fun setProgressBarSizes(PositiveBar: Int, NegativeBar: Int) {
        (negativeBar.layoutParams as LinearLayout.LayoutParams).weight = NegativeBar.toFloat()
        (positiveBar.layoutParams as LinearLayout.LayoutParams).weight = PositiveBar.toFloat()

        positiveBar.background.setColorFilter(if (PositiveBar >= NegativeBar) POSITIVE_BAR_COLOR else PLACEHOLDER_BAR_COLOR, PorterDuff.Mode.SRC_IN)
        negativeBar.background.setColorFilter(if (NegativeBar > PositiveBar) NEGATIVE_BAR_COLOR else PLACEHOLDER_BAR_COLOR, PorterDuff.Mode.SRC_IN)

        indicatorCircleTextView.background.setColorFilter(if (PositiveBar >= NegativeBar) POSITIVE_COLOR else NEGATIVE_COLOR, PorterDuff.Mode.SRC_IN)
    }

    companion object {

        private var POSITIVE_COLOR = -1
        private var NEGATIVE_COLOR = -1

        private var POSITIVE_BAR_COLOR = -1
        private var NEGATIVE_BAR_COLOR = -1
        private var PLACEHOLDER_BAR_COLOR = -1
    }
}
