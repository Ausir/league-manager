package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.Tournament;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

public class TournamentTable extends Table {
	public TournamentTable() {
		super("");

		Container tournamentContainer = new IndexedContainer();

		tournamentContainer.addContainerProperty("Name", String.class, null);
		tournamentContainer.addContainerProperty("Year", Integer.class, null);
		tournamentContainer.addContainerProperty("Sex", String.class, null);
		tournamentContainer
				.addContainerProperty("Max Age", Integer.class, null);
		tournamentContainer.addContainerProperty("Organizer Email",
				String.class, null);
		tournamentContainer.addContainerProperty("", Button.class, null);

		this.setContainerDataSource(tournamentContainer);
	}

	public void addTournament(Tournament tournament) {
		final String tournamentName = tournament.getName();
		final int tournamentYear = tournament.getYear();
		Button btn = new Button("View Tournament", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				home.showTournamentCalendarView(tournamentName, tournamentYear);
			}
		});

		this.addItem(new Object[] { tournament.getName(), tournament.getYear(),
				tournament.getSex() ? "M" : "F", tournament.getMaxAge(),
				tournament.getOrganizerEmail(), btn }, tournament.getName()
				+ tournament.getYear());
	}

	public void removeTournament(String tournamentName, int tournamentYear) {
		this.removeItem(tournamentName + tournamentYear);
	}

	public boolean hasTournament(String tournamentName, int tournamentYear) {
		return this.containsId(tournamentName + tournamentYear);
	}
}
