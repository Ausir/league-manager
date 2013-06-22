package it.unipd.dei.db.kayak.league_manager;

import java.util.Date;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

	@Override
	public void init(VaadinRequest request) {
	    // Create the content root layout for the UI
	    VerticalLayout content = new VerticalLayout();
	    setContent(content);

	    // Add a simple component
	    Label label = new Label("Hello Vaadin user");
	    content.addComponent(label);

	    // Add a component with user interaction
	    content.addComponent(new Button("What is the time?",
	                                    new ClickListener() {
	        public void buttonClick(ClickEvent event) {
	            Notification.show("The time is " + new Date());
	        }
	    }));
	}
}