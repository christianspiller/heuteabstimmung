package com.noser.heuteabstimmung

import com.noser.heuteabstimmung.core.model.SourceDetails
import com.noser.heuteabstimmung.core.model.VotationLocation
import com.noser.heuteabstimmung.core.model.DivisionLevel
import com.noser.heuteabstimmung.core.usecase.ImportLocationUseCase
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataSelectorEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataTypeEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.DivisionLevelEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.VotationLocationDataSelectorEntity
import com.noser.heuteabstimmung.persistence.db.impl.repositories.DataSelectorRepository
import com.noser.heuteabstimmung.persistence.db.impl.repositories.VotationLocationDataSelectorRepository
import com.noser.heuteabstimmung.persistence.db.impl.repositories.VotationLocationRepository
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.collections.shouldHaveSize
import java.net.URI

class ImportTest(
    private val importLocationUseCase: ImportLocationUseCase,
    private val votationLocationRepository: VotationLocationRepository,
    private val votationLocationDataSelectorRepository: VotationLocationDataSelectorRepository,
    private val dataSelectorRepository: DataSelectorRepository
) : DatabaseTest() {

    private fun sourceDetails(storageAllowed: Boolean): SourceDetails {
        return SourceDetails("srg", URI("https://api.ssrsrg.ch"), storageAllowed, "any")
    }

    init {

        val votationLocation = VotationLocation("123", 2,"Kanton Bern", "BE", DivisionLevel.Canton)

        "test import of a location should not store data when source details deny it" {

            val sourceDetails = sourceDetails(false)

            importLocationUseCase.importLocation(votationLocation, sourceDetails)
            votationLocationRepository.findAll() shouldHaveSize 0
        }

        "test import of a location should store votation location data when source details allow it" {

            val sourceDetails = sourceDetails(true)

            importLocationUseCase.importLocation(votationLocation, sourceDetails)

            val locations = votationLocationRepository.findAll()

            locations shouldHaveSingleElement {
                votationLocationEntity ->
                votationLocationEntity.id == 1L && votationLocationEntity.shortName == "BE"
            }
        }

        "test import of a location twice should not duplicate location data" {

            val sourceDetails = sourceDetails(true)
            
            importLocationUseCase.importLocation(votationLocation, sourceDetails)
            importLocationUseCase.importLocation(votationLocation, sourceDetails)

            val locations = votationLocationRepository.findAll()

            locations shouldHaveSingleElement {
                    votationLocationEntity ->
                votationLocationEntity.id == 1L && votationLocationEntity.shortName == "BE"
            }

        }

        val expectedSelector = DataSelectorEntity(1, "Kanton Bern", "123",
            DataTypeEntity.LOCATION_DATA, votationLocation.hashCode().toString(), "srg")

        "test import of a location should create a selector and index keys" {

            val sourceDetails = sourceDetails(false)

            importLocationUseCase.importLocation(votationLocation, sourceDetails)

            val selectors = dataSelectorRepository.findAll()

            selectors shouldHaveSingleElement expectedSelector
            selectors.first().indices.map { dataIndexEntity -> dataIndexEntity.key }.shouldContainAll("Kanton", "Bern", "BE")

            val votationLocationDataSelectors = votationLocationDataSelectorRepository.findAllVotationLocationDataSelectors()
            votationLocationDataSelectors shouldHaveSingleElement VotationLocationDataSelectorEntity(1, 2, expectedSelector,
            DivisionLevelEntity.Canton)

        }

        "test import of a location twice should not duplicate selector and keys" {

            val sourceDetails = sourceDetails(true)

            importLocationUseCase.importLocation(votationLocation, sourceDetails)
            importLocationUseCase.importLocation(votationLocation, sourceDetails)

            val selectors = dataSelectorRepository.findAll()

            selectors shouldHaveSingleElement expectedSelector
            selectors.first().indices.map { dataIndexEntity -> dataIndexEntity.key }.shouldContainAll("Kanton", "Bern", "BE")
        }

    }
}
