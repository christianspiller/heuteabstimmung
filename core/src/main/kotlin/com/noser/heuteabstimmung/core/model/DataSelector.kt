package com.noser.heuteabstimmung.core.model


data class DataSelector(var name: String,
                        var extid: String,
                        var type: String,
                        var level: String,
                        var hash: String,
                        var source: String)
{
    var indexKeys: Set<String> = emptySet()
}
