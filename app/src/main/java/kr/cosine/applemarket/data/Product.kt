package kr.cosine.applemarket.data

class Product private constructor(
    val imageDrawableId: Int,
    val title: String,
    val description: String,
    val seller: String,
    val price: Int,
    val address: String,
    val likeCount: Int,
    val chatCount: Int
) {

    var isLiked = false

    companion object {
        fun of(
            imageDrawableId: Int,
            title: String,
            description: String,
            seller: String,
            price: Int,
            address: String,
            likeCount: Int,
            chatCount: Int
        ) : Product {
            return Product(imageDrawableId, title, description, seller, price, address, likeCount, chatCount)
        }
    }
}