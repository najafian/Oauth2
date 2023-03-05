package com.helia.oauth.wellknown

import com.helia.oauth.config.JwtProperties
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import java.security.KeyPair
import java.security.interfaces.RSAPublicKey
import java.util.List

@RestController
class WellKnownEndpoint(props: JwtProperties) {
    private val keyPair: KeyPair

    init {
        keyPair = props.keyPair
    }

    /**
     * https://openid.net/specs/openid-connect-discovery-1_0.html#ProviderConfig
     */
    @GetMapping(path = [".well-known/openid-configuration", "oauth/token/.well-known/openid-configuration"])
    fun openIdConfiguration(builder: UriComponentsBuilder): Map<String, Any> {
        return java.util.Map.of(
            "issuer", builder.replacePath("oauth/token").build().toString(),
            "authorization_endpoint", builder.replacePath("oauth/authorize").build().toString(),
            "token_endpoint", builder.replacePath("oauth/token").build().toString(),
            "jwks_uri", builder.replacePath("token_keys").build().toString(),
            "subject_types_supported", List.of("public")
        )
    }

    /**
     * https://docs.spring.io/spring-security-oauth2-boot/docs/2.3.x-SNAPSHOT/reference/html5/#oauth2-boot-authorization-server-spring-security-oauth2-resource-server-jwk-set-uri
     */
    @GetMapping(path = ["token_keys"])
    fun tokenKeys(): Map<String, Any> {
        val publicKey = keyPair.public as RSAPublicKey
        val key = RSAKey.Builder(publicKey).build()
        return JWKSet(key).toJSONObject()
    }
}