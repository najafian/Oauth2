package com.helia.oauth.account

import com.helia.oauth.config.OauthProperties
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.util.stream.Collectors

@Controller
class AccountController(oauthProperties: OauthProperties) {
    private val clientDetails: List<ClientDetails>

    init {
        clientDetails = oauthProperties.getMyClients().values
            .stream()
            .filter { c: ClientDetails -> c.additionalInformation.containsKey("name") }
            .collect(Collectors.toList())
    }

    @GetMapping(path = ["/"])
    fun index(model: Model, @AuthenticationPrincipal userDetails: AccountUserDetails): String {
        model.addAttribute("account", userDetails.userAccount)
        model.addAttribute("clientDetails", clientDetails)
        return "index"
    }

    @GetMapping(path = ["login"])
    fun login(): String {
        return "login"
    }
}