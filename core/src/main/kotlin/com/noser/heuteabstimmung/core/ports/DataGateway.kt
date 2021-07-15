package com.noser.heuteabstimmung.core.ports

import com.noser.heuteabstimmung.core.model.SourceDetails
import com.noser.heuteabstimmung.core.model.VotationLocation

interface DataGateway {
    fun getAllLocations(sourceDetails: SourceDetails): List<VotationLocation>
    fun getLocationByExtId(extId: String, sourceDetails: SourceDetails): VotationLocation?
}