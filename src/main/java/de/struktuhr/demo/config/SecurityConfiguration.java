package de.struktuhr.demo.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {
    private final OidcClientInitiatedLogoutSuccessHandler logoutSuccessHandler;

    public SecurityConfiguration(@Autowired ClientRegistrationRepository clientRegistrationRepository) {
        logoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        logoutSuccessHandler.setPostLogoutRedirectUri("http://localhost:8081/unsecured"); // <-- Where Keycloak will redirect after logging out
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.oauth2Login(Customizer.withDefaults()); // <-- This is important to let Spring Security to know to redirect to external login page.
        http.logout(c -> c.logoutSuccessHandler(logoutSuccessHandler)); // <-- Logout with oauth2 must be handled with Keycloak
        super.configure(http);
    }
}
