package pl.edu.pwr.wojciech.okonski.lab3.lab3

import android.app.DialogFragment
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), OnColorSelectionListener, OnPointGainedListener, OnSizeChangedListener {
    private var started: Boolean = false
    private val sensorManager: SensorManager by lazy {
        getSystemService(SENSOR_SERVICE) as SensorManager
    }
    private val accelerometer: Sensor by lazy {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }
    private lateinit var shareActionProvider: ShareActionProvider
    private val sendIntent
        get() = with(Intent(Intent.ACTION_SEND)) {
            putExtra(Intent.EXTRA_TEXT, shareMessage)
            type = "text/plain"
            this
        }
    private val shareMessage: String
        get() {
            val sharingMessageTemplate = getString(R.string.shareMessage)
            return String.format(sharingMessageTemplate, points)
        }
    private val shapeColorDialog by lazy { ColorDialog(R.string.pick_a_shape_color) }
    private val backGroundColorDialog by lazy { ColorDialog(R.string.pick_a_background_color) }
    private var points = 0
    private val time = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        btnStart.setOnClickListener { startGame() }
    }

    private fun startGame() {
        btnStart.isEnabled = false
        timer.start()
        started = true
    }

    val timer = object : CountDownTimer(time * 1000L, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            tvTimer.text = (millisUntilFinished / 1000).toString()
        }

        override fun onFinish() {
            started = false
            tvTimer.text = time.toString()
            ResultDialog(points).show(fragmentManager, "ResultDialogFragment")
            points = 0
            displayPoints()
            btnStart.isEnabled = true
        }
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
        prepareSharing(menu)
    }

    private fun prepareSharing(menu: Menu) {
        val shareItem = menu.findItem(R.id.share)
        shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider
        updateShareIntent()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_shape_colors -> toTrue {
            shapeColorDialog.show(fragmentManager, "ShapeColorDialogFragment")
        }
        R.id.action_background_colors -> toTrue {
            backGroundColorDialog.show(fragmentManager, "BackGroundColorDialogFragment")
        }
        R.id.action_size -> toTrue {
            SizeDialog(shapeView.appleSize.toFloat() / shapeView.shapeSize).show(fragmentManager, "SizeDialogFragment")
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onColorSelection(color: Int, dialog: DialogFragment) = when (dialog) {
        shapeColorDialog -> shapeView.setShapeColor(color)
        backGroundColorDialog -> shapeView.setBackgroundColor(color)
        else -> Unit
    }

    override fun onPointGained() {
        if (started) {
            points++
            displayPoints()
            updateShareIntent()
        }
    }

    private fun displayPoints() {
        tvResult.text = points.toString()
    }

    private fun updateShareIntent() {
        shareActionProvider.setShareIntent(sendIntent)
    }

    override fun onSizeChanged(sizePercent: Float) {
        shapeView.appleSize = (sizePercent * shapeView.shapeSize).toInt()
        shapeView.deployApple()
    }
}