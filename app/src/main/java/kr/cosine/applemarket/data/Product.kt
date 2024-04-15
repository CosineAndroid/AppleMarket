package kr.cosine.applemarket.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.cosine.applemarket.R

@Parcelize
class Product private constructor(
    val id: Int,
    val imageDrawableId: Int,
    val title: String,
    val description: String,
    val seller: String,
    val price: Int,
    val address: String,
    val likeCount: Int,
    val chatCount: Int,
    var isLiked: Boolean = false
) : Parcelable {

    val likeDrawableId get() = if (isLiked) R.drawable.filled_like else R.drawable.empty_like

    val totalLikeCount get() = likeCount + if (isLiked) 1 else 0

    companion object {
        private var id = 0

        fun of(
            imageDrawableId: Int,
            title: String,
            description: String,
            seller: String,
            price: Int,
            address: String,
            likeCount: Int,
            chatCount: Int,
        ): Product {
            return Product(id++, imageDrawableId, title, description, seller, price, address, likeCount, chatCount)
        }
    }
}