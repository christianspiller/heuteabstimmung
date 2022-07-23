package com.noser.heuteabstimmung.core.model

/**
 * DataSelectors are indexes which reference to external or internal sources. Because not every source allows to save
 * the data locally, the DataSelector saves only the name which can be used for searching the index.
 */
data class DataSelector(var name: String,
                        var extId: String,
                        var dataType: DataType,
                        var hash: String,
                        var source: String)
{
    var indexKeys: Set<String> = emptySet()
}
