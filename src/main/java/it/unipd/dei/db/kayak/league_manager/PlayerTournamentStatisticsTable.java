package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.PlayerTournamentStatistics;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

public class PlayerTournamentStatisticsTable extends Table {
	public PlayerTournamentStatisticsTable() {
		super("");

		Container statisticsContainer = (IndexedContainer) this
				.getContainerDataSource();
		statisticsContainer.addContainerProperty("Tournament Name", String.class,
				null);
		statisticsContainer.addContainerProperty("Tournament Year", Integer.class,
				null);
		statisticsContainer.addContainerProperty("", Button.class, null);// view
																		// tournament
		statisticsContainer.addContainerProperty("Club", String.class, null);
		statisticsContainer.addContainerProperty(" ", Button.class, null);// view
																		// club
		statisticsContainer.addContainerProperty("Matches", Integer.class, null);
		statisticsContainer.addContainerProperty("Goal", Integer.class, null);
		statisticsContainer.addContainerProperty("Green", Integer.class, null);
		statisticsContainer.addContainerProperty("Yellow", Integer.class, null);
		statisticsContainer.addContainerProperty("Red", Integer.class, null);

		this.setContainerDataSource(statisticsContainer);
	}

	public void addPlayerTournamentStatistics(PlayerTournamentStatistics tRes) {
		final String tournamentName = tRes.getTournamentName();
		final int tournamentYear = tRes.getTournamentYear();
		Button tournamentButton = new Button("View Club", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				home.showTournamentCalendarView(tournamentName, tournamentYear);
			}
		});

		final long clubID = tRes.getClubID();
		Button clubButton = new Button("View Club", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				home.showClubDetailsSubWindow(clubID);
			}
		});

		this.addItem(
				new Object[] { tournamentName, tournamentYear,
						tournamentButton, tRes.getClubName(), clubButton,
						tRes.getPlayedMatches(), tRes.getGoals(),
						tRes.getGreenCards(), tRes.getYellowCards(),
						tRes.getRedCards() }, tournamentName + tournamentYear);
	}

	public void removePlayerTournamentStatistics(String tournamentName,
			int tournamentYear) {
		this.removeItem(tournamentName + tournamentYear);
	}

	public boolean hasPlayerTournamentStatistics(String tournamentName,
			int tournamentYear) {
		return this.containsId(tournamentName + tournamentYear);
	}
}
