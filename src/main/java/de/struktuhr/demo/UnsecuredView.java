package de.struktuhr.demo;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("unsecured")
@RouteAlias("")
@AnonymousAllowed
public class UnsecuredView extends Div {
    public UnsecuredView() {
        add(new Paragraph("Welcome to unsecured route. This you may access without logging in."));
        Anchor linkToSecuredPage = new Anchor("/secured", "This route will require you to login");
        linkToSecuredPage.setRouterIgnore(true); // <-- So that spring security web filter will catch it
        add(linkToSecuredPage);
    }
}