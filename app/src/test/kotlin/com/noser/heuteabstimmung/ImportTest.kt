package com.noser.heuteabstimmung

import com.noser.heuteabstimmung.core.model.SourceDetails
import com.noser.heuteabstimmung.core.model.VotationLocation
import com.noser.heuteabstimmung.core.model.VotationLocationLevel
import com.noser.heuteabstimmung.core.usecase.ImportLocationUseCase
import com.noser.heuteabstimmung.persistence.db.impl.entities.VotationLocationEntity
import com.noser.heuteabstimmung.persistence.db.impl.repositories.VotationLocationRepository
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import org.flywaydb.core.Flyway
import java.net.URI

@MicronautTest(transactional = false)
class ImportTest(
    private val importLocationUseCase: ImportLocationUseCase,
    private val repository: VotationLocationRepository,
    private val flyway: Flyway
) : StringSpec() {

    override fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)
        flyway.clean()
        flyway.migrate()
    }

    init {

        "test import of a location without saving raw data" {

            val votationLocation = VotationLocation("123", "Kanton Bern", "BE", VotationLocationLevel.Kanton)
            val sourceDetails = SourceDetails("srg", URI("https://api.ssrsrg.ch"), false, "any")

            importLocationUseCase.importLocation(votationLocation, sourceDetails)

            repository.findAll() shouldBe emptyList<VotationLocation>()
        }

        "test import of a location" {

            val votationLocation = VotationLocation("456", "Kanton Bern", "BE", VotationLocationLevel.Kanton)
            val sourceDetails = SourceDetails("srg", URI("https://api.ssrsrg.ch"), true, "any")

            importLocationUseCase.importLocation(votationLocation, sourceDetails)

            repository.findAll() shouldBe listOf(VotationLocationEntity(1, "456", "Kanton Bern", "BE", "Kanton"))
        }
        
    }
}
