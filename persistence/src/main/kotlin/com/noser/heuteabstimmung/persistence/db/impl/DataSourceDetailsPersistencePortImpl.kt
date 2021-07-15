package com.noser.heuteabstimmung.persistence.db.impl

import com.noser.heuteabstimmung.core.model.DataType
import com.noser.heuteabstimmung.core.model.SourceDetails
import com.noser.heuteabstimmung.core.ports.persistence.DataSourceDetailsPersistencePort
import java.net.URI
import javax.inject.Singleton

@Singleton
class DataSourceDetailsPersistencePortImpl : DataSourceDetailsPersistencePort {
    override fun getSourceDetails(dataType: DataType): List<SourceDetails> {
        return listOf(SourceDetails("srg", URI("https://developer.srgssr.ch"), true, "any"))
    }
}