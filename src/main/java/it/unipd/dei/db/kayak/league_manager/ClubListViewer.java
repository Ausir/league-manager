package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.Club;
import it.unipd.dei.db.kayak.league_manager.database.DML;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ClubListViewer {
	// private fields
	private VerticalLayout mainLayout;

	private List<Club> clubList;

	public ClubListViewer() {
		clubList = new ArrayList<Club>(DML.retrieveAllClubs());

		this.setUpContent();
	}

	private void setUpContent() {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(new MarginInfo(true, true, true, true));

		Label presentation = new Label("Club list");
		mainLayout.addComponent(presentation);

		VerticalLayout tableLayout = new VerticalLayout();
		tableLayout.setMargin(new MarginInfo(false, false, false, true));

		ClubTable clubTable = new ClubTable();

		for (Club c : clubList) {
			clubTable.addClub(c);
		}

		tableLayout.addComponent(clubTable);
		tableLayout.setExpandRatio(clubTable, 1);
		clubTable.setSizeFull();

		mainLayout.addComponent(tableLayout);
		mainLayout.setExpandRatio(tableLayout, 1);
		tableLayout.setSizeFull();

		mainLayout.setSizeFull();
	}

	public VerticalLayout getContent() {
		return mainLayout;
	}
}
