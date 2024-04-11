package kr.cosine.applemarket.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Product (
    val imageDrawableId: Int,
    val title: String,
    val description: String,
    val seller: String,
    val price: Int,
    val address: String,
    val likeCount: Int,
    val chatCount: Int,
    var isLiked: Boolean = false
) : Parcelable