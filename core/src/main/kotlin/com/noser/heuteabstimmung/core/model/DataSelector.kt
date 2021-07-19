package com.noser.heuteabstimmung.core.model


data class DataSelector(var name: String,
                        var extId: String,
                        var dataType: DataType,
                        var divisionLevel: DivisionLevel,
                        var hash: String,
                        var source: String)
{
    var indexKeys: Set<String> = emptySet()
}
