package com.noser.heuteabstimmung.persitence.db.impl

import com.noser.heuteabstimmung.core.model.SourceDetails
import com.noser.heuteabstimmung.core.ports.persistence.DataSourceDetailsPersistencePort
import java.net.URI
import javax.inject.Singleton

@Singleton
class DataSourceDetailsPersistencePortImpl : DataSourceDetailsPersistencePort {
    override fun getSourceDetails(): List<SourceDetails> {
        return listOf(SourceDetails("srg", URI("https://developer.srgssr.ch"), "any"))
    }
}