package com.noser.heuteabstimmung

import io.micronaut.runtime.Micronaut

object HeuteAbstimmungApplication {

	@JvmStatic
	fun main(args: Array<String>) {
		Micronaut.build()
			.args(*args)
			.mainClass(HeuteAbstimmungApplication.javaClass)
			.start()
	}
}