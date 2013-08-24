package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.Tournament;
import it.unipd.dei.db.kayak.league_manager.database.DML;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class TournamentListViewer {
	// private fields
	private VerticalLayout mainLayout;

	private List<Tournament> tournamentList;

	public TournamentListViewer() {
		tournamentList = new ArrayList<Tournament>(
				DML.retrieveAllTournaments());

		this.setUpContent();
	}

	private void setUpContent() {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(new MarginInfo(true, true, true, true));

		Label presentation = new Label("Tournament list");
		mainLayout.addComponent(presentation);

		VerticalLayout tableLayout = new VerticalLayout();
		tableLayout.setMargin(new MarginInfo(false, false, false, true));

		TournamentTable tournamentTable = new TournamentTable();

		for (Tournament t : tournamentList) {
			tournamentTable.addTournament(t);
		}

		tableLayout.addComponent(tournamentTable);
		tableLayout.setExpandRatio(tournamentTable, 1);
		tournamentTable.setSizeFull();

		mainLayout.addComponent(tableLayout);
		mainLayout.setExpandRatio(tableLayout, 1);
		tableLayout.setSizeFull();

		mainLayout.setSizeFull();
	}

	public VerticalLayout getContent() {
		return mainLayout;
	}
}
