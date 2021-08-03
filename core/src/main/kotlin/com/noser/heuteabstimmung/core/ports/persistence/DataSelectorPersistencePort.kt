package com.noser.heuteabstimmung.core.ports.persistence

import com.noser.heuteabstimmung.core.model.VotationLocationDataSelector

interface DataSelectorPersistencePort {
    fun findVotationLocationDataSelectors(query: String): List<VotationLocationDataSelector>
}