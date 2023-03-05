package com.helia.oauth.jwt

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import java.util.Map
import java.util.Set

class IdTokenEnhancer(private val jwtAccessTokenConverter: JwtAccessTokenConverter) : TokenEnhancer {
    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        if (accessToken.scope.contains(OidcScopes.OPENID)) {
            val idToken = DefaultOAuth2AccessToken(accessToken)
            idToken.scope = Set.of(OidcScopes.OPENID)
            idToken.refreshToken = null
            (accessToken as DefaultOAuth2AccessToken).additionalInformation = Map.of<String, Any>(
                OidcParameterNames.ID_TOKEN,
                jwtAccessTokenConverter.enhance(idToken, authentication).value
            )
        }
        return accessToken
    }
}