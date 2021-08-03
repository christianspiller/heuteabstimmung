package com.noser.heuteabstimmung.persistence.db.impl

import com.noser.heuteabstimmung.core.model.VotationLocationDataSelector
import com.noser.heuteabstimmung.core.ports.persistence.DataSelectorPersistencePort
import com.noser.heuteabstimmung.persistence.db.impl.mapper.VotationLocationDataSelectorMapper
import com.noser.heuteabstimmung.persistence.db.impl.repositories.DataSelectorRepository
import com.noser.heuteabstimmung.persistence.db.impl.repositories.VotationLocationDataSelectorRepository
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class DataSelectorPersistencePortImpl(
    private val dataSelectorRepository: DataSelectorRepository,
    private val votationLocationDataSelectorRepository: VotationLocationDataSelectorRepository,
    private val votationLocationDataSelectorMapper: VotationLocationDataSelectorMapper) : DataSelectorPersistencePort{

    @Transactional
    override fun findVotationLocationDataSelectors(query: String): List<VotationLocationDataSelector> {
        val listDataSelectors = dataSelectorRepository.listDataSelectors("$query%")

        return listDataSelectors
            .flatMap { dataSelectorEntity -> votationLocationDataSelectorRepository.findByDataSelectorEntity(dataSelectorEntity) }
            .map { entity -> votationLocationDataSelectorMapper.fromEntity(entity) }
    }

}