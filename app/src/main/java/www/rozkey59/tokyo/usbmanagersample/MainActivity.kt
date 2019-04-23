package www.rozkey59.tokyo.usbmanagersample

import android.animation.ValueAnimator
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_main.view.*
import www.rozkey59.tokyo.usbmanagersample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        setUp()
    }

    private fun setUp() {
        val usbReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
                val batteryStatus = context.registerReceiver(null, filter)

                val status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                if (status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL) {
                    val animation = ValueAnimator.ofFloat(1500f, 0f)
                    animation.interpolator = AccelerateInterpolator()
                    animation.duration = 500L
                    animation.addUpdateListener {
                        binding.image.translationY = animation.animatedValue as Float
                    }
                    animation.start()
                } else {
                    val animation = ValueAnimator.ofFloat(0f, 1500f)
                    animation.interpolator = AccelerateInterpolator()
                    animation.duration = 500L
                    animation.addUpdateListener {
                        binding.image.translationY = animation.animatedValue as Float
                    }
                    animation.start()
                }
            }
        }
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        registerReceiver(usbReceiver, filter)
    }
}
