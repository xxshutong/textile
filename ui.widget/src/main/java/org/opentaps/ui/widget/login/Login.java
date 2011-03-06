package org.opentaps.ui.widget.login;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.authroles.authentication.panel.SignInPanel;


public class Login extends WebPage {

    private static final long serialVersionUID = 4336390968338598338L;

    public Login() {
        this(null);
    }
    public Login(final PageParameters parameters) {
        super(parameters);
        add(new SignInPanel("signInPanel"));
    }

}
