package kr.cosine.applemarket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.cosine.applemarket.R
import kr.cosine.applemarket.data.Product
import kr.cosine.applemarket.databinding.ItemProductBinding
import kr.cosine.applemarket.extension.applyComma
import kr.cosine.applemarket.holder.ProductViewHolder

class ProductAdapter(
    private val products: List<Product>
) : RecyclerView.Adapter<ProductViewHolder>() {

    private lateinit var parrentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        parrentContext = parent.context
        val layoutInflater = LayoutInflater.from(parrentContext)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder.from(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = with(holder) {
        val product = products[position]
        previewImageView.setImageResource(product.imageDrawableId)
        titleTextView.text = product.title
        addressTextView.text = product.address
        priceTextView.text = parrentContext.getString(R.string.price_format, product.price.applyComma())
        chatTextView.text = product.chatCount.applyComma()
        likeTextView.text = product.likeCount.applyComma() // 하트 빨간색
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = products.size
}