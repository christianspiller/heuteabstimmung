package com.noser.heuteabstimmung.datasource.impl

import com.noser.heuteabstimmung.core.model.*
import com.noser.heuteabstimmung.core.ports.DataGateway

import javax.inject.Singleton

@Singleton
class DataGatewayImpl : DataGateway {
    override fun getAllLocations(sourceDetails: SourceDetails): List<VotationLocation> {
        val locations = mutableListOf<VotationLocation>()

        for (counter in 0..10) {
            val votationLocation = VotationLocation(
                0,
                counter.toString(),
                LocalizedString("${counter}_de", "${counter}_fr", "${counter}_it", "${counter}_en"),
                VotationLocationLevel.State,
                null
            )

            locations.add(votationLocation)
        }

        return locations

    }

    override fun getLocationByExtId(extId: String, sourceDetails: SourceDetails): VotationLocation? {
        return VotationLocation(
            0,
            extId,
            LocalizedString("${extId}_de", "${extId}_fr", "${extId}_it", "${extId}_en"),
            VotationLocationLevel.State,
            null
        )
    }
}