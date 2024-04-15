package kr.cosine.applemarket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.cosine.applemarket.data.Product
import kr.cosine.applemarket.databinding.ItemProductBinding
import kr.cosine.applemarket.extension.applyComma
import kr.cosine.applemarket.extension.getFormattedPrice
import kr.cosine.applemarket.holder.ProductViewHolder

class ProductAdapter(
    private val products: MutableList<Product>,
    private val clickScope: (Int, Product) -> Unit,
    private val longClickScope: (Int, Product) -> Unit
) : RecyclerView.Adapter<ProductViewHolder>() {

    private lateinit var parrentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        parrentContext = parent.context
        val layoutInflater = LayoutInflater.from(parrentContext)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = with(holder) {
        val product = products[position]
        holder.itemView.apply {
            setOnClickListener {
                clickScope(holder.adapterPosition, product)
            }
            setOnLongClickListener {
                longClickScope(holder.adapterPosition, product)
                return@setOnLongClickListener true
            }
        }
        previewImageView.setImageResource(product.imageDrawableId)
        titleTextView.text = product.title
        addressTextView.text = product.address
        priceTextView.text = parrentContext.getFormattedPrice(product.price)
        chatTextView.text = product.chatCount.applyComma()
        likeImageView.setImageResource(product.likeDrawableId)
        likeTextView.text = product.totalLikeCount.applyComma()
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = products.size

    fun deleteProduct(position: Int, product: Product) {
        products.remove(product)
        notifyItemRemoved(position)
        notifyItemChanged(position)
    }
}