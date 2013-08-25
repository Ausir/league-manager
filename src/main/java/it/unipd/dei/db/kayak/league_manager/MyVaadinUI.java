package it.unipd.dei.db.kayak.league_manager;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.ClientConnector.DetachEvent;
import com.vaadin.ui.UI;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {
	private Home home;

	@Override
	public void init(VaadinRequest request) {
		// // Create the content root layout for the UI
		// VerticalLayout content = new VerticalLayout();
		// setContent(content);
		//
		// // Add a simple component
		// Label label = new Label("Hello Vaadin user");
		// content.addComponent(label);
		//
		// // Add a component with user interaction
		// content.addComponent(new Button("What is the time?",
		// new ClickListener() {
		// public void buttonClick(ClickEvent event) {
		// Notification.show("The time is " + new Date());
		// }
		// }));
		
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