package it.unipd.dei.db.kayak.league_manager;

import java.io.File;

import it.unipd.dei.db.kayak.league_manager.data.OwnershipResult;
import it.unipd.dei.db.kayak.league_manager.data.Player;
import it.unipd.dei.db.kayak.league_manager.data.PlayerCareerEvent;
import it.unipd.dei.db.kayak.league_manager.data.PlayerCareerInfo;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
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

		tabLayout.addComponent(new Label("Career events"));

		VerticalLayout careerLayout = new VerticalLayout();
		careerLayout.setMargin(new MarginInfo(false, false, false, true));
		tabLayout.addComponent(careerLayout);

		VerticalLayout tLayout = null;
		String tName = "";
		int tYear = 0;

		VerticalLayout mDayLayout = null;
		String mDayID = "";

		VerticalLayout mUpLayout = null;
		String mUpID = "";

		for (int eventIdx = 0; eventIdx < playerInfo.getCareerEvents().size(); ++eventIdx) {
			PlayerCareerEvent cEvent = playerInfo.getCareerEvents().get(
					eventIdx);

			if (!cEvent.getTournamentName().equals(tName)
					|| cEvent.getTournamentYear() != tYear) {
				tName = cEvent.getTournamentName();
				tYear = cEvent.getTournamentYear();

				tLayout = new VerticalLayout();
				tLayout.setMargin(new MarginInfo(false, false, false, true));

				String tCaption = tName + " " + tYear + " played for ";
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

				mDayLayout = new VerticalLayout();
				mDayLayout.setMargin(new MarginInfo(false, false, false, true));

				tLayout.addComponent(new Label(""
						+ cEvent.getMatchDayStartDate() + " - "
						+ cEvent.getMatchDayEndDate()));

				tLayout.addComponent(mDayLayout);
			}

			if (!cEvent.getMatchUpID().equals(mUpID)) {
				mUpID = cEvent.getMatchUpID();

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

			String eventCaption = "" + (cEvent.getInstant() / 100) + "' ";
			if (cEvent.getFraction() == 0) {
				eventCaption = "1° half";
			} else if (cEvent.getFraction() == 1) {
				eventCaption = "2° half";
			} else {
				eventCaption = "" + (cEvent.getFraction() - 1) + "° sup.";
			}
			eventCaption += " " + cEvent.getActionDisplay();

			mUpLayout.addComponent(new Label(eventCaption));
		}

		tabLayout.addComponent(careerLayout);
		tabLayout.setExpandRatio(careerLayout, 1);

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
