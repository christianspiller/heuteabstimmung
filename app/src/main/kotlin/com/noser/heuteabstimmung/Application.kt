package com.noser.heuteabstimmung

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("com.noser.heuteabstimmung")
		.start()
}

