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

class ShapeView(context: Context, attrs: AttributeSet?) : ImageView(context, attrs), SensorEventListener {
    private var shapeX: Int = 0
    private var shapeY: Int = 0
    private val shapeWidth = 100
    private val shapeHeight = 100
    private val shape = ShapeDrawable(OvalShape())

    init {
        shape.paint.color = Color.GREEN
    }

    override fun onSensorChanged(event: SensorEvent) {
        updateXIfNecessary(event)
        updateYIfNecesarry(event)
    }

    private fun updateXIfNecessary(event: SensorEvent) {
        val xChange = event.values[0].toInt()
        if (xChange > 0 && shapeX > 0 || xChange < 0 && shapeX < measuredWidth - shapeWidth)
            shapeX -= xChange * Math.abs(xChange)
    }

    private fun updateYIfNecesarry(event: SensorEvent) {
        val yChange = event.values[1].toInt()
        if (yChange < 0 && shapeY > 0 || yChange > 0 && shapeY < measuredHeight - shapeHeight)
            shapeY += yChange * Math.abs(yChange)
    }

    override fun onDraw(canvas: Canvas) {
        shape.setBounds(shapeX, shapeY, shapeX + shapeWidth, shapeY + shapeHeight)
        shape.draw(canvas)
        invalidate()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}