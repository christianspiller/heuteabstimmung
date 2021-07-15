package com.noser.heuteabstimmung.core.model

import java.net.URI

data class SourceDetails (
    val name: String,
    val landingPage: URI,
    val storageAllowed: Boolean,
    val license: String)

