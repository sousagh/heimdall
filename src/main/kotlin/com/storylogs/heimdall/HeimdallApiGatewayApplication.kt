package com.storylogs.heimdall

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HeimdallApiGatewayApplication

fun main(args: Array<String>) {
	runApplication<HeimdallApiGatewayApplication>(*args)
}
