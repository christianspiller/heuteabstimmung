package com.noser.heuteabstimmung

import com.noser.heuteabstimmung.core.model.DataSelector
import com.noser.heuteabstimmung.core.model.DataType
import com.noser.heuteabstimmung.core.model.DivisionLevel
import com.noser.heuteabstimmung.core.model.VotationLocationDataSelector
import com.noser.heuteabstimmung.core.usecase.LookupDataUseCase
import com.noser.heuteabstimmung.persistence.db.impl.entities.*
import com.noser.heuteabstimmung.persistence.db.impl.repositories.DataSelectorRepository
import com.noser.heuteabstimmung.persistence.db.impl.repositories.VotationLocationDataSelectorRepository
import io.kotest.core.test.TestCase
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSingleElement
import io.kotest.matchers.collections.shouldHaveSize

import java.util.function.Consumer

class LookupTest(
    private val lookupDataUseCase: LookupDataUseCase,
    private val dataSelectorRepository: DataSelectorRepository,
    private val votationLocationDataSelectorRepository: VotationLocationDataSelectorRepository
) : DatabaseTest() {

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)

        val dataSelectorEntityBe = DataSelectorEntity(0, "Kanton Bern", "123",
            DataTypeEntity.LOCATION_DATA, "xyz", "srg")
        dataSelectorRepository.save(dataSelectorEntityBe)
        addKeys(dataSelectorEntityBe, "Bern", "Kanton", "BE")
        votationLocationDataSelectorRepository.save(
            VotationLocationDataSelectorEntity(0, 2, dataSelectorEntityBe, DivisionLevelEntity.Canton))


        val dataSelectorEntityLu = DataSelectorEntity(0,"Kanton Luzern", "456",  DataTypeEntity.LOCATION_DATA,
            "xyz", "srg")
        dataSelectorRepository.save(dataSelectorEntityLu)
        addKeys(dataSelectorEntityLu, "Luzern", "Kanton", "LU")
        votationLocationDataSelectorRepository.save(
            VotationLocationDataSelectorEntity(0, 3, dataSelectorEntityLu, DivisionLevelEntity.Canton))
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
            val dataSelector =  DataSelector("Kanton Bern", "123",
                DataType.LOCATION_DATA, "xyz", "srg")
            val expected = VotationLocationDataSelector(2, dataSelector, DivisionLevel.Canton)

            listOf("Bern", "Ber", "ber", "bER").forAll { key ->
                lookupDataUseCase.findLocationSelectors(key, null) shouldHaveSingleElement expected

            }
        }


        "test search with wrong keyword should return empty list" {

            listOf("ern", "ton", "xyz").forAll { key ->
                lookupDataUseCase.findLocationSelectors(key, null) shouldHaveSize 0
            }
        }

        "test search with higher scored keyword should return multiple results" {

            val dataSelectorBe =  DataSelector("Kanton Bern", "123",
                DataType.LOCATION_DATA, "xyz", "srg")
            val expectedBe = VotationLocationDataSelector(2, dataSelectorBe, DivisionLevel.Canton)

            val dataSelectorLu = DataSelector("Kanton Luzern", "456",
                DataType.LOCATION_DATA,"xyz", "srg")
            val expectedLu = VotationLocationDataSelector(3, dataSelectorLu, DivisionLevel.Canton)

            listOf("Kanton", "kanTon", "ka").forAll { key ->
                lookupDataUseCase.findLocationSelectors(key, null) shouldContainAll
                        listOf(
                            expectedBe,
                            expectedLu)
            }
        }

    }
}
