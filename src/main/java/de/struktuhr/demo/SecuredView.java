package de.struktuhr.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Route("secured")
@PermitAll
public class SecuredView extends Div {
    public SecuredView() {
        OidcUser user = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        add(new Paragraph("This is a secured route and you are the user '%s'".formatted(user.getName())));

        Anchor logoutLink = new Anchor("/logout", "logout");
        logoutLink.setRouterIgnore(true); // <-- /logout is handled by spring and not Vaadin
        add(logoutLink);

        Button btn = new Button("Click me");
        btn.addClickListener(e -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication.getAuthorities());
            add(new Paragraph("Clicked"));
        });

        add(btn);
    }
}
