package com.noser.heuteabstimmung.core.usecase

import com.noser.heuteabstimmung.core.model.VotationLocationDataSelector

interface LookupDataUseCase {
    fun findLocationSelectors(query: String, source: String?): List<VotationLocationDataSelector>
}