package kr.cosine.applemarket

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kr.cosine.applemarket.adapter.ProductAdapter
import kr.cosine.applemarket.data.IntentKey
import kr.cosine.applemarket.data.Product
import kr.cosine.applemarket.databinding.ActivityMainBinding
import kr.cosine.applemarket.listener.ScrollListener
import kr.cosine.applemarket.registry.ProductRegistry

class MainActivity : AppCompatActivity() {

    private companion object {
        const val NOTIFICATION_CHANNEL_ID = "AppleMarket"
        const val NOTIFICATION_CHANNEL_NAME = "사과마켓"
        const val NOTIFICATION_ID = 1
    }

    private val productRegistry = ProductRegistry.getInstance()

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val notificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    private val productRecyclerView by lazy { binding.productRecyclerView }

    private var openedProductPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        initBackPressedCallback()
        initNotificationChannel()
        initNoticiationButton()
        initRecyclerView()
        initScrollButton()
    }

    override fun onResume() {
        super.onResume()
        if (openedProductPosition != -1) {
            productRecyclerView.adapter?.notifyItemChanged(openedProductPosition)
            openedProductPosition = -1
        }
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback(this) {
            showExitDialog()
        }
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

    private fun initRecyclerView() = with(productRecyclerView) {
        val dividerItemDecoration = DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL)
        addItemDecoration(dividerItemDecoration)

        val scrollListener = ScrollListener(binding.scrollButton)
        addOnScrollListener(scrollListener)

        val products = productRegistry.products.toMutableList()
        adapter = ProductAdapter(products, ::startProductActivity, ::showProductDeleteDialog)
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

    private fun startProductActivity(position: Int, product: Product) {
        openedProductPosition = position
        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra(IntentKey.PRODUCT_ID, product.id)
        startActivity(intent)
    }

    private fun showProductDeleteDialog(position: Int, product: Product) {
        showDialog(
            getString(R.string.product_delete_title),
            getString(R.string.product_delete_description)
        ) {
            deleteProduct(position, product)
        }
    }

    private fun deleteProduct(position: Int, product: Product) {
        val productAdapter = productRecyclerView.adapter as? ProductAdapter ?: return
        productAdapter.deleteProduct(position, product)
        productRegistry.removeProduct(product)
    }

    private fun showExitDialog() {
        showDialog(
            getString(R.string.exit_title),
            getString(R.string.exit_description)
        ) {
            finish()
        }
    }

    private fun showDialog(title: String, message: String, positiveScope: () -> Unit) {
        AlertDialog.Builder(this).apply {
            setCancelable(false)
            setIcon(R.drawable.chat)
            setTitle(title)
            setMessage(message)
            setNegativeButton(getString(R.string.exit_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton(getString(R.string.exit_confirm)) { _, _ ->
                positiveScope()
            }
        }.show()
    }

    fun onScrollButtonClick(view: View) {
        productRecyclerView.smoothScrollToPosition(0)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initScrollButton() {
        binding.scrollButton.apply {
            setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> R.drawable.clicked_scroll_button_background to R.drawable.clicked_up_arrow
                    MotionEvent.ACTION_UP -> R.drawable.default_scroll_button_background to R.drawable.default_up_arrow
                    else -> null
                }?.let {
                    setBackgroundResource(it.first)
                    setImageResource(it.second)
                }
                return@setOnTouchListener false
            }
        }
    }
}