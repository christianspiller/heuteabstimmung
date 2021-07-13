package com.noser.heuteabstimmung

import io.micronaut.runtime.Micronaut

object ApplicationKt {

	@JvmStatic
	fun main(args: Array<String>) {
		Micronaut.build()
			.args(*args)
			.packages("com.noser.heuteabstimmung")
			.start()
	}
}