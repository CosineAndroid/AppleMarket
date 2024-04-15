package kr.cosine.applemarket.extension

import java.text.DecimalFormat

private val decimalFormat = DecimalFormat("#,##0")

fun Int.applyComma(): String = decimalFormat.format(this)