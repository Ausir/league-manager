package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.ClubDetails;
import it.unipd.dei.db.kayak.league_manager.data.FakeDataWarehouse;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpDetails;
import it.unipd.dei.db.kayak.league_manager.data.Player;
import it.unipd.dei.db.kayak.league_manager.data.PlayerCareerInfo;
import it.unipd.dei.db.kayak.league_manager.data.Tournament;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Home {
	// private fields
	private VerticalLayout mainLayout;
	private HorizontalLayout headerLayout;
	// private HorizontalLayout buttonsLayout;
	private HorizontalLayout bodyLayout;
	private VerticalLayout leftBar;
	private VerticalLayout mainAreaLayout;

	private Map<String, MatchUpDetailsSubWindow> matchUpDetailsSubWindows;
	private Map<Long, PlayerCareerInfoSubWindow> playerCareerInfoSubWindows;
	private Map<Long, ClubDetailsSubWindow> clubDetailsSubWindow;

	// constructor
	public Home() {
		matchUpDetailsSubWindows = new HashMap<String, MatchUpDetailsSubWindow>();
		playerCareerInfoSubWindows = new HashMap<Long, PlayerCareerInfoSubWindow>();
		clubDetailsSubWindow = new HashMap<Long, ClubDetailsSubWindow>();

		this.setUpContent();
	}

	public void showTournamentList() {
		mainAreaLayout.removeAllComponents();
		mainAreaLayout.addComponent(new TournamentListViewer().getContent());
	}

	public void showPlayerList() {
		mainAreaLayout.removeAllComponents();
		mainAreaLayout.addComponent(new PlayerListViewer().getContent());
	}

	public void showClubList() {
		mainAreaLayout.removeAllComponents();
		mainAreaLayout.addComponent(new ClubListViewer().getContent());
	}

	public void showTournamentCalendarView(Tournament tournament) {
		mainAreaLayout.removeAllComponents();
		mainAreaLayout.addComponent(new TournamentCalendarViewer(tournament)
				.getContent());
	}

	public void showClubDetailsSubWindow(long clubID) {
		if (!clubDetailsSubWindow.containsKey(clubID)) {
			ClubDetails clubDetails = FakeDataWarehouse.getClubDetails(clubID);
			ClubDetailsSubWindow detailsWindow = new ClubDetailsSubWindow(
					clubDetails);
			clubDetailsSubWindow.put(clubID, detailsWindow);
			UI.getCurrent().addWindow(detailsWindow.getWindow());
		}
	}

	public void closedClubDetailsSubWindow(long clubID) {
		if (clubDetailsSubWindow.containsKey(clubID)) {
			// System.out.println("close event of MatchUpDetailsSubWindow "
			// + matchUpID);
			clubDetailsSubWindow.remove(clubID);
		}
	}

	public void showMatchUpDetailsSubWindow(String matchUpID) {
		if (!matchUpDetailsSubWindows.containsKey(matchUpID)) {
			// System.out.println("opening of MatchUpDetailsSubWindow "
			// + matchUpID);
			MatchUpDetails details = FakeDataWarehouse
					.getMatchUpDetails(matchUpID);
			MatchUpDetailsSubWindow detailsWindow = new MatchUpDetailsSubWindow(
					details);
			matchUpDetailsSubWindows.put(matchUpID, detailsWindow);
			UI.getCurrent().addWindow(detailsWindow.getWindow());
		}
	}

	// public void closeMatchUpDetailsSubWindow(int matchUpID) {
	// if (matchUpDetailsSubWindows.containsKey(matchUpID)) {
	// UI.getCurrent().removeWindow(
	// matchUpDetailsSubWindows.get(matchUpID).getWindow());
	// matchUpDetailsSubWindows.remove(matchUpID);
	// }
	// }

	public void closedMatchUpDetailsSubWindow(String matchUpID) {
		if (matchUpDetailsSubWindows.containsKey(matchUpID)) {
			// System.out.println("close event of MatchUpDetailsSubWindow "
			// + matchUpID);
			matchUpDetailsSubWindows.remove(matchUpID);
		}
	}

	public void showPlayerCareerInfoSubWindow(long playerID) {
		if (!playerCareerInfoSubWindows.containsKey(playerID)) {
			Player player = null;
			for (Player p : FakeDataWarehouse.getPlayers()) {
				if (p.getID() == playerID) {
					player = p;
					break;
				}
			}
			PlayerCareerInfo playerInfo = FakeDataWarehouse
					.getPlayerCareerInfo(playerID);
			PlayerCareerInfoSubWindow playerWindow = new PlayerCareerInfoSubWindow(
					player, playerInfo);

			playerCareerInfoSubWindows.put(playerID, playerWindow);
			UI.getCurrent().addWindow(playerWindow.getWindow());
		}
	}

	public void closedPlayerCareerInfoSubWindow(long playerID) {
		if (playerCareerInfoSubWindows.containsKey(playerID)) {
			// System.out.println("close event of MatchUpDetailsSubWindow "
			// + matchUpID);
			playerCareerInfoSubWindows.remove(playerID);
		}
	}

	private void setUpContent() {
		FakeDataWarehouse.initFakeData();

		mainLayout = new VerticalLayout();

		headerLayout = new HorizontalLayout();
		Label bannerLabel = new Label(
				"PROTOTYPE BANNER - FICK LOGO + OTHER STUFF");
		headerLayout.addComponent(bannerLabel);
		headerLayout.setComponentAlignment(bannerLabel, Alignment.TOP_LEFT);
		headerLayout.setHeight("80px");
		headerLayout.setWidth("100%");
		mainLayout.addComponent(headerLayout);

		bodyLayout = new HorizontalLayout();

		leftBar = new VerticalLayout();
		leftBar.addComponent(new Label("Most recent Tournaments"));
		List<Tournament> tournaments = FakeDataWarehouse
				.getMostRecentTournaments();
		for (final Tournament t : tournaments) {
			leftBar.addComponent(new Button(t.getName() + " - " + t.getYear(),
					new ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							showTournamentCalendarView(t);
						}
					}));
		}

		leftBar.addComponent(new Button("All Tournaments", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				showTournamentList();
			}
		}));

		leftBar.addComponent(new Button("All Players", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				showPlayerList();
			}
		}));

		leftBar.addComponent(new Button("All Clubs", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				showClubList();
			}
		}));

		Label leftBarSpacer = new Label();
		leftBar.addComponent(leftBarSpacer);
		leftBar.setExpandRatio(leftBarSpacer, 1);
		leftBar.setWidth("150px");
		leftBar.setHeight("100%");
		bodyLayout.addComponent(leftBar);

		mainAreaLayout = new VerticalLayout();
		mainAreaLayout.addComponent(new Label("Main Area Layout"));
		mainAreaLayout.setSizeFull();

		bodyLayout.addComponent(mainAreaLayout);
		bodyLayout.setExpandRatio(mainAreaLayout, 1);
		bodyLayout.setSizeFull();
		mainLayout.addComponent(bodyLayout);
		mainLayout.setExpandRatio(bodyLayout, 1);

		mainLayout.setSizeFull();
	}

	public VerticalLayout getContent() {
		return mainLayout;
	}
}
