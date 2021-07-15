package com.noser.heuteabstimmung.persistence.db.impl.entities

import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation
import javax.persistence.*

@MappedEntity
@Table(name = "data_selector")
data class DataSelectorEntity(@Id
                        @GeneratedValue(strategy = GenerationType.IDENTITY)
                        var id: Long,
                              var name: String,
                              var extid: String,
                              var type: String,
                              var level: String,
                              var hash: String,
                              var source: String
                        )
{
    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "fk_data_selector")
    private var indices: Set<DataIndexEntity> = emptySet()
}
