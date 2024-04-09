package kr.cosine.applemarket

import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kr.cosine.applemarket.adapter.ProductAdapter
import kr.cosine.applemarket.databinding.ActivityMainBinding
import kr.cosine.applemarket.registry.ProductRegistry

class MainActivity : AppCompatActivity() {

    private companion object {
        const val NOTIFICATION_CHANNEL_ID = "AppleMarket"
        const val NOTIFICATION_CHANNEL_NAME = "사과마켓"
        const val NOTIFICATION_ID = 1
    }

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val productRegistry by lazy { ProductRegistry.getInstance() }

    private val backPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitDialog()
            }
        }
    }

    private val notificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        initBackPressedCallback()
        initNotificationChannel()
        initNoticiationButton()
        initRecyclerView()
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    private fun initNotificationChannel() {
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
            setSound(uri, audioAttributes)
        }
        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun initNoticiationButton() {
        binding.bellImageView.setOnClickListener {
            showNotification()
        }
    }

    private fun initRecyclerView() = with(binding.productRecyclerView) {
        val dividerItemDecoration = DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL)
        addItemDecoration(dividerItemDecoration)
        adapter = ProductAdapter(productRegistry.products) {

        }
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun showNotification() {
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setWhen(System.currentTimeMillis())
            setContentTitle(getString(R.string.notification_title))
            setContentText(getString(R.string.notification_description))
        }.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this).apply {
            setCancelable(false)
            setIcon(R.drawable.chat)
            setTitle(getString(R.string.exit_title))
            setMessage(getString(R.string.exit_description))
            setNegativeButton(getString(R.string.exit_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton(getString(R.string.exit_confirm)) { _, _ ->
                finish()
            }
        }.show()
    }
}