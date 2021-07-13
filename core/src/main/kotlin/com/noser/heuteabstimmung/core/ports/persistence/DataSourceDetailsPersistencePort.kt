package com.noser.heuteabstimmung.core.ports.persistence

import com.noser.heuteabstimmung.core.model.SourceDetails

interface DataSourceDetailsPersistencePort {
    fun getSourceDetails(): List<SourceDetails>
}