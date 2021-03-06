package com.noser.heuteabstimmung.core.usecase

import com.noser.heuteabstimmung.core.model.SourceDetails
import com.noser.heuteabstimmung.core.model.VotationLocation

interface ImportLocationUseCase {
    fun importLocation(votationLocation: VotationLocation, sourceDetails: SourceDetails)
}