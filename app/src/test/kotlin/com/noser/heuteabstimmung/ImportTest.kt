package com.noser.heuteabstimmung

import com.noser.heuteabstimmung.core.model.SourceDetails
import com.noser.heuteabstimmung.core.model.VotationLocation
import com.noser.heuteabstimmung.core.model.VotationLocationLevel
import com.noser.heuteabstimmung.core.usecase.ImportLocationUseCase
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataSelectorEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.VotationLocationEntity
import com.noser.heuteabstimmung.persistence.db.impl.repositories.DataSelectorRepository
import com.noser.heuteabstimmung.persistence.db.impl.repositories.VotationLocationRepository
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.collections.shouldHaveSize
import java.net.URI

class ImportTest(
    private val importLocationUseCase: ImportLocationUseCase,
    private val votationLocationRepository: VotationLocationRepository,
    private val dataSelectorRepository: DataSelectorRepository
) : DatabaseTest() {

    init {

        "test import of a location without saving raw data" {

            val votationLocation = VotationLocation("123", "Kanton Bern", "BE", VotationLocationLevel.Kanton)
            val sourceDetails = SourceDetails("srg", URI("https://api.ssrsrg.ch"), false, "any")

            importLocationUseCase.importLocation(votationLocation, sourceDetails)
            votationLocationRepository.findAll() shouldHaveSize 0
        }

        "test import of a location" {

            val votationLocation = VotationLocation("456", "Kanton Bern", "BE", VotationLocationLevel.Kanton)
            val sourceDetails = SourceDetails("srg", URI("https://api.ssrsrg.ch"), true, "any")

            importLocationUseCase.importLocation(votationLocation, sourceDetails)

            val locations = votationLocationRepository.findAll()

            locations shouldHaveSingleElement VotationLocationEntity(1, "456", "Kanton Bern", "BE", "Kanton")
        }

        "test indexing of a location" {

            val votationLocation = VotationLocation("456", "Kanton Bern", "BE", VotationLocationLevel.Kanton)
            val sourceDetails = SourceDetails("srg", URI("https://api.ssrsrg.ch"), true, "any")

            importLocationUseCase.importLocation(votationLocation, sourceDetails)

            val selectors = dataSelectorRepository.findAll()

            selectors shouldHaveSingleElement DataSelectorEntity(1, "Kanton Bern", "456",
                "Location", "Kanton", votationLocation.hashCode().toString(), "srg")
            selectors.first().indices.map { dataIndexEntity -> dataIndexEntity.key }.shouldContainAll("Kanton", "Bern", "BE")
        }

    }
}
