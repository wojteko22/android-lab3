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
import java.util.*

class ShapeView(context: Context, attrs: AttributeSet?) : ImageView(context, attrs), SensorEventListener {
    private var shapeX: Int = 0
    private var shapeY: Int = 0
    private val shapeWidth = 100
    private val shapeHeight = 100
    private val appleWidth = 30
    private val appleHeight = 30
    private val shape = ShapeDrawable(OvalShape())
    private val apple = ShapeDrawable()
    private val generator = Random()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setShapeColor(Color.GREEN)
        apple.setColor(Color.BLACK)
        deployApple()
    }

    fun setShapeColor(color: Int) {
        shape.setColor(color)
    }

    private fun ShapeDrawable.setColor(color: Int) {
        this.paint.color = color
    }

    override fun onDraw(canvas: Canvas) {
        shape.setBounds(shapeX, shapeY, shapeX + shapeWidth, shapeY + shapeHeight)
        shape.draw(canvas)
        apple.draw(canvas)
        invalidate()
    }

    override fun onSensorChanged(event: SensorEvent) {
        updateXIfNecessary(event)
        updateYIfNecessary(event)
//        val number = tvResult.text.toString().toInt()
//        tvResult.text = (number + 1).toString()
        if (shape.bounds.contains(apple.bounds))
            deployApple()
    }

    private fun updateXIfNecessary(event: SensorEvent) {
        val xChange = event.values[0].toInt()
        val maxX = measuredWidth - shapeWidth
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
        val maxY = measuredHeight - shapeHeight
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

    private fun deployApple() {
        val x = generator.nextInt(measuredWidth - appleWidth)
        val y = generator.nextInt(measuredHeight - appleHeight)
        apple.setBounds(x, y, x + appleWidth, y + appleHeight)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}