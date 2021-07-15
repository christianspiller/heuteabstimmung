package com.noser.heuteabstimmung
import com.noser.heuteabstimmung.persistence.db.impl.repositories.VotationLocationRepository
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.optional.shouldBePresent
import io.kotest.mpp.log
import java.util.function.Consumer

@MicronautTest
class DatabaseTest(private val application: EmbeddedApplication<*>, private val votationLocationRepository: VotationLocationRepository): StringSpec({

    "test database access" {
        val location = votationLocationRepository.findById(1)


    }
})
