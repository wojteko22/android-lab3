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
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            shapeX -= event.values[0].toInt()
            shapeY += event.values[1].toInt()
        }
    }

    override fun onDraw(canvas: Canvas) {
        shape.setBounds(shapeX, shapeY, shapeX + shapeWidth, shapeY + shapeHeight)
        shape.draw(canvas)
        invalidate()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}