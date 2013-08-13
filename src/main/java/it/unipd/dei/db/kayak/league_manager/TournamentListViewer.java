package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.FakeDataWarehouse;
import it.unipd.dei.db.kayak.league_manager.data.Tournament;
import it.unipd.dei.db.kayak.league_manager.data_utils.TournamentYearComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class TournamentListViewer {
	// private fields
	private VerticalLayout mainLayout;

	private List<Tournament> tournamentList;

	public TournamentListViewer() {
		tournamentList = new ArrayList<Tournament>(
				FakeDataWarehouse.getTournaments());
		Collections.sort(tournamentList, new TournamentYearComparator(false));

		this.setUpContent();
	}

	private void setUpContent() {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(new MarginInfo(true, true, true, true));

		Label presentation = new Label("Tournament list");
		mainLayout.addComponent(presentation);

		VerticalLayout tournamentLayout = new VerticalLayout();
		tournamentLayout.setMargin(new MarginInfo(false, false, false, true));
		for (Tournament t : tournamentList) {
			String tournamentCaption = t.getName() + " " + t.getYear();
			final Tournament tournament = t;
			Button tournamentButton = new Button(tournamentCaption,
					new ClickListener() {
						private static final long serialVersionUID = -8006110621462013098L;

						@Override
						public void buttonClick(ClickEvent event) {
							Home home = ((MyVaadinUI) UI.getCurrent())
									.getHome();
							home.showTournamentCalendarView(tournament);
						}
					});

			tournamentLayout.addComponent(tournamentButton);
		}
		mainLayout.addComponent(tournamentLayout);

		Label spacer = new Label();
		mainLayout.addComponent(spacer);
		mainLayout.setExpandRatio(spacer, 1);
	}

	public VerticalLayout getContent() {
		return mainLayout;
	}
}
