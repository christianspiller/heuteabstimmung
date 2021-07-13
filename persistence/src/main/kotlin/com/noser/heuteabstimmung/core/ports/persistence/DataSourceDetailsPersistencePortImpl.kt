package com.noser.heuteabstimmung.core.ports.persistence

import com.noser.heuteabstimmung.core.model.SourceDetails
import java.net.URI
import javax.inject.Singleton

@Singleton
class DataSourceDetailsPersistencePortImpl : DataSourceDetailsPersistencePort {
    override fun getSourceDetails(): List<SourceDetails> {
        return listOf(SourceDetails("srg", URI("https://developer.srgssr.ch"), "any"))
    }
}