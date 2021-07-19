package com.noser.heuteabstimmung.persistence.db.impl

import com.noser.heuteabstimmung.core.model.DataSelector
import com.noser.heuteabstimmung.core.ports.persistence.DataSelectorPersistencePort
import com.noser.heuteabstimmung.persistence.db.impl.mapper.DataSelectorMapper
import com.noser.heuteabstimmung.persistence.db.impl.repositories.DataSelectorRepository
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class DataSelectorPersistencePortImpl(
    private val dataSelectorRepository: DataSelectorRepository,
    private val dataSelectorMapper: DataSelectorMapper) : DataSelectorPersistencePort{

    @Transactional
    override fun findDataSelector(query: String): List<DataSelector> {
        val listDataSelectors = dataSelectorRepository.listDataSelectors("$query%")

        return listDataSelectors.map { entity -> dataSelectorMapper.fromEntity(entity) }
    }

}