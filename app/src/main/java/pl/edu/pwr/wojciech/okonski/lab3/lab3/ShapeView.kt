package pl.edu.pwr.wojciech.okonski.lab3.lab3

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.AttributeSet
import android.widget.ImageView
import pl.edu.pwr.wojciech.okonski.lab3.lab3.listeners.OnPointGainedListener
import java.util.*

class ShapeView(context: Context, attrs: AttributeSet?) : ImageView(context, attrs), SensorEventListener {
    private val shape = ShapeDrawable(OvalShape())
    private val apple = ShapeDrawable()
    private val generator = Random()
    private val listener: OnPointGainedListener =
            try {
                context as OnPointGainedListener
            } catch (e: ClassCastException) {
                throw ClassCastException(context.toString() + " must implement OnPointGainedListener")
            }

    private var shapeX: Int = 0
    private var shapeY: Int = 0

    val shapeSize = 100
    var appleSize = 30

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setShapeColor(Color.GREEN)
        apple.color = Color.BLACK
        deployApple()
    }

    fun setShapeColor(color: Int) {
        shape.color = color
    }

    override fun onDraw(canvas: Canvas) {
        apple.draw(canvas)
        shape.setBounds(shapeX, shapeY, shapeX + shapeSize, shapeY + shapeSize)
        shape.draw(canvas)
        invalidate()
    }

    override fun onSensorChanged(event: SensorEvent) {
        updateXIfNecessary(event)
        updateYIfNecessary(event)
        if (shape.bounds.contains(apple.bounds)) {
            listener.onPointGained()
            deployApple()
        }
    }

    private fun updateXIfNecessary(event: SensorEvent) {
        val xChange = event.values[0].toInt()
        val maxX = measuredWidth - shapeSize
        shapeX = when {
            xChange >= 0 ->
                if (shapeX > 0)
                    shapeX - squareWithSign(xChange)
                else 0
            else ->
                if (shapeX < maxX)
                    shapeX - squareWithSign(xChange)
                else maxX
        }
    }

    private fun updateYIfNecessary(event: SensorEvent) {
        val yChange = event.values[1].toInt()
        val maxY = measuredHeight - shapeSize
        shapeY = when {
            yChange <= 0 ->
                if (shapeY > 0)
                    shapeY + squareWithSign(yChange)
                else 0
            else ->
                if (shapeY < maxY)
                    shapeY + squareWithSign(yChange)
                else maxY
        }
    }

    private fun squareWithSign(number: Int) = number * Math.abs(number)

    fun deployApple() {
        val x = generator.nextInt(measuredWidth - appleSize)
        val y = generator.nextInt(measuredHeight - appleSize)
        apple.setBounds(x, y, x + appleSize, y + appleSize)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}