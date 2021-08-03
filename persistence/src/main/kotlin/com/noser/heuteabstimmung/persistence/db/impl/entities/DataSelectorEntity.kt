package com.noser.heuteabstimmung.persistence.db.impl.entities

import javax.persistence.*

@Entity
@Table(name = "data_selector")
@Inheritance(strategy = InheritanceType.JOINED)
data class DataSelectorEntity(@Id
                        @GeneratedValue(strategy = GenerationType.IDENTITY)
                        var id: Long,
                              var name: String,
                              var extId: String,
                              var dataType: DataTypeEntity,
                              var hash: String,
                              var source: String
                        )
{
    @OneToMany(mappedBy = "dataSelectorEntity", fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
    var indices: List<DataIndexEntity> = mutableListOf()
}
