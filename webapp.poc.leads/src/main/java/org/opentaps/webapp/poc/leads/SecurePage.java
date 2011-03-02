package org.opentaps.webapp.poc.leads;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;


public class SecurePage extends WebPage {
    private static final long serialVersionUID = -1557982817543655258L;

    public SecurePage() {
        super();
    }

    public SecurePage(IModel<?> model) {
        super(model);
    }

    public SecurePage(PageParameters parameters) {
        super(parameters);
    }
}
