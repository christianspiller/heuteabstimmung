package com.noser.heuteabstimmung.core.model

import java.util.*


class LocalizedString (val de: String, val fr: String, val it: String, val en: String?){

    fun get(locale: Locale): String =
        when (locale) {
            Locale.ENGLISH -> getOrElse(en, de, fr, it)
            Locale.ITALIAN -> getOrElse(it, en, fr, de)
            Locale.FRENCH -> getOrElse(fr, en, it, de)
            Locale.GERMAN -> getOrElse(de, en, fr, it)
            else -> {
                getOrElse(en, de, fr, it)
            }
        }

    internal fun getOrElse(prio1: String?, prio2: String?, prio3: String?, prio4: String?) : String {
        when {
            prio1 != null -> {
                return prio1
            }
            prio2 != null -> {
                return prio2
            }
            prio3 != null -> {
                return prio3
            }
            prio4 != null -> {
                return prio4
            }
            else -> return "null"
        }
    }
}
