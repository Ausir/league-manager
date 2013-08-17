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
		window.setCaption(player.getID() + " " + player.getFirstName() + " "
				+ player.getLastName());
		window.setHeight("300px");
		window.setWidth("450px");

		VerticalLayout mainLayout = new VerticalLayout();
		// mainLayout.setMargin(new MarginInfo(true, true, true, true));

		mainLayout.addComponent(new Label(player.getID() + " "
				+ player.getFirstName() + " " + player.getLastName()));
		mainLayout.addComponent(new Label("Born " + player.getBirthday()));

		// Ownerships section
		mainLayout.addComponent(new Label("Ownerships contracts"));

		VerticalLayout tableLayout = new VerticalLayout();
		tableLayout.setMargin(new MarginInfo(false, false, false, true));
		OwnershipResultTable ownershipTable = new OwnershipResultTable();

		for (OwnershipResult o : playerInfo.getOwnerships()) {
			ownershipTable.addOwnershipResult(o);
		}
		ownershipTable.setHeight("100px");
		tableLayout.addComponent(ownershipTable);
		

		mainLayout.addComponent(tableLayout);

		// Events section
		mainLayout.addComponent(new Label("Events"));

		tableLayout = new VerticalLayout();
		tableLayout.setMargin(new MarginInfo(false, false, false, true));

		for (EventResult e : playerInfo.getEvents()) {
			MatchUpDetails details = FakeDataWarehouse
					.getMatchUpDetails((int) e.getMatchUpID());
			MatchUpResult res = details.getResult();

			HorizontalLayout eventLayout = new HorizontalLayout();
			final int matchUpID = (int) res.getMatchUpID();
			eventLayout.addComponent(new Button(res.getFullyQualifiedName(),
					new ClickListener() {
						private static final long serialVersionUID = 8649065438982350398L;

						@Override
						public void buttonClick(ClickEvent event) {
							Home home = ((MyVaadinUI) UI.getCurrent())
									.getHome();
							home.showMatchUpDetailsSubWindow(matchUpID);
						}
					}));
			String eventCaption = "    - " + e.getCompactString();
			eventLayout.addComponent(new Label(eventCaption));

			Label spacer = new Label();
			eventLayout.addComponent(spacer);
			eventLayout.setExpandRatio(spacer, 1);

			tableLayout.addComponent(eventLayout);
		}

		mainLayout.addComponent(tableLayout);

		Label spacer = new Label("");
		mainLayout.addComponent(spacer);
		mainLayout.setExpandRatio(spacer, 1);

		window.setContent(mainLayout);
		mainLayout.setSizeFull();

		final int playerID = (int) player.getID();
		window.addCloseListener(new CloseListener() {
			private static final long serialVersionUID = 731861246706327704L;

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
