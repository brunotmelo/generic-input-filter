package io.btmg.genericinputfilter

import android.text.InputFilter
import android.text.Spanned

class GenericInputFilter(private val mask: String) : InputFilter {

  companion object {

    fun cpf(): GenericInputFilter {
      return GenericInputFilter("###.###.###-##")
    }

    fun buildBtc(): GenericInputFilter {
      return GenericInputFilter("##,########")
    }
  }

  override fun filter(
    source: CharSequence?,
    start: Int,
    end: Int,
    dest: Spanned?,
    dStart: Int,
    dEnd: Int
  ): CharSequence {
    if (source != null && source.isNotEmpty()) {
      val previous = source.filter { it.isDigit() }
      return applyMask(previous, dStart, dEnd)
    } else {
      return ""
    }
  }

  private fun applyMask(previous: CharSequence, dStart: Int, dEnd: Int): CharSequence {
    var result = ""
    var maskAdditions = 0
    for (i in dStart until dEnd + previous.length) {
      val curChar = i
      if (curChar < mask.length) {
        if (mask[curChar + maskAdditions] != '#') {
          result += mask[curChar + maskAdditions]
          maskAdditions++
        }
        result += previous[curChar - dStart]
      }
    }
    return result
  }

}