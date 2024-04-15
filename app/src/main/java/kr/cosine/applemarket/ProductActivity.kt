package kr.cosine.applemarket

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kr.cosine.applemarket.data.IntentKey
import kr.cosine.applemarket.data.Product
import kr.cosine.applemarket.databinding.ActivityProductBinding
import kr.cosine.applemarket.extension.getFormattedPrice
import kr.cosine.applemarket.registry.ProductRegistry

class ProductActivity : AppCompatActivity() {

    private val binding by lazy { ActivityProductBinding.inflate(layoutInflater) }

    private val productRegistry = ProductRegistry.getInstance()

    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra(IntentKey.PRODUCT_ID, -1)
        val product = productRegistry.findProductById(id) ?: run {
            finish()
            Toast.makeText(this, "상품을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        this.product = product

        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        productImageView.setImageResource(product.imageDrawableId)
        sellerTextView.text = product.seller
        addressTextView.text = product.address
        titleTextView.text = product.title
        descriptionTextView.text = product.description
        switchLikeImage()
        priceTextView.text = getFormattedPrice(product.price)
    }

    fun onBackspaceClick(view: View) {
        finish()
    }

    fun switchLike(view: View) {
        val newLiked = !product.isLiked
        product.isLiked = newLiked
        switchLikeImage()
        if (newLiked) {
            Snackbar.make(view, getString(R.string.like_message), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun switchLikeImage() {
        binding.likeImageButton.setImageResource(product.likeDrawableId)
    }
}