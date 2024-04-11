package kr.cosine.applemarket

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kr.cosine.applemarket.data.IntentKey
import kr.cosine.applemarket.data.Product
import kr.cosine.applemarket.databinding.ActivityProductBinding
import kr.cosine.applemarket.extension.getFormattedPrice

class ProductActivity : AppCompatActivity() {

    private val binding by lazy { ActivityProductBinding.inflate(layoutInflater) }

    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        product = intent.getParcelableExtra(IntentKey.PRODUCT)!! // 다른 방법으로 데이터 동기화

        setContentView(binding.root)
        init()
    }

    private fun init() = with(binding) {
        productImageView.setImageResource(product.imageDrawableId)
        sellerTextView.text = product.seller
        addressTextView.text = product.address
        titleTextView.text = product.title
        descriptionTextView.text = product.description
        switchLikeImage()
        priceTextView.text = getFormattedPrice(product.price)
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
        val imageDrawableId = if (product.isLiked) R.drawable.filled_like else R.drawable.empty_like
        binding.likeImageButton.setImageResource(imageDrawableId)
    }
}