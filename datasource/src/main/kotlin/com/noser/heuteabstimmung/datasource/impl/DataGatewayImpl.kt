package com.noser.heuteabstimmung.datasource.impl

import com.noser.heuteabstimmung.core.model.*
import com.noser.heuteabstimmung.core.ports.DataGateway

import javax.inject.Singleton

@Singleton
class DataGatewayImpl : DataGateway {

    private val locations: List<VotationLocation> = (1..26).map { bfsNumber -> VotationLocation(
        (bfsNumber-1).toString(),
        bfsNumber + 1,
        "${bfsNumber} de",
        "${bfsNumber} short",
        DivisionLevel.Canton
    ) }

    override fun getAllLocations(sourceDetails: SourceDetails): List<VotationLocation> {
        val locations = mutableListOf<VotationLocation>()

        for (counter in 0..25) {
            val votationLocation = VotationLocation(
                counter.toString(),
                counter + 1,
                "${counter} de",
                "${counter} short",
                DivisionLevel.Canton
            )

            locations.add(votationLocation)
        }

        return locations

    }

    override fun getLocationByExtId(extId: String, sourceDetails: SourceDetails): VotationLocation? {
        return locations.find { location -> location.extId == extId }
    }
}