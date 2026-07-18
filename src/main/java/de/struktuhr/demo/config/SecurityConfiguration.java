package de.struktuhr.demo.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

import java.util.*;
import java.util.stream.Collectors;

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


    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapperForKeycloak() {

        return (Collection<? extends GrantedAuthority> authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            for (GrantedAuthority grantedAuthority : authorities) {
                if (grantedAuthority instanceof OidcUserAuthority oidcUserAuthority) {
                    Set<SimpleGrantedAuthority> mappedOidcAuthorities =
                            Optional.ofNullable(oidcUserAuthority.getUserInfo())
                                    .map(oidcUserInfo -> oidcUserInfo.getClaimAsMap("realm_access"))
                                    .map(realm -> realm.get("roles"))
                                    .map(Collection.class::cast)
                                    .map(roles -> ((Collection<String>) roles).stream()
                                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                            .collect(Collectors.toSet())
                                    ).orElse(Collections.emptySet());

                    mappedAuthorities.addAll(mappedOidcAuthorities);
                }
                else {
                    mappedAuthorities.add(grantedAuthority);
                }
            }

            return mappedAuthorities;
        };

    }
}
