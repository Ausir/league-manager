package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.Tournament;
import it.unipd.dei.db.kayak.league_manager.database.DML;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

// create the layout for the all-tournaments table
public class TournamentListViewer {
	// private fields
	private VerticalLayout mainLayout;

	private List<Tournament> tournamentList;

	public TournamentListViewer() {
		MyVaadinUI ui=(MyVaadinUI) UI.getCurrent();
		tournamentList = new ArrayList<Tournament>(DML.retrieveAllTournaments(ui.getConnection())
				);

		this.setUpContent();
	}

	private void setUpContent() {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(new MarginInfo(true, true, true, true));

		Label presentation = new Label("Lista dei tornei");
		mainLayout.addComponent(presentation);

		VerticalLayout tableLayout = new VerticalLayout();
		tableLayout.setMargin(new MarginInfo(false, false, false, true));

		// create the table itself
		final TournamentTable tournamentTable = new TournamentTable(
				tournamentList);

		// create filter for search
		HorizontalLayout controlLayout = new HorizontalLayout();
		final TextField filterField = new TextField();
		controlLayout.addComponent(filterField);
		filterField.setImmediate(true);
		filterField.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				tournamentTable.filterTournamentNames(filterField.getValue());
			}
		});
		controlLayout.addComponent(new Button("Rierca per nome",
				new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						tournamentTable.filterTournamentNames(filterField
								.getValue());
					}
				}));
		tableLayout.addComponent(controlLayout);

		tableLayout.addComponent(tournamentTable);
		tableLayout.setExpandRatio(tournamentTable, 1);
		tournamentTable.setWidth("100%");

		mainLayout.addComponent(tableLayout);
		mainLayout.setExpandRatio(tableLayout, 1);
		tableLayout.setSizeFull();

		mainLayout.setSizeFull();
	}

	public VerticalLayout getContent() {
		return mainLayout;
	}
}
