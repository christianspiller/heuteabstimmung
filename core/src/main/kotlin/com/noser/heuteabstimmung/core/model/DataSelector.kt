package com.noser.heuteabstimmung.core.model


data class DataSelector(var name: String,
                        var extId: String,
                        var dataType: DataType,
                        var hash: String,
                        var source: String)
{
    var indexKeys: Set<String> = emptySet()
}
