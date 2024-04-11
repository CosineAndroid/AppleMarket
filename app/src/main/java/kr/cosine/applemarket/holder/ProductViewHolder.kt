package kr.cosine.applemarket.holder

import androidx.recyclerview.widget.RecyclerView
import kr.cosine.applemarket.databinding.ItemProductBinding

class ProductViewHolder(
    binding: ItemProductBinding
) : RecyclerView.ViewHolder(binding.root) {

    val previewImageView = binding.previewImageView

    val titleTextView = binding.titleTextView

    val addressTextView = binding.addressTextView

    val priceTextView = binding.priceTextView

    val chatTextView = binding.chatTextView

    val likeImageView = binding.likeImageView

    val likeTextView = binding.likeTextView
}