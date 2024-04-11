package kr.cosine.applemarket.extension

import android.content.Context
import kr.cosine.applemarket.R

fun Context.getFormattedPrice(price: Int): String {
    return getString(R.string.price_format, price.applyComma())
}