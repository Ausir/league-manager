package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.EventResult;
import it.unipd.dei.db.kayak.league_manager.data.FakeDataWarehouse;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;
import it.unipd.dei.db.kayak.league_manager.data.OwnershipResult;
import it.unipd.dei.db.kayak.league_manager.data.Player;
import it.unipd.dei.db.kayak.league_manager.data.PlayerCareerInfo;
import it.unipd.dei.db.kayak.league_manager.data_utils.EventResultTimeComparator;
import it.unipd.dei.db.kayak.league_manager.data_utils.OwnershipResultDateComparator;

import java.util.Collections;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PlayerCareerInfoSubWindow {
	// private fields
	private Window window;
	// private VerticalLayout mainLayout;

	private Player player;
	private PlayerCareerInfo playerInfo;

	public PlayerCareerInfoSubWindow(Player player, PlayerCareerInfo playerInfo) {
		this.player = player;
		this.playerInfo = playerInfo;

		Collections.sort(playerInfo.getOwnerships(),
				new OwnershipResultDateComparator());

		Collections.sort(playerInfo.getEvents(), new EventResultTimeComparator(
				true));

		this.setUpWindow();
	}

	public void setUpWindow() {
		window = new Window();
		window.setCaption(player.getID() + " - " + player.getFirstName() + " "
				+ player.getLastName());
		window.setHeight("400px");
		window.setWidth("600px");

		TabSheet tabs = new TabSheet();

		// content of Player Info
		VerticalLayout tabLayout = new VerticalLayout();
		// tabLayout.setMargin(new MarginInfo(false, false, false, true));

		tabLayout.addComponent(new Label(player.getID() + " - "
				+ player.getFirstName() + " " + player.getLastName()));
		tabLayout.addComponent(new Label("Birthday: " + player.getBirthday()));

		Label spacer = new Label();
		tabLayout.addComponent(spacer);
		tabLayout.setExpandRatio(spacer, 1);

		tabLayout.setSizeFull();

		tabs.addTab(tabLayout, "Player Info");

		// Ownerships section
		tabLayout = new VerticalLayout();
		tabLayout.setMargin(new MarginInfo(false, false, true, false));

		tabLayout.addComponent(new Label("Ownerships contracts"));

		VerticalLayout tableLayout = new VerticalLayout();
		tableLayout.setMargin(new MarginInfo(false, false, false, true));

		OwnershipResultTable ownershipTable = new OwnershipResultTable();
		for (OwnershipResult o : playerInfo.getOwnerships()) {
			ownershipTable.addOwnershipResult(o);
		}

		tableLayout.addComponent(ownershipTable);
		tableLayout.setExpandRatio(ownershipTable, 1);
		ownershipTable.setSizeFull();

		tabLayout.addComponent(tableLayout);
		tabLayout.setExpandRatio(tableLayout, 1);
		tableLayout.setSizeFull();

		tabLayout.setSizeFull();

		tabs.addTab(tabLayout, "Ownerships");

		// Events section
		tabLayout = new VerticalLayout();
		tabLayout.setMargin(new MarginInfo(false, false, true, false));

		tabLayout.addComponent(new Label("Events"));

		VerticalLayout subLayout = new VerticalLayout();
		subLayout.setMargin(new MarginInfo(false, false, false, true));

		for (EventResult e : playerInfo.getEvents()) {
			MatchUpDetails details = FakeDataWarehouse.getMatchUpDetails(e
					.getMatchUpID());
			MatchUpResult res = details.getResult();

			HorizontalLayout eventLayout = new HorizontalLayout();
			final String matchUpID = res.getMatchUpID();
			eventLayout.addComponent(new Button(res.getFullyQualifiedName(),
					new ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							Home home = ((MyVaadinUI) UI.getCurrent())
									.getHome();
							home.showMatchUpDetailsSubWindow(matchUpID);
						}
					}));
			String eventCaption = "    - " + e.getCompactString();
			eventLayout.addComponent(new Label(eventCaption));

			spacer = new Label();
			eventLayout.addComponent(spacer);
			eventLayout.setExpandRatio(spacer, 1);

			subLayout.addComponent(eventLayout);
		}

		tabLayout.addComponent(subLayout);
		tabLayout.setExpandRatio(subLayout, 1);
		subLayout.setSizeFull();

		spacer = new Label("");
		tabLayout.addComponent(spacer);
		tabLayout.setExpandRatio(spacer, 1);

		tabLayout.setSizeFull();

		tabs.addTab(tabLayout, "Events");

		window.setContent(tabs);
		tabs.setSizeFull();

		final int playerID = (int) player.getID();
		window.addCloseListener(new CloseListener() {
			@Override
			public void windowClose(CloseEvent e) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				home.closedPlayerCareerInfoSubWindow(playerID);
			}
		});
	}

	public Window getWindow() {
		return window;
	}
}
