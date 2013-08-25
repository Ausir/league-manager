package it.unipd.dei.db.kayak.league_manager;

import java.io.File;
import java.util.Collections;

import it.unipd.dei.db.kayak.league_manager.data.EventResult;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;
import it.unipd.dei.db.kayak.league_manager.data.PlayerMatchUpInfo;
import it.unipd.dei.db.kayak.league_manager.data_utils.EventResultTimeComparator;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
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

		Collections.sort(matchUpDetails.getEventList(),
				new EventResultTimeComparator(true));

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

		String matchUpCaption = mRes.getTournamentName() + " "
				+ mRes.getTournamentYear() + " - " + mRes.getDate() + ": "
				+ mRes.getTournamentPhaseName();
		tabLayout.addComponent(new Label(matchUpCaption));
		tabLayout.addComponent(new Label("Played at "
				+ matchUpDetails.getPitchName()));

		HorizontalLayout eventLine = new HorizontalLayout();

		Label left = new Label(mRes.getTeamHostName() + " "
				+ mRes.getTeamHostGoals());
		eventLine.addComponent(left);
		left.setWidth("100%");
		Label right = new Label(mRes.getTeamGuestName() + " "
				+ mRes.getTeamGuestGoals());
		eventLine.addComponent(right);
		right.setWidth("100%");

		eventLine.setExpandRatio(left, 0.5f);
		eventLine.setExpandRatio(right, 0.5f);
		tabLayout.addComponent(eventLine);
		eventLine.setWidth("100%");

		for (EventResult event : matchUpDetails.getEventList()) {
			eventLine = new HorizontalLayout();

			HorizontalLayout eventLayout = new HorizontalLayout();

			String eventCaption = "" + (event.getInstant() / 100) + ":"
					+ (event.getInstant() % 100) + " ";
			if (event.getFraction() == 0) {
				eventCaption += "1° half";
			} else if (event.getFraction() == 1) {
				eventCaption += "2° half";
			} else {
				eventCaption += "" + (event.getFraction() - 1) + "° sup.";
			}
			eventLayout.addComponent(new Label(eventCaption));
			Label spacer = new Label();
			spacer.setWidth("5px");
			eventLayout.addComponent(spacer);

			String basepath = VaadinService.getCurrent().getBaseDirectory()
					.getAbsolutePath();
			String imgName = null;
			if (event.getActionName().endsWith("goal")) {
				imgName = "goal_30x20";
			} else if (event.getActionName().equals("goal R")) {
				imgName = "goal_penalty_30x20";
			} else if (event.getActionName().startsWith("green")) {
				imgName = "green";
			} else if (event.getActionName().startsWith("yellow")) {
				imgName = "yellow";
			} else if (event.getActionName().startsWith("red")) {
				imgName = "red";
			} else if (event.getActionName().equals("1ª palla")) {
				imgName = "first_ball";
			}
			FileResource resource = new FileResource(new File(basepath
					+ "/WEB-INF/images/" + imgName + ".png"));
			eventLine.addComponent(new Label(eventCaption));
			Image img = new Image(null, resource);
			eventLayout.addComponent(img);
			eventCaption = "" + event.getPlayerInfo().getNumber() + " "
					+ event.getPlayerInfo().getPlayerName();
			eventLayout.addComponent(new Label(eventCaption));
			spacer = new Label("");
			spacer.setWidth("5px");
			eventLayout.addComponent(spacer);
			eventLayout.addComponent(new Label(" "
					+ event.getActionDescription()));
			Label eventLayoutSpacer = new Label("");
			eventLayoutSpacer.setWidth("100%");
			eventLayout.addComponent(eventLayoutSpacer);
			// eventLayout.setExpandRatio(eventLayoutSpacer, 1);

			eventLayout.setHeight("30px");

			// String eventString = "" + event.getShortString();
			// String empty = "";

			Label leftLabelMargin = new Label("");
			leftLabelMargin.setWidth("10px");
			Label rightLabelMargin = new Label("");
			rightLabelMargin.setWidth("10px");
			Label empty = new Label("");
			empty.setWidth("100%");
			empty.setHeight("30px");

			if (event.getPlayerInfo().getClubID() == mRes.getClubHostID()) {
				eventLine.addComponent(leftLabelMargin);
				eventLine.addComponent(eventLayout);
				eventLine.addComponent(rightLabelMargin);
				eventLine.addComponent(empty);

				eventLine.setExpandRatio(eventLayout, 0.5f);
				eventLine.setExpandRatio(empty, 0.5f);
			} else {
				eventLine.addComponent(leftLabelMargin);
				eventLine.addComponent(empty);
				eventLine.addComponent(rightLabelMargin);
				eventLine.addComponent(eventLayout);

				eventLine.setExpandRatio(eventLayout, 0.5f);
				eventLine.setExpandRatio(empty, 0.5f);
			}

			tabLayout.addComponent(eventLine);
			eventLine.setWidth("100%");
			eventLine.setHeight("30px");
		}

		Label eventSpacer = new Label("");
		eventSpacer.setHeight("100%");
		tabLayout.addComponent(eventSpacer);
		tabLayout.setExpandRatio(eventSpacer, 1);

		tabLayout.setSizeFull();

		tabs.addTab(tabLayout, "Events");

		// content of LineUps
		tabLayout = new VerticalLayout();

		HorizontalLayout tabLayoutSplitter = new HorizontalLayout();
		VerticalLayout leftSplitLayout = new VerticalLayout();
		VerticalLayout rightSplitLayout = new VerticalLayout();

		leftSplitLayout.addComponent(new Label(mRes.getTeamHostName()));
		rightSplitLayout.addComponent(new Label(mRes.getTeamGuestName()));

		VerticalLayout leftSplitSubLayout = new VerticalLayout();
		leftSplitSubLayout.setMargin(new MarginInfo(false, false, false, true));
		VerticalLayout rightSplitSubLayout = new VerticalLayout();
		rightSplitSubLayout
				.setMargin(new MarginInfo(false, false, false, true));

		for (PlayerMatchUpInfo playerInfo : matchUpDetails.getHostLineUp()) {
			HorizontalLayout playerLine = new HorizontalLayout();

			final int playerID = (int) playerInfo.getPlayerID();
			Button btn = new Button("", new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
					home.showPlayerCareerInfoSubWindow(playerID);
				}
			});

			String basepath = VaadinService.getCurrent().getBaseDirectory()
					.getAbsolutePath();
			FileResource resource = new FileResource(new File(basepath
					+ "/WEB-INF/images/magnifier.png"));
			btn.setIcon(resource);

			playerLine.addComponent(btn);
			Label smallSpacer = new Label("");
			smallSpacer.setWidth("10px");
			playerLine.addComponent(smallSpacer);
			playerLine.addComponent(new Label(playerInfo.getNumber() + " "
					+ playerInfo.getPlayerName()));

			leftSplitSubLayout.addComponent(playerLine);
		}

		for (PlayerMatchUpInfo playerInfo : matchUpDetails.getGuestLineUp()) {
			HorizontalLayout playerLine = new HorizontalLayout();

			final int playerID = (int) playerInfo.getPlayerID();
			Button btn = new Button("", new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
					home.showPlayerCareerInfoSubWindow(playerID);
				}
			});

			String basepath = VaadinService.getCurrent().getBaseDirectory()
					.getAbsolutePath();
			FileResource resource = new FileResource(new File(basepath
					+ "/WEB-INF/images/magnifier.png"));
			btn.setIcon(resource);

			playerLine.addComponent(btn);
			Label smallSpacer = new Label("");
			smallSpacer.setWidth("10px");
			playerLine.addComponent(smallSpacer);
			playerLine.addComponent(new Label(playerInfo.getNumber() + " "
					+ playerInfo.getPlayerName()));

			rightSplitSubLayout.addComponent(playerLine);
		}

		leftSplitLayout.addComponent(leftSplitSubLayout);
		rightSplitLayout.addComponent(rightSplitSubLayout);

		tabLayoutSplitter.addComponent(leftSplitLayout);
		tabLayoutSplitter.addComponent(rightSplitLayout);
		tabLayoutSplitter.setExpandRatio(leftSplitLayout, 0.5f);
		tabLayoutSplitter.setExpandRatio(rightSplitLayout, 0.5f);

		tabLayout.addComponent(tabLayoutSplitter);
		tabLayout.setExpandRatio(tabLayoutSplitter, 1);
		tabLayoutSplitter.setSizeFull();

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

		tabs.addTab(tabLayout, "Judge Staff");

		window.setContent(tabs);
		tabs.setSizeFull();

		final String mUpID = matchUpDetails.getResult().getMatchUpID();
		window.addCloseListener(new CloseListener() {
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
