package com.helia.oauth.config

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity.RequestMatcherConfigurer
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@Order(-1)
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .formLogin { formLogin: FormLoginConfigurer<HttpSecurity?> ->
                formLogin
                    .loginPage("/login").usernameParameter("email").passwordParameter("password").permitAll()
            }
            .logout { logout: LogoutConfigurer<HttpSecurity?> ->
                logout
                    .logoutRequestMatcher(AntPathRequestMatcher("/logout") /* supports GET /logout */)
                    .permitAll()
            }
            .requestMatchers { requestMatchers: RequestMatcherConfigurer ->
                requestMatchers
                    .mvcMatchers(
                        "/",
                        "/login",
                        "/logout",
                        "/oauth/authorize",
                        "token_keys",
                        "/.well-known/*",
                        "/oauth/token/.well-known/*"
                    )
                    .requestMatchers(EndpointRequest.toAnyEndpoint())
            }
            .authorizeRequests { authorize ->
                authorize
                    .mvcMatchers("/oauth/authorize", "/token_keys", "/.well-known/*", "/oauth/token/.well-known/*")
                    .permitAll()
                    .requestMatchers(EndpointRequest.to("info", "health", "prometheus")).permitAll()
                    .anyRequest().authenticated()
            }
    }
}