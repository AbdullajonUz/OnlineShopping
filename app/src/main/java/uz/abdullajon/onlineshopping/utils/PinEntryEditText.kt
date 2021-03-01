package uz.abdullajon.onlineshopping.utils

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText


class PinEntryEditText : AppCompatEditText {

    var mSpace = -1f //24 dp by default

    var mCharSize = 0f
    var mNumChars = 6
    var mLineSpacing = 8f

    private var mClickListener: OnClickListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        setBackgroundResource(0)
        val multi = context.resources.displayMetrics.density
        mSpace *= multi
        mLineSpacing *= multi
        val mMaxLength = attrs.getAttributeIntValue("PinEntryEditText", "maxLength", 6)
        mNumChars = mMaxLength

        super.setOnClickListener { v ->
            setSelection(text!!.length)
            mClickListener?.onClick(v)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        //  super.onDraw(canvas)

        val availableWidth = width - paddingRight - paddingLeft
        mCharSize = if (mSpace < 0) {
            (availableWidth / (mNumChars * 2 - 1)).toFloat()
        } else {
            (availableWidth - mSpace * (mNumChars - 1)) / mNumChars
        }

        var startX = paddingLeft
        val bottom = height - paddingBottom

        val pinText = text
        val textLength = text!!.length
        val textWidths = FloatArray(textLength)
        paint.getTextWidths(text, 0, textLength, textWidths)

        for (i in 0 until mNumChars) {
            canvas!!.drawLine(
                startX.toFloat(), bottom.toFloat(), startX + mCharSize, bottom.toFloat(), paint
            )
            if (text!!.length > i) {
                val middle = startX + mCharSize / 2
                canvas.drawText(
                    pinText.toString(),
                    i,
                    i + 1,
                    middle - textWidths[0] / 2,
                    bottom - mLineSpacing,
                    paint
                )
            }
            startX += if (mSpace < 0) {
                (mCharSize * 2).toInt()
            } else {
                (mCharSize + mSpace.toInt()).toInt()
            }
        }

    }

    override fun setOnClickListener(l: OnClickListener?) {
        mClickListener = l
    }
}