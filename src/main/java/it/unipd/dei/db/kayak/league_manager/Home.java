package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.ClubDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpDetails;
import it.unipd.dei.db.kayak.league_manager.data.PlayerCareerInfo;
import it.unipd.dei.db.kayak.league_manager.data.Tournament;
import it.unipd.dei.db.kayak.league_manager.data.TournamentDetails;
import it.unipd.dei.db.kayak.league_manager.database.DML;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
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

	private List<Button> recentTournamentButtons;
	private Button allTournamentsButton;
	private Button allPlayersButton;
	private Button allClubsButton;
	private Button manageButton;

	private LoginElement login;
	private String loggedInUserEmail;

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

	public void showTournamentCalendarView(String tournamentName,
			int tournamentYear) {
		mainAreaLayout.removeAllComponents();
		
		TournamentDetails tDetails = DML.retrieveTournamentDetails(tournamentName, tournamentYear);
//				FakeDataWarehouse.getTournamentDetails(
//				tournamentName, tournamentYear);

		mainAreaLayout.addComponent(new TournamentCalendarViewer(tDetails)
				.getContent());
	}

	public void showClubDetailsSubWindow(long clubID) {
		if (!clubDetailsSubWindow.containsKey(clubID)) {
			ClubDetails clubDetails = DML.retrieveClubDetails(clubID);
//					FakeDataWarehouse.getClubDetails(clubID);
			
			ClubDetailsSubWindow detailsWindow = new ClubDetailsSubWindow(
					clubDetails);

			clubDetailsSubWindow.put(clubID, detailsWindow);
			UI.getCurrent().addWindow(detailsWindow.getWindow());
		}
	}

	public void closedClubDetailsSubWindow(long clubID) {
		if (clubDetailsSubWindow.containsKey(clubID)) {
			clubDetailsSubWindow.remove(clubID);
		}
	}

	public void showMatchUpDetailsSubWindow(String matchUpID) {
		if (!matchUpDetailsSubWindows.containsKey(matchUpID)) {
			MatchUpDetails details = DML.retrieveMatchUpDetails(matchUpID);
//					FakeDataWarehouse
//					.getMatchUpDetails(matchUpID);
			
			MatchUpDetailsSubWindow detailsWindow = new MatchUpDetailsSubWindow(
					details);

			matchUpDetailsSubWindows.put(matchUpID, detailsWindow);
			UI.getCurrent().addWindow(detailsWindow.getWindow());
		}
	}

	public void closedMatchUpDetailsSubWindow(String matchUpID) {
		if (matchUpDetailsSubWindows.containsKey(matchUpID)) {
			matchUpDetailsSubWindows.remove(matchUpID);
		}
	}

	public void showPlayerCareerInfoSubWindow(long playerID) {
		if (!playerCareerInfoSubWindows.containsKey(playerID)) {
			PlayerCareerInfo playerInfo = DML.retrievePlayerCareerInfo(playerID);
//					FakeDataWarehouse
//					.getPlayerCareerInfo(playerID);
			
			PlayerCareerInfoSubWindow playerWindow = new PlayerCareerInfoSubWindow(
					playerInfo);

			playerCareerInfoSubWindows.put(playerID, playerWindow);
			UI.getCurrent().addWindow(playerWindow.getWindow());
		}
	}

	public void closedPlayerCareerInfoSubWindow(long playerID) {
		if (playerCareerInfoSubWindows.containsKey(playerID)) {
			playerCareerInfoSubWindows.remove(playerID);
		}
	}

	public String getLoggedInUserEmail() {
		return loggedInUserEmail;
	}

	public void setLoggedIn(String loggedUserEmail) {
		login.setLoggedIn(loggedUserEmail);

		manageButton.setVisible(true);

		loggedInUserEmail = loggedUserEmail;
	}

	public void setUnlogged() {
		login.setUnlogged();

		manageButton.setVisible(false);
		// TODO: close all opened manage elements

		loggedInUserEmail = null;
	}

	private void setUpContent() {
//		FakeDataWarehouse.initFakeData();

		loggedInUserEmail = null;

		mainLayout = new VerticalLayout();

		headerLayout = new HorizontalLayout();

		String basepath = VaadinService.getCurrent().getBaseDirectory()
				.getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath
				+ "/WEB-INF/images/fick_logo_256x120.png"));
		Image logo = new Image(null, resource);

		headerLayout.addComponent(logo);
		headerLayout.setExpandRatio(logo, 1);

		login = new LoginElement();
		VerticalLayout loginLayout = login.getContent();
		loginLayout.setWidth("200px");
		headerLayout.addComponent(loginLayout);

		headerLayout.setHeight("120px");
		headerLayout.setWidth("100%");

		mainLayout.addComponent(headerLayout);

		bodyLayout = new HorizontalLayout();

		leftBar = new VerticalLayout();
		leftBar.addComponent(new Label("Most recent Tournaments"));
		List<Tournament> tournaments = DML.retrieveAllTournaments(); 
//				FakeDataWarehouse
//				.getMostRecentTournaments();
		recentTournamentButtons = new ArrayList<Button>();

		for (final Tournament t : tournaments) {
			Button btn = new Button(t.getName() + " - " + t.getYear(),
					new ClickListener() {
						@Override
						public void buttonClick(ClickEvent event) {
							showTournamentCalendarView(t.getName(), t.getYear());
						}
					});
			recentTournamentButtons.add(btn);
			leftBar.addComponent(btn);
		}

		allTournamentsButton = new Button("All Tournaments",
				new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						showTournamentList();
					}
				});
		leftBar.addComponent(allTournamentsButton);

		allPlayersButton = new Button("All Players", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				showPlayerList();
			}
		});
		leftBar.addComponent(allPlayersButton);

		allClubsButton = new Button("All Clubs", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				showClubList();
			}
		});
		leftBar.addComponent(allClubsButton);

		manageButton = new Button("Manage Something", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("Manage something");
			}
		});
		leftBar.addComponent(manageButton);
		manageButton.setVisible(false);

		Label leftBarSpacer = new Label();
		leftBar.addComponent(leftBarSpacer);
		leftBar.setExpandRatio(leftBarSpacer, 1);
		leftBar.setWidth("250px");
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
