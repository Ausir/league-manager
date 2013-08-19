package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.EventResult;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;
import it.unipd.dei.db.kayak.league_manager.data.PlayerMatchUpInfo;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class MatchUpDetailsSubWindow {
	private Window window;

	private MatchUpDetails matchUpDetails;

	public MatchUpDetailsSubWindow(MatchUpDetails matchUpDetails) {
		this.matchUpDetails = matchUpDetails;

		this.setUpWindow();
	}

	private void setUpWindow() {
		MatchUpResult mRes = matchUpDetails.getResult();

		window = new Window();
		window.setCaption(matchUpDetails.getResult().getFullyQualifiedName());
		window.setHeight("400px");
		window.setWidth("600px");

		TabSheet tabs = new TabSheet();

		// content of Events tab
		VerticalLayout tabLayout = new VerticalLayout();
		// tabLayout.setMargin(new MarginInfo(true, true, true, true));

		String matchUpCaption = mRes.getTournamentName() + " "
				+ mRes.getTournamentYear() + " - " + mRes.getDate() + ": "
				+ mRes.getTournamentPhaseName();
		tabLayout.addComponent(new Label(matchUpCaption));
		tabLayout.addComponent(new Label("Played at "
				+ matchUpDetails.getPitchName()));

		HorizontalLayout tabLayoutSplitter = new HorizontalLayout();
		VerticalLayout leftSplitLayout = new VerticalLayout();
		VerticalLayout rightSplitLayout = new VerticalLayout();

		leftSplitLayout.addComponent(new Label(mRes.getTeamHostName() + " "
				+ mRes.getTeamHostGoals()));
		rightSplitLayout.addComponent(new Label(mRes.getTeamGuestName() + " "
				+ mRes.getTeamGuestGoals()));

		VerticalLayout leftSplitSubLayout = new VerticalLayout();
		leftSplitSubLayout.setMargin(new MarginInfo(false, false, false, true));
		VerticalLayout rightSplitSubLayout = new VerticalLayout();
		rightSplitSubLayout
				.setMargin(new MarginInfo(false, false, false, true));

		for (EventResult event : matchUpDetails.getEventList()) {
			if (event.getPlayerInfo().getClubID() == mRes.getClubHostID()) {
				leftSplitSubLayout.addComponent(new Label(event
						.getShortString()));
				rightSplitSubLayout.addComponent(new Label(""));
			} else {
				leftSplitSubLayout.addComponent(new Label(""));
				rightSplitSubLayout.addComponent(new Label(event
						.getShortString()));
			}
		}

		leftSplitLayout.addComponent(leftSplitSubLayout);
		rightSplitLayout.addComponent(rightSplitSubLayout);

		tabLayoutSplitter.addComponent(leftSplitLayout);
		tabLayoutSplitter.addComponent(rightSplitLayout);
		tabLayoutSplitter.setExpandRatio(leftSplitLayout, 0.5f);
		tabLayoutSplitter.setExpandRatio(rightSplitLayout, 0.5f);

		tabLayout.addComponent(tabLayoutSplitter);
		tabLayoutSplitter.setSizeFull();
		tabLayout.setExpandRatio(tabLayoutSplitter, 1);
		tabLayout.setSizeFull();

		tabs.addTab(tabLayout, "Events");

		tabLayout = new VerticalLayout();
		// tabLayout.setMargin(new MarginInfo(true, true, true, true));

		tabLayoutSplitter = new HorizontalLayout();
		leftSplitLayout = new VerticalLayout();
		rightSplitLayout = new VerticalLayout();

		leftSplitLayout.addComponent(new Label(mRes.getTeamHostName()));
		rightSplitLayout.addComponent(new Label(mRes.getTeamGuestName()));

		leftSplitSubLayout = new VerticalLayout();
		leftSplitSubLayout.setMargin(new MarginInfo(false, false, false, true));
		rightSplitSubLayout = new VerticalLayout();
		rightSplitSubLayout
				.setMargin(new MarginInfo(false, false, false, true));

		for (PlayerMatchUpInfo playerInfo : matchUpDetails.getHostLineUp()) {
			final int playerID = (int) playerInfo.getPlayerID();
			leftSplitSubLayout.addComponent(new Button(playerInfo.getNumber()
					+ " " + playerInfo.getPlayerName(), new ClickListener() {
				private static final long serialVersionUID = -8353847524980785433L;

				@Override
				public void buttonClick(ClickEvent event) {
					Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
					home.showPlayerCareerInfoSubWindow(playerID);
				}
			}));
		}
		for (PlayerMatchUpInfo playerInfo : matchUpDetails.getGuestLineUp()) {
			final int playerID = (int) playerInfo.getPlayerID();
			rightSplitSubLayout.addComponent(new Button(playerInfo.getNumber()
					+ " " + playerInfo.getPlayerName(), new ClickListener() {
				private static final long serialVersionUID = -8353847524980785433L;

				@Override
				public void buttonClick(ClickEvent event) {
					Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
					home.showPlayerCareerInfoSubWindow(playerID);
				}
			}));
		}

		leftSplitLayout.addComponent(leftSplitSubLayout);
		rightSplitLayout.addComponent(rightSplitSubLayout);

		tabLayoutSplitter.addComponent(leftSplitLayout);
		tabLayoutSplitter.addComponent(rightSplitLayout);
		tabLayoutSplitter.setExpandRatio(leftSplitLayout, 0.5f);
		tabLayoutSplitter.setExpandRatio(rightSplitLayout, 0.5f);

		tabLayout.addComponent(tabLayoutSplitter);
		tabLayout.setExpandRatio(tabLayoutSplitter, 1);
		tabLayout.setSizeFull();

		tabs.addTab(tabLayout, "LineUps");

		// content of Judge Staff
		tabLayout = new VerticalLayout();
		// tabLayout.setMargin(new MarginInfo(true, true, true, true));

		tabLayout.addComponent(new Label("Referees:"));
		VerticalLayout tabSubLayout = new VerticalLayout();
		tabSubLayout.setMargin(new MarginInfo(false, false, false, true));
		tabSubLayout.addComponent(new Label(matchUpDetails.getReferee1()));
		String tmp = matchUpDetails.getReferee2();
		if (tmp != null) {
			tabSubLayout.addComponent(new Label(tmp));
		}
		tabLayout.addComponent(tabSubLayout);

		tabLayout.addComponent(new Label("Scorekeeper:"));
		tabSubLayout = new VerticalLayout();
		tabSubLayout.setMargin(new MarginInfo(false, false, false, true));
		tabSubLayout.addComponent(new Label(matchUpDetails.getScorekeeper()));
		tabLayout.addComponent(tabSubLayout);

		tabLayout.addComponent(new Label("Timekeepers:"));
		tabSubLayout = new VerticalLayout();
		tabSubLayout.setMargin(new MarginInfo(false, false, false, true));
		tabSubLayout.addComponent(new Label(matchUpDetails.getTimekeeper1()));
		tmp = matchUpDetails.getTimekeeper2();
		if (tmp != null) {
			tabSubLayout.addComponent(new Label(tmp));
		}
		tabLayout.addComponent(tabSubLayout);

		tabLayout.addComponent(new Label("Linemen:"));
		tabSubLayout = new VerticalLayout();
		tabSubLayout.setMargin(new MarginInfo(false, false, false, true));
		tabSubLayout.addComponent(new Label(matchUpDetails.getLineman1()));
		tmp = matchUpDetails.getLineman2();
		if (tmp != null) {
			tabSubLayout.addComponent(new Label(tmp));
		}
		tabLayout.addComponent(tabSubLayout);

		// tabLayout.setSizeFull();

		tabs.addTab(tabLayout, "Judge Staff");

		window.setContent(tabs);
		tabs.setSizeFull();

		final String mUpID = matchUpDetails.getResult().getMatchUpID();
		window.addCloseListener(new CloseListener() {
			private static final long serialVersionUID = 731861246706327704L;

			@Override
			public void windowClose(CloseEvent e) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				home.closedMatchUpDetailsSubWindow(mUpID);
			}
		});
	}

	public Window getWindow() {
		return window;
	}
}
