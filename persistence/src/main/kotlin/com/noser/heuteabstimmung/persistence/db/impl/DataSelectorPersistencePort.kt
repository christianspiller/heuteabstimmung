package com.noser.heuteabstimmung.persistence.db.impl

import com.noser.heuteabstimmung.core.model.DataSelector
import com.noser.heuteabstimmung.core.ports.persistence.DataSelectorPersistencePort
import com.noser.heuteabstimmung.persistence.db.impl.repositories.DataSelectorRepository
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class DataSelectorPersistencePort(
    private val dataSelectorRepository: DataSelectorRepository) : DataSelectorPersistencePort{

    @Transactional
    override fun findDataSelector(query: String): List<DataSelector> {
        return emptyList()
    }

}