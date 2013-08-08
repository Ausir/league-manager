package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.FakeDataWarehouse;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpDetails;
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
	private HorizontalLayout buttonsLayout;
	private HorizontalLayout bodyLayout;
	private VerticalLayout leftBar;
	private VerticalLayout mainAreaLayout;

	private Map<Integer, MatchUpDetailsSubWindow> matchUpDetailsSubWindows;

	// constructor
	public Home() {
		matchUpDetailsSubWindows = new HashMap<Integer, MatchUpDetailsSubWindow>();

		this.setUpContent();
	}

	public void showTournamentCalendarView(Tournament tournament) {
		mainAreaLayout.removeAllComponents();
		mainAreaLayout.addComponent(new TournamentCalendarViewer(tournament)
				.getContent());
	}

	public void showPlayerCareerView(int playerID) {
		mainAreaLayout.removeAllComponents();
		// mainAreaLayout.addComponent(new TournamentCalendarViewer(tournament)
		// .getContent());
	}

	public void showClubDetailsView(int clubID) {
		mainAreaLayout.removeAllComponents();
		// mainAreaLayout.addComponent(new TournamentCalendarViewer(tournament)
		// .getContent());
	}

	public void showMatchUpDetailsSubWindow(int matchUpID) {
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

	public void closedMatchUpDetailsSubWindow(int matchUpID) {
		if (matchUpDetailsSubWindows.containsKey(matchUpID)) {
			// System.out.println("close event of MatchUpDetailsSubWindow "
			// + matchUpID);
			matchUpDetailsSubWindows.remove(matchUpID);
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

		buttonsLayout = new HorizontalLayout();
		Button btn1 = new Button("Button1");
		buttonsLayout.addComponent(btn1);
		btn1.setWidth("200px");
		Button btn2 = new Button("Button2");
		buttonsLayout.addComponent(btn2);
		btn2.setWidth("200px");
		Button btn3 = new Button("Button3");
		buttonsLayout.addComponent(btn3);
		btn3.setWidth("200px");
		Label buttonsSpacer = new Label();
		buttonsLayout.addComponent(buttonsSpacer);
		buttonsLayout.setExpandRatio(buttonsSpacer, 1);
		buttonsLayout.setHeight("40px");
		buttonsLayout.setWidth("100%");

		bodyLayout = new HorizontalLayout();

		leftBar = new VerticalLayout();
		leftBar.addComponent(new Label("Most recent Tournaments"));
		List<Tournament> tournaments = FakeDataWarehouse
				.getMostRecentTournaments();
		for (final Tournament t : tournaments) {
			leftBar.addComponent(new Button(t.getName() + " - " + t.getYear(),
					new ClickListener() {
						private static final long serialVersionUID = -1853835079781595641L;

						public void buttonClick(ClickEvent event) {
							showTournamentCalendarView(t);
						}
					}));
		}
		Label leftBarSpacer = new Label();
		leftBar.addComponent(leftBarSpacer);
		leftBar.setExpandRatio(leftBarSpacer, 1);
		leftBar.setWidth("150px");
		leftBar.setHeight("100%");

		mainAreaLayout = new VerticalLayout();
		mainAreaLayout.addComponent(new Label("Main Area Layout"));
		mainAreaLayout.setSizeFull();

		bodyLayout.addComponent(leftBar);
		bodyLayout.addComponent(mainAreaLayout);
		bodyLayout.setExpandRatio(mainAreaLayout, 1);
		bodyLayout.setSizeFull();

		mainLayout.addComponent(headerLayout);
		mainLayout.addComponent(buttonsLayout);
		mainLayout.addComponent(bodyLayout);
		mainLayout.setExpandRatio(bodyLayout, 1);

		mainLayout.setSizeFull();
	}

	public VerticalLayout getContent() {
		return mainLayout;
	}
}
