package com.helia.oauth.config

import com.helia.oauth.repository.ClientDetailRepository
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.client.BaseClientDetails
import java.util.function.Function
import java.util.stream.Collectors
import javax.annotation.PostConstruct

@ConfigurationProperties(prefix = "oauth")
class OauthProperties @ConstructorBinding constructor(private val clients: List<BaseClientDetails>) {

    fun getMyClients(): Map<String, ClientDetails> {
        return clients.associateBy { it.clientId }
//        return clients.stream().collect(
//            Collectors.toMap(
//                Function { obj: BaseClientDetails -> obj.clientId },
//                Function<BaseClientDetails, ClientDetails?> { x: BaseClientDetails? -> x })
//        )
    }
}