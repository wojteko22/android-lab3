package pl.edu.pwr.wojciech.okonski.lab3.lab3

import android.app.DialogFragment
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), OnColorSelectionListener {
    private val sensorManager: SensorManager by lazy {
        getSystemService(SENSOR_SERVICE) as SensorManager
    }
    private val accelerometer: Sensor by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(shapeView, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(shapeView)
    }

    override fun onCreateOptionsMenu(menu: Menu) = toTrue {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> toTrue {
            val dialog: DialogFragment = ColorDialog()
            dialog.show(fragmentManager, "ColorDialogFragment")
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onColorSelection(color: Int) {
        shapeView.changeShapeColor(color)
    }
}