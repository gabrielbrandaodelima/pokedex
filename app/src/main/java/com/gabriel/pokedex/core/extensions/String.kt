package com.gabriel.pokedex.core.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.TextAppearanceSpan
import androidx.core.content.ContextCompat
import com.gabriel.pokedex.R

fun String.Companion.empty() = ""

fun String.capitalizeWords(): String {
    return if (isNotEmpty() && this[0].isLowerCase()) {

        val words = this.split(" ", "-")
        val capWords = arrayListOf<String>()
        for (word in words) {
            if (word.isNotEmpty())
                capWords.add(word.substring(0, 1).toUpperCase() + word.substring(1))
        }
        var final = String.empty()
        capWords.forEach {
            final += it.plus(" ")
        }
        final
    } else this
}

/**
 * Returns matched color text within this [string],
 * starting from the beginning.
 * @param mContext context.
 * @param textToMatchCase [string] to match within [source] text. {String}
 * @param ignoreCase `true` to ignore character case when matching a string. By default `true`.
 * @return [SpannableString] with matched char sequence text coloured of [string] or an empty [spannableString], if nothing matched.
 */
fun String?.getSpannableMatchCase(mContext: Context,
                                  textToMatchCase: String?,
                                  ignoreCase: Boolean = true): SpannableString {
    return this?.let { source ->
        textToMatchCase?.let { destiny ->
            val spannable = SpannableString(this)
            val matchCaseIndexStart = source.indexOf(destiny, 0, ignoreCase)
            val matchCaseIndexEnd = matchCaseIndexStart + destiny.length
            if (matchCaseIndexStart != -1) {
                val color = ColorStateList(
                    arrayOf(intArrayOf()),
                    intArrayOf(ContextCompat.getColor(mContext, R.color.black))
                )
                val boldSpan = TextAppearanceSpan(null, Typeface.BOLD, -1, color, null)
                spannable.setSpan(boldSpan, matchCaseIndexStart, matchCaseIndexEnd, Spanned.SPAN_INTERMEDIATE)

            }
            spannable
        }
    } ?: SpannableString(String.empty())
}