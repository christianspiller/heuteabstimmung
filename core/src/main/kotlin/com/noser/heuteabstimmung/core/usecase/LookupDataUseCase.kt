package com.noser.heuteabstimmung.core.usecase

import com.noser.heuteabstimmung.core.model.DataSelector

interface LookupDataUseCase {
    fun findLocationSelectors(query: String, source: String?): List<DataSelector>
}