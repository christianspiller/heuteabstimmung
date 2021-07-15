package com.noser.heuteabstimmung.core.ports.persistence

import com.noser.heuteabstimmung.core.model.DataSelector

interface DataSelectorPersistencePort {
    fun saveDataSelector(dataSelector: DataSelector, indexKeys: Set<String>)
}