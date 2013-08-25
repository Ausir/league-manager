package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.ClubDetails;
import it.unipd.dei.db.kayak.league_manager.data.LMUserDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpDetails;
import it.unipd.dei.db.kayak.league_manager.data.PlayerCareerInfo;
import it.unipd.dei.db.kayak.league_manager.data.Tournament;
import it.unipd.dei.db.kayak.league_manager.data.TournamentDetails;
import it.unipd.dei.db.kayak.league_manager.data.TournamentEssentials;
import it.unipd.dei.db.kayak.league_manager.database.DML;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Tree;
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

	private Button addPlayerButton;
	private Button addOwnershipButton;

	private LoginElement login;
	private LMUserDetails loggedInUser;

	private Map<String, MatchUpDetailsSubWindow> matchUpDetailsSubWindows;
	private Map<Long, PlayerCareerInfoSubWindow> playerCareerInfoSubWindows;
	private Map<Long, ClubDetailsSubWindow> clubDetailsSubWindow;

	private Visualizing current;
	private String currentTName;
	private String currentMID;
	private int currentTYear;

	private enum Visualizing {
		HOME, CLUBS, TOURNAMENTS, PLAYERS, SINGLE_TOURNAMENT, SINGLE_MATCHDAY, ADD_PLAYER, ADD_OWNERSHIP
	}

	// constructor
	public Home() {
		matchUpDetailsSubWindows = new HashMap<String, MatchUpDetailsSubWindow>();
		playerCareerInfoSubWindows = new HashMap<Long, PlayerCareerInfoSubWindow>();
		clubDetailsSubWindow = new HashMap<Long, ClubDetailsSubWindow>();

		current = Visualizing.HOME;
		currentTName = "";
		currentTYear = -1;

		this.setUpContent();
	}

	public void showTournamentList() {
		if (current != Visualizing.TOURNAMENTS) {
			current = Visualizing.TOURNAMENTS;

			mainAreaLayout.removeAllComponents();
			mainAreaLayout
					.addComponent(new TournamentListViewer().getContent());
		}
	}

	public void showPlayerList() {
		if (current != Visualizing.PLAYERS) {
			current = Visualizing.PLAYERS;

			mainAreaLayout.removeAllComponents();
			mainAreaLayout.addComponent(new PlayerListViewer().getContent());
		}
	}

	public void showClubList() {
		if (current != Visualizing.CLUBS) {
			current = Visualizing.CLUBS;

			mainAreaLayout.removeAllComponents();
			mainAreaLayout.addComponent(new ClubListViewer().getContent());
		}
	}

	public void showTournamentCalendarView(String tournamentName,
			int tournamentYear) {
		if ((current != Visualizing.SINGLE_TOURNAMENT)
				|| (!currentTName.equals(tournamentName) || currentTYear != tournamentYear)) {
			currentTName = tournamentName;
			currentTYear = tournamentYear;
			current = Visualizing.SINGLE_TOURNAMENT;

			mainAreaLayout.removeAllComponents();

			TournamentDetails tDetails = DML.retrieveTournamentDetails(tournamentName, tournamentYear);
					//FakeDataWarehouse.getTournamentDetails(tournamentName, tournamentYear);

			mainAreaLayout.addComponent(new TournamentCalendarViewer(tDetails)
			.getContent());
		}
	}

	public void showMatchDayCalendarView(String matchday_id) {
		if ((current != Visualizing.SINGLE_MATCHDAY)
				|| (!currentMID.equals(matchday_id))) {

			currentMID = matchday_id;
			current = Visualizing.SINGLE_MATCHDAY;

			mainAreaLayout.removeAllComponents();

			MatchDayMatches mDetails = DML.retrieveMatches(matchday_id);

			mainAreaLayout.addComponent(new MatchDayCalendarViewer(mDetails).getContent());
		}
	}

