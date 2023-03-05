package com.helia.oauth.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.helia.oauth.account.AccountUserDetails
import com.helia.oauth.jwt.IdTokenEnhancer
import com.helia.oauth.jwt.JwtClamsEnhancer
import com.helia.oauth.repository.ClientDetailRepository
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientRegistrationException
import org.springframework.security.oauth2.provider.client.BaseClientDetails
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import java.security.KeyPair
import java.util.List

/**
 * https://docs.spring.io/spring-security-oauth2-boot/docs/2.3.x-SNAPSHOT/reference/html5/#oauth2-boot-authorization-server-spring-security-oauth2-resource-server
 */
@EnableAuthorizationServer
@Configuration
class AuthorizationServerConfig(
    authenticationConfiguration: AuthenticationConfiguration,
    props: JwtProperties,
    jwtClamsEnhancer: JwtClamsEnhancer,
    private val clientDetailRepository: ClientDetailRepository,
    private val objectMapper:ObjectMapper
) : AuthorizationServerConfigurerAdapter() {
    private val authenticationManager: AuthenticationManager
    private val keyPair: KeyPair
    private val jwtClamsEnhancer: JwtClamsEnhancer
    private val log=LoggerFactory.getLogger(javaClass)
    init {
        authenticationManager = authenticationConfiguration.authenticationManager
        keyPair = props.keyPair
        this.jwtClamsEnhancer = jwtClamsEnhancer
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        val tokenEnhancerChain = TokenEnhancerChain()
        tokenEnhancerChain.setTokenEnhancers(
            listOf(
                jwtClamsEnhancer,
                jwtAccessTokenConverter(),
                IdTokenEnhancer(jwtAccessTokenConverter())
            )
        )
        endpoints
            .authenticationManager(authenticationManager)
            .tokenEnhancer(tokenEnhancerChain)
            .tokenStore(tokenStore())
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.withClientDetails { clientId: String ->
            val clientDetailEntity = clientDetailRepository.findByClientId(clientId)
                ?: throw ClientRegistrationException(String.format("%s is not registered.", clientId))
            var scope=""
            if(!clientDetailEntity.clientScopes.isNullOrEmpty())
                scope= clientDetailEntity.clientScopes!!.map { it.name!! }.reduce { acc, scopeEntity -> "$acc,${scopeEntity}" }
            var authorities=""
            if(!clientDetailEntity.clientAuthorities.isNullOrEmpty())
                authorities= clientDetailEntity.clientAuthorities!!.map { it.name!! }.reduce { acc, auth -> "$acc,${auth}" }
            var redirectUrls=""
            if(!clientDetailEntity.clientRedirects.isNullOrEmpty())
                redirectUrls= clientDetailEntity.clientRedirects!!.map { it.url }.reduce { acc, s -> "$acc,$s" }.toString()
            log.error("redirectUrls: $redirectUrls")
            log.error("scope: $scope")
            val baseClientDetails = BaseClientDetails(
                clientId, clientDetailEntity.resourceIds?:"",
                scope,
                clientDetailEntity.grantType,
                authorities,
                redirectUrls
            )
            baseClientDetails.setAutoApproveScopes(clientDetailEntity.clientScopes!!.map { it.name!! })
            baseClientDetails.clientSecret=clientDetailEntity.clientSecret
            baseClientDetails.accessTokenValiditySeconds=604800
            baseClientDetails.refreshTokenValiditySeconds=604800
            baseClientDetails.addAdditionalInformation("user",getUserDetail())
            baseClientDetails
        }
    }

    private fun getUserDetail(): String {
        if(SecurityContextHolder.getContext().authentication!=null && SecurityContextHolder.getContext().authentication.isAuthenticated && SecurityContextHolder.getContext().authentication.principal is AccountUserDetails)
            return objectMapper.writeValueAsString((SecurityContextHolder.getContext().authentication.principal as AccountUserDetails).userAccount)
        return ""
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JwtTokenStore(jwtAccessTokenConverter())
    }

    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setKeyPair(keyPair)
        return converter
    }
}