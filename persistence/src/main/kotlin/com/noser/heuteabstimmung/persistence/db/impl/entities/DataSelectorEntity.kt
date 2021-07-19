package com.noser.heuteabstimmung.persistence.db.impl.entities

import javax.persistence.*

@Entity
@Table(name = "data_selector")
data class DataSelectorEntity(@Id
                        @GeneratedValue(strategy = GenerationType.IDENTITY)
                        var id: Long,
                              var name: String,
                              var extId: String,
                              var dataType: DataTypeEntity,
                              var divisionLevel: DivisionLevelEntity,
                              var hash: String,
                              var source: String
                        )
{
    @OneToMany(mappedBy = "dataSelectorEntity", fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
    var indices: List<DataIndexEntity> = mutableListOf()
}
