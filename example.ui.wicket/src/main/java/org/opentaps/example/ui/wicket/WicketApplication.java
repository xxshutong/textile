package org.opentaps.example.ui.wicket;

import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for Example web application.
 * 
 * @see org.opentaps.example.ui.wicket.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
	}
}
