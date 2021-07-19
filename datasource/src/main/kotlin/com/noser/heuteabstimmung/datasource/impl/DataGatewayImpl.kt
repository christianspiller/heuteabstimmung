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
                counter.toString(),
                "${counter} de",
                "${counter} short",
                DivisionLevel.Canton
            )

            locations.add(votationLocation)
        }

        return locations

    }

    override fun getLocationByExtId(extId: String, sourceDetails: SourceDetails): VotationLocation? {
        return VotationLocation(
            extId,
            "${extId} de",
            "${extId} short",
            DivisionLevel.Canton
        )
    }
}