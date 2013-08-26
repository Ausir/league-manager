package it.unipd.dei.db.kayak.league_manager;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * EVERYTHING starts from here.
 */
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {
	
	private Home home;

	@Override
	public void init(VaadinRequest request) {
		home = new Home();
		addDetachListener(new DetachListener() {
			@Override
			public void detach(DetachEvent event) {
				home.close();
			}
		});
		this.setContent(home.getContent());
	}
	
	public Home getHome() {
		return home;
	}
}
