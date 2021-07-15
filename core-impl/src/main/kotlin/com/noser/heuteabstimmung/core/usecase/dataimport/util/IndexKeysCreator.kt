package com.noser.heuteabstimmung.core.usecase.dataimport.util

import java.util.function.Consumer
import javax.inject.Singleton

@Singleton
class IndexKeysCreator {
    
    fun createIndexKeys(vararg phrases: String): Set<String> {
        val keys = mutableSetOf<String>()
        phrases.forEach { phrase -> getKeys(phrase, keys) }
        return keys
    }

    private fun getKeys(phrase: String, keys: MutableSet<String>) {
        phrase.trim()
            .replace("(", "")
            .replace(")", "")
            .split(" ")
            .forEach(Consumer { key -> keys.add(key) })

    }
}