public void showAddPlayerView() {
		if (current != Visualizing.ADD_PLAYER) {
			current = Visualizing.ADD_PLAYER;

			mainAreaLayout.removeAllComponents();
			mainAreaLayout.addComponent(new AddPlayerView().getContent());
		}
	}

	public void showAddOwnershipView() {
		if (current != Visualizing.ADD_OWNERSHIP) {
			current = Visualizing.ADD_OWNERSHIP;

			mainAreaLayout.removeAllComponents();
			mainAreaLayout.addComponent(new AddOwnershipView().getContent());
		}
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

	public LMUserDetails getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedIn(LMUserDetails loggedUser) {
		login.setLoggedIn(loggedUser);

		if (loggedUser.isSectretary()) {
			addPlayerButton.setVisible(true);
			addOwnershipButton.setVisible(true);
		}

		loggedInUser = loggedUser;
		// System.out.println("user email: "
		// + loggedInUser.getUserData().getEmail());
		// System.out.println("is secretary: " + loggedInUser.isSectretary());
		// System.out.println("is manager: " + loggedInUser.isManager());
		// System.out.println("is club manager: "
		// + (loggedInUser.getManagedClubID() != -1));
	}

	public void setUnlogged() {
		login.setUnlogged();

		addPlayerButton.setVisible(false);
		addOwnershipButton.setVisible(false);
		// TODO: close all opened manage elements

		loggedInUser = null;
	}

	private void setUpContent() {
//		FakeDataWarehouse.initFakeData();

		loggedInUser = null;

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

		// Create the Tree,a dd to layout
		Tree tree = new Tree("Tornei recenti");

		Map<TournamentEssentials, List<MatchDay>> days = DML.retrieveAllMatchDays();
		List<TournamentEssentials> tournaments = new ArrayList<TournamentEssentials>();
		tournaments.addAll(0, days.keySet());
		Collections.sort(tournaments);

		final HierarchicalContainer treeCont = new HierarchicalContainer();
		treeCont.addContainerProperty("t_year", Integer.class, null);
		treeCont.addContainerProperty("t_name", String.class, null);
		treeCont.addContainerProperty("d_id", String.class, null);
		treeCont.addContainerProperty("caption", String.class, null);

		for (TournamentEssentials t : tournaments) {
			// insert and update object
			Object parent_id = treeCont.addItem();
			treeCont.getItem(parent_id).getItemProperty("t_name").setValue(t.getName());
			treeCont.getItem(parent_id).getItemProperty("t_year").setValue(t.getYear());
			treeCont.getItem(parent_id).getItemProperty("caption").setValue(t.getName());

			for (MatchDay day : days.get(t)) {
				String d_id = day.getID();
				SimpleDateFormat df =
						new SimpleDateFormat("dd-MM-yyyy");
				String d_date = df.format((Date)day.getStartDate());

				Object child_id = treeCont.addItem();
				treeCont.getItem(child_id).getItemProperty("d_id").setValue(d_id);
				treeCont.getItem(child_id).getItemProperty("caption").setValue(d_date);
				
				treeCont.setParent(child_id, parent_id);
				treeCont.setChildrenAllowed(child_id, false);
			}
			// Expand the subtree.
			tree.collapseItem(parent_id);
		}

		tree.setContainerDataSource(treeCont);
		tree.setItemCaptionPropertyId("caption");
		tree.setItemCaptionMode(ItemCaptionMode.PROPERTY);


		// Cause valueChange immediately when the user selects
		tree.setImmediate(true);

		// Set tree to show the 'name' property as caption for items
		//tree.setItemCaptionPropertyId(ExampleUtil.hw_PROPERTY_NAME);
		//tree.setItemCaptionMode(ItemCaptionMode.PROPERTY);

		tree.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				Object id = event.getProperty().getValue(); 
				if (id != null){
					
					if (treeCont.getItem(id).getItemProperty("t_name").getValue() == null){
						showMatchDayCalendarView((String) treeCont.getItem(id).getItemProperty("d_id").getValue());
					} else {
						showTournamentCalendarView((String)treeCont.getItem(id).getItemProperty("t_name").getValue(), (Integer)treeCont.getItem(id).getItemProperty("t_year").getValue());
					}
				}
			}
		});

		leftBar.addComponent(tree);

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

		addPlayerButton = new Button("Add Player", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (loggedInUser != null) {
					showAddPlayerView();
				}
			}
		});
		leftBar.addComponent(addPlayerButton);
		addPlayerButton.setVisible(false);

		addOwnershipButton = new Button("Add Ownership", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (loggedInUser != null) {
					showAddOwnershipView();
				}
			}
		});
		leftBar.addComponent(addOwnershipButton);
		addOwnershipButton.setVisible(false);

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

	public void close(){
		// TODO: cleanup
	}
}
