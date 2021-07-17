package com.noser.heuteabstimmung

import com.noser.heuteabstimmung.core.model.DataSelector
import com.noser.heuteabstimmung.core.usecase.LookupDataUseCase
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataIndexEntity
import com.noser.heuteabstimmung.persistence.db.impl.entities.DataSelectorEntity
import com.noser.heuteabstimmung.persistence.db.impl.repositories.DataSelectorRepository
import io.kotest.core.test.TestCase
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.collections.shouldHaveSize

import java.util.function.Consumer

class LookupTest(
    private val lookupDataUseCase: LookupDataUseCase,
    private val dataSelectorRepository: DataSelectorRepository
) : DatabaseTest() {

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)

        val dataSelectorEntity = DataSelectorEntity(0, "Kanton Bern", "123", "Location",
            "Kanton", "xyz", "srg")
        dataSelectorRepository.save(dataSelectorEntity)
        addKeys(dataSelectorEntity, "Bern", "Kanton", "BE")

        val dataSelectorEntity2 = DataSelectorEntity(0, "Kanton Luzern", "456", "Location",
            "Kanton", "xyz", "srg")
        dataSelectorRepository.save(dataSelectorEntity2)
        addKeys(dataSelectorEntity2, "Luzern", "Kanton", "LU")
    }

    private fun addKeys(dataSelectorEntity: DataSelectorEntity, vararg keys: String) {
        val dataIndexEntities = mutableListOf<DataIndexEntity>()
        keys.toList().forEach(Consumer { key -> dataIndexEntities.add(
            DataIndexEntity(0, key, dataSelectorEntity)
        ) })

        dataSelectorRepository.saveAll(dataIndexEntities)
    }

    init {

        "test search by keyword should return data selector candidates" {

            listOf("Bern", "Ber", "ber", "bER").forAll { key ->
                lookupDataUseCase.findLocationSelectors(key, null) shouldHaveSingleElement
                        DataSelector("Kanton Bern", "123",
                            "Location", "Kanton", "xyz", "srg")
            }
        }


        "test search with wrong keyword should return empty list" {

            listOf("ern", "ton", "xyz").forAll { key ->
                lookupDataUseCase.findLocationSelectors(key, null) shouldHaveSize 0
            }
        }

        "test search with higher scored keyword should return multiple results" {

            listOf("Kanton", "kanTon", "ka").forAll { key ->
                lookupDataUseCase.findLocationSelectors(key, null) shouldContainAll
                        listOf(
                            DataSelector("Kanton Bern", "123",
                            "Location", "Kanton", "xyz", "srg"),
                            DataSelector("Kanton Luzern", "456",
                                "Location", "Kanton", "xyz", "srg"))
            }
        }

    }
}
