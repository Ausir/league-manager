package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.ClubDetails;
import it.unipd.dei.db.kayak.league_manager.data.LMUser;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

// create subwindow containing club details
public class ClubDetailsSubWindow {
	private Window window;

	private ClubDetails clubDetails;

	public ClubDetailsSubWindow(ClubDetails clubDetails) {
		this.clubDetails = clubDetails;

		this.setUpWindow();
	}

	private void setUpWindow() {
		window = new Window();
		window.setCaption("" + clubDetails.getClubID() + " - "
				+ clubDetails.getName());
		window.setHeight("400px");
		window.setWidth("600px");

		TabSheet tabs = new TabSheet();

		// content of Contacts tab
		VerticalLayout tabLayout = new VerticalLayout();
		tabLayout.setMargin(new MarginInfo(false, false, true, false));

		tabLayout.addComponent(new Label(clubDetails.getName()));
		tabLayout
				.addComponent(new Label("Indirizzo: " + clubDetails.getAddress()));
		tabLayout
				.addComponent(new Label("Sito web: " + clubDetails.getWebsite()));
		tabLayout.addComponent(new Label("Email: " + clubDetails.getEmail()));
		tabLayout.addComponent(new Label("Responsabili:"));

		VerticalLayout tableLayout = new VerticalLayout();
		tableLayout.setMargin(new MarginInfo(false, false, false, true));

		LMUserTable mgrTable = new LMUserTable();
		for (LMUser mgr : clubDetails.getClubManagers()) {
			mgrTable.addLMUser(mgr);
		}

		tableLayout.addComponent(mgrTable);
		tableLayout.setExpandRatio(mgrTable, 1);
		mgrTable.setSizeFull();

		tabLayout.addComponent(tableLayout);
		tabLayout.setExpandRatio(tableLayout, 1);
		tableLayout.setSizeFull();

		tabLayout.setSizeFull();

		tabs.addTab(tabLayout, "Contatti");

		// content of Players
		tabLayout = new VerticalLayout();
		tabLayout.setMargin(new MarginInfo(false, false, true, false));

		tabLayout.addComponent(new Label("Giocatori attualmente di proprietà del club"));

		tableLayout = new VerticalLayout();
		tableLayout.setMargin(new MarginInfo(false, false, false, true));

		PlayerTable playerTable = new PlayerTable(clubDetails.getClubPlayers());

		tableLayout.addComponent(playerTable);
		tableLayout.setExpandRatio(playerTable, 1);
		playerTable.setSizeFull();

		tabLayout.addComponent(tableLayout);
		tabLayout.setExpandRatio(tableLayout, 1);
		tableLayout.setSizeFull();

		tabLayout.setSizeFull();

		tabs.addTab(tabLayout, "Giocatori attuali");

		window.setContent(tabs);
		tabs.setSizeFull();

		final long clubID = clubDetails.getClubID();
		window.addCloseListener(new CloseListener() {
			@Override
			public void windowClose(CloseEvent e) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				home.closedClubDetailsSubWindow(clubID);
			}
		});
	}

	public Window getWindow() {
		return window;
	}
}
