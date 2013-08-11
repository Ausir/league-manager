package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.EventResult;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;
import it.unipd.dei.db.kayak.league_manager.data.PlayerMatchUpInfo;

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
		window.setHeight("300px");
		window.setWidth("450px");

		TabSheet tabs = new TabSheet();

		VerticalLayout eventLayout = new VerticalLayout();
		String matchUpCaption = mRes.getTournamentName() + " "
				+ mRes.getTournamentYear() + " - " + mRes.getDate() + ": "
				+ mRes.getTournamentPhaseName();
		Label matchUpLabel = new Label(matchUpCaption);
		eventLayout.addComponent(matchUpLabel);

		Label leftSpacer = new Label("");
		leftSpacer.setWidth("10px");
		Label midSpacer = new Label("");
		midSpacer.setWidth("10px");
		Label rightSpacer = new Label("");
		rightSpacer.setWidth("10px");

		HorizontalLayout eventSplitter = new HorizontalLayout();
		VerticalLayout eventHostLayout = new VerticalLayout();
		VerticalLayout eventGuestLayout = new VerticalLayout();

		Label hostResultLabel = new Label(mRes.getTeamHostName() + " "
				+ mRes.getTeamHostGoals());
		eventHostLayout.addComponent(hostResultLabel);
		Label guestResultLabel = new Label(mRes.getTeamGuestName() + " "
				+ mRes.getTeamGuestGoals());
		eventGuestLayout.addComponent(guestResultLabel);

		for (EventResult event : matchUpDetails.getEventList()) {
			if (event.getPlayerInfo().getClubID() == mRes.getClubHostID()) {
				eventHostLayout
						.addComponent(new Label(event.getCompactString()));
				eventGuestLayout.addComponent(new Label(""));
			} else {
				eventHostLayout.addComponent(new Label(""));
				eventGuestLayout.addComponent(new Label(event
						.getCompactString()));
			}
		}

		eventSplitter.addComponent(leftSpacer);
		eventSplitter.addComponent(eventHostLayout);
		eventSplitter.addComponent(midSpacer);
		eventSplitter.addComponent(eventGuestLayout);
		eventSplitter.addComponent(rightSpacer);
		eventSplitter.setExpandRatio(eventHostLayout, 0.5f);
		eventSplitter.setExpandRatio(eventGuestLayout, 0.5f);
		eventLayout.addComponent(eventSplitter);
		eventLayout.setExpandRatio(eventSplitter, 1);
		eventLayout.setSizeFull();

		tabs.addTab(eventLayout, "Events");

		Label leftSpacer2 = new Label("");
		leftSpacer2.setWidth("10px");
		Label midSpacer2 = new Label("");
		midSpacer2.setWidth("10px");
		Label rightSpacer2 = new Label("");
		rightSpacer2.setWidth("10px");

		VerticalLayout lineUpsLayout = new VerticalLayout();
		HorizontalLayout lineUpsSplitter = new HorizontalLayout();
		VerticalLayout lineUpsHostLayout = new VerticalLayout();
		VerticalLayout lineUpsGuestLayout = new VerticalLayout();

		Label hostClubLabel = new Label(mRes.getTeamHostName());
		lineUpsHostLayout.addComponent(hostClubLabel);
		Label guestClubLabel = new Label(mRes.getTeamGuestName());
		lineUpsGuestLayout.addComponent(guestClubLabel);

		for (PlayerMatchUpInfo playerInfo : matchUpDetails.getHostLineUp()) {
			// lineUpsHostLayout.addComponent(new Label(playerInfo
			// .getCompactString()));
			final int playerID = (int) playerInfo.getPlayerID();
			lineUpsHostLayout.addComponent(new Button(playerInfo.getNumber()
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
			// lineUpsGuestLayout.addComponent(new Label(playerInfo
			// .getCompactString()));
			final int playerID = (int) playerInfo.getPlayerID();
			lineUpsGuestLayout.addComponent(new Button(playerInfo.getNumber()
					+ " " + playerInfo.getPlayerName(), new ClickListener() {
				private static final long serialVersionUID = -8353847524980785433L;

				@Override
				public void buttonClick(ClickEvent event) {
					Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
					home.showPlayerCareerInfoSubWindow(playerID);
				}
			}));
		}

		lineUpsSplitter.addComponent(leftSpacer2);
		lineUpsSplitter.addComponent(lineUpsHostLayout);
		lineUpsSplitter.addComponent(midSpacer2);
		lineUpsSplitter.addComponent(lineUpsGuestLayout);
		lineUpsSplitter.addComponent(rightSpacer2);
		lineUpsSplitter.setExpandRatio(lineUpsHostLayout, 0.5f);
		lineUpsSplitter.setExpandRatio(lineUpsGuestLayout, 0.5f);
		lineUpsLayout.addComponent(lineUpsSplitter);
		lineUpsLayout.setExpandRatio(lineUpsSplitter, 1);
		lineUpsLayout.setSizeFull();

		tabs.addTab(lineUpsLayout, "LineUps");

		window.setContent(tabs);
		tabs.setSizeFull();

		final int mUpID = (int) matchUpDetails.getResult().getMatchUpID();
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
