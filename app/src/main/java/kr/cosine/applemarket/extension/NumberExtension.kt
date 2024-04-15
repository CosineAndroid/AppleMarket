package kr.cosine.applemarket.extension

import java.text.DecimalFormat

private val decimalFormat = DecimalFormat("#,###")

fun Int.applyComma(): String = decimalFormat.format(this)