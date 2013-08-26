package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.Player;
import it.unipd.dei.db.kayak.league_manager.data.PlayerCareerEvent;
import it.unipd.dei.db.kayak.league_manager.data.PlayerCareerInfo;

import java.io.File;

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

public class PlayerCareerInfoSubWindow {
	// private fields
	private Window window;

	private PlayerCareerInfo playerInfo;

	public PlayerCareerInfoSubWindow(PlayerCareerInfo playerInfo) {
		this.playerInfo = playerInfo;

		// Collections.sort(playerInfo.getOwnerships(),
		// new OwnershipResultDateComparator());

		// Collections.sort(playerInfo.getEvents(), new
		// EventResultTimeComparator(
		// true));

		this.setUpWindow();
	}

	public void setUpWindow() {
		Player player = playerInfo.getPlayerData();

		window = new Window();
		window.setCaption(player.getID() + " - " + player.getName());
		window.setHeight("400px");
		window.setWidth("600px");

		TabSheet tabs = new TabSheet();

		// content of Player Info
		VerticalLayout tabLayout = new VerticalLayout();
		// tabLayout.setMargin(new MarginInfo(false, false, false, true));

		tabLayout.addComponent(new Label(player.getID() + " - "
				+ player.getName()));
		tabLayout.addComponent(new Label("Data di nascita: " + player.getBirthday()));

		Label spacer = new Label();
		tabLayout.addComponent(spacer);
		tabLayout.setExpandRatio(spacer, 1);

		tabLayout.setSizeFull();

		tabs.addTab(tabLayout, "Informazioni del giocatore");

		// Ownerships section
		tabLayout = new VerticalLayout();
		tabLayout.setMargin(new MarginInfo(false, false, true, false));

		tabLayout.addComponent(new Label("Contratti"));

		VerticalLayout tableLayout = new VerticalLayout();
		tableLayout.setMargin(new MarginInfo(false, false, false, true));

		OwnershipResultTable ownershipTable = new OwnershipResultTable(
				playerInfo.getOwnerships());

		tableLayout.addComponent(ownershipTable);
		tableLayout.setExpandRatio(ownershipTable, 1);
		ownershipTable.setSizeFull();

		tabLayout.addComponent(tableLayout);
		tabLayout.setExpandRatio(tableLayout, 1);
		tableLayout.setSizeFull();

		tabLayout.setSizeFull();

		tabs.addTab(tabLayout, "Contratti");

		// Events section
		tabLayout = new VerticalLayout();

		tabLayout.addComponent(new Label("Eventi di carriera"));

		VerticalLayout careerLayout = new VerticalLayout();
		careerLayout.setMargin(new MarginInfo(false, false, false, true));
		tabLayout.addComponent(careerLayout);

		VerticalLayout tLayout = null;
		boolean chainT = false;
		String tName = "";
		int tYear = 0;

		VerticalLayout mDayLayout = null;
		boolean chainMDay = false;
		String mDayID = "";

		VerticalLayout mUpLayout = null;
		boolean chainMUp = false;
		String mUpID = "";

		for (int eventIdx = 0; eventIdx < playerInfo.getCareerEvents().size(); ++eventIdx) {
			PlayerCareerEvent cEvent = playerInfo.getCareerEvents().get(
					eventIdx);

			if (!cEvent.getTournamentName().equals(tName)
					|| cEvent.getTournamentYear() != tYear) {
				tName = cEvent.getTournamentName();
				tYear = cEvent.getTournamentYear();

				if (tLayout != null && chainT) {
					tLayout.setMargin(new MarginInfo(false, false, true, true));
				}
				chainT = true;
				chainMDay = false;
				chainMUp = false;
				tLayout = new VerticalLayout();
				tLayout.setMargin(new MarginInfo(false, false, false, true));

				String tCaption = tName + " " + tYear + " giocato per ";
				if (cEvent.getPlayerClubID() == cEvent.getHostClubID()) {
					tCaption += cEvent.getHostClubName();
				} else {
					tCaption += cEvent.getGuestClubName();
				}

				HorizontalLayout tControlLayout = new HorizontalLayout();

				final String ftName = tName;
				final int ftYear = tYear;
				Button btn = new Button("", new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
						home.showTournamentCalendarView(ftName, ftYear);
					}
				});

				String basepath = VaadinService.getCurrent().getBaseDirectory()
						.getAbsolutePath();
				FileResource resource = new FileResource(new File(basepath
						+ "/WEB-INF/images/magnifier.png"));
				btn.setIcon(resource);

				tControlLayout.addComponent(btn);
				Label smallSpacer = new Label("");
				smallSpacer.setWidth("10px");
				tControlLayout.addComponent(smallSpacer);
				tControlLayout.addComponent(new Label(tCaption));
				careerLayout.addComponent(tControlLayout);

				careerLayout.addComponent(tLayout);
			}

			if (!cEvent.getMatchDayID().equals(mDayID)) {
				mDayID = cEvent.getMatchDayID();

				if (mDayLayout != null && chainMDay) {
					mDayLayout.setMargin(new MarginInfo(false, false, true,
							true));
				}
				chainMDay = true;
				chainMUp = false;
				mDayLayout = new VerticalLayout();
				mDayLayout.setMargin(new MarginInfo(false, false, false, true));

				tLayout.addComponent(new Label(""
						+ cEvent.getMatchDayStartDate() + " - "
						+ cEvent.getMatchDayEndDate()));

				tLayout.addComponent(mDayLayout);
			}

			if (!cEvent.getMatchUpID().equals(mUpID)) {
				mUpID = cEvent.getMatchUpID();

				if (mUpLayout != null && chainMUp) {
					mUpLayout
							.setMargin(new MarginInfo(false, false, true, true));
				}
				chainMUp = true;
				mUpLayout = new VerticalLayout();
				mUpLayout.setMargin(new MarginInfo(false, false, false, true));

				HorizontalLayout mUpControlLayout = new HorizontalLayout();

				final String fmUpID = mUpID;
				Button btn = new Button("", new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
						home.showMatchUpDetailsSubWindow(fmUpID);
					}
				});

				String basepath = VaadinService.getCurrent().getBaseDirectory()
						.getAbsolutePath();
				FileResource resource = new FileResource(new File(basepath
						+ "/WEB-INF/images/magnifier.png"));
				btn.setIcon(resource);

				mUpControlLayout.addComponent(btn);
				Label smallSpacer = new Label("");
				smallSpacer.setWidth("10px");
				mUpControlLayout.addComponent(smallSpacer);
				mUpControlLayout.addComponent(new Label(cEvent
						.getHostClubName()
						+ " "
						+ cEvent.getHostGoals()
						+ " - "
						+ cEvent.getGuestClubName()
						+ " "
						+ cEvent.getGuestGoals()));
				mDayLayout.addComponent(mUpControlLayout);

				mDayLayout.addComponent(mUpLayout);
			}

			HorizontalLayout eventLine = new HorizontalLayout();

			int min = cEvent.getInstant() / 100;
			Integer sec = cEvent.getInstant() % 100;
			String secondi;
			if(sec < 10) secondi = "0"+sec;
			else secondi = sec.toString();
			String eventCaption = "" + min + ":"
					+ secondi + " ";
			if (cEvent.getFraction() == 1) {
				eventCaption += "1° tempo";
			} else if (cEvent.getFraction() == 2) {
				eventCaption += "2° tempo";
			} else {
				eventCaption += "" + (cEvent.getFraction() - 2) + "° sup.";
			}

			String basepath = VaadinService.getCurrent().getBaseDirectory()
					.getAbsolutePath();
			String actionName = cEvent.getActionDisplay();
			String imgName = null;
			if (actionName.endsWith("goal")) {
				imgName = "goal_30x20";
			} else if (actionName.equals("goal R")) {
				imgName = "goal_penalty_30x20";
			} else if (actionName.startsWith("green")) {
				imgName = "green";
			} else if (actionName.startsWith("yellow")) {
				imgName = "yellow";
			} else if (actionName.startsWith("red")) {
				imgName = "red";
			} else if (actionName.equals("1ª palla")) {
				imgName = "first_ball";
			}
			FileResource resource = new FileResource(new File(basepath
					+ "/WEB-INF/images/" + imgName + ".png"));

			eventLine.addComponent(new Label(eventCaption));
			spacer = new Label("");
			spacer.setWidth("5px");
			eventLine.addComponent(spacer);
			Image img = new Image(null, resource);
			eventLine.addComponent(img);
			spacer = new Label("");
			spacer.setWidth("5px");
			eventLine.addComponent(spacer);
			eventLine.addComponent(new Label(" " + cEvent.getActionDisplay()));

			// eventCaption += " " + cEvent.getActionDisplay();

			mUpLayout.addComponent(eventLine);
		}

		tabLayout.addComponent(careerLayout);
		tabLayout.setExpandRatio(careerLayout, 1);

		tabLayout.setSizeFull();

		tabs.addTab(tabLayout, "Eventi");

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
