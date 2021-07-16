package com.noser.heuteabstimmung.core.ports.persistence

import com.noser.heuteabstimmung.core.model.DataSelector

interface DataSelectorPersistencePort {
    fun findDataSelector(query: String): List<DataSelector>
}