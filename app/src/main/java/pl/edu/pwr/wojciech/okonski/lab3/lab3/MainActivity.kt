package pl.edu.pwr.wojciech.okonski.lab3.lab3

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private val sensorManager: SensorManager by lazy {
        getSystemService(SENSOR_SERVICE) as SensorManager
    }
    private val accelerometer: Sensor by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }
    private val animatedView: AnimatedView by lazy { AnimatedView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val toolbar = findViewById(R.id.toolbar) as Toolbar
//        setSupportActionBar(toolbar)
        setContentView(animatedView)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(animatedView, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(animatedView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    inner class AnimatedView(context: Context) : ImageView(context), SensorEventListener {
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
}
