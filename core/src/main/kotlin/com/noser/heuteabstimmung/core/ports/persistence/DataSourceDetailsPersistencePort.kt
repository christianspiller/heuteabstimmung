package com.noser.heuteabstimmung.core.ports.persistence

import com.noser.heuteabstimmung.core.model.DataType
import com.noser.heuteabstimmung.core.model.SourceDetails

interface DataSourceDetailsPersistencePort {
    fun getSourceDetails(dataType: DataType): List<SourceDetails>
}