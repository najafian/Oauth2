package com.helia.oauth.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.helia.oauth.account.AccountUserDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames
import org.springframework.security.oauth2.jwt.JwtClaimNames
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.stereotype.Component
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.List

@Component
class JwtClamsEnhancer(private val clientDetailsService: ClientDetailsService,private val objectMapper:ObjectMapper) : TokenEnhancer {
    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        val issuer = ServletUriComponentsBuilder.fromCurrentRequest().build().toString()
        val additionalInformation: MutableMap<String, Any> = LinkedHashMap()
        val expiration = accessToken.expiration.toInstant()
        val client = SecurityContextHolder.getContext().authentication
        val clientId = client.name
        val clientDetails = clientDetailsService.loadClientByClientId(clientId)
        additionalInformation[JwtClaimNames.ISS] = issuer
        additionalInformation[JwtClaimNames.EXP] = expiration.epochSecond
        additionalInformation[JwtClaimNames.IAT] =
            expiration.minusSeconds(clientDetails.accessTokenValiditySeconds.toLong()).epochSecond
        additionalInformation[JwtClaimNames.AUD] = List.of(clientId)
        val nonce = authentication.oAuth2Request.requestParameters[OidcParameterNames.NONCE]
        if (nonce != null) {
            additionalInformation[OidcParameterNames.NONCE] = nonce
        }
        if (authentication.principal is AccountUserDetails) {
            val userDetails = authentication
                .principal as AccountUserDetails
            val account = userDetails.userAccount
            additionalInformation[JwtClaimNames.SUB] = account.email
            additionalInformation["user"] = objectMapper.writeValueAsString(account)
            additionalInformation["email"] = account.email
            additionalInformation["email_verified"] = true
        }
        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInformation
        return accessToken
    }
}