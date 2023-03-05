package com.helia.oauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class OauthApplication

fun main(args: Array<String>) {
	runApplication<OauthApplication>(*args)
}