package com.noser.heuteabstimmung.core.usecase.datalookup.impl

import com.noser.heuteabstimmung.core.model.VotationLocationDataSelector
import com.noser.heuteabstimmung.core.ports.persistence.DataSelectorPersistencePort
import com.noser.heuteabstimmung.core.usecase.LookupDataUseCase
import javax.inject.Singleton

@Singleton
class LookupDataUseCaseImpl(private val dataSelectorPersistencePort: DataSelectorPersistencePort) : LookupDataUseCase {
    override fun findLocationSelectors(query: String, source: String?): List<VotationLocationDataSelector> {
        return dataSelectorPersistencePort.findVotationLocationDataSelectors(query)
    }
}