package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.Club;
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

		final ClubTable clubTable = new ClubTable(clubList);
		
		HorizontalLayout controlLayout = new HorizontalLayout();
		final TextField filterField = new TextField();
		controlLayout.addComponent(filterField);
		filterField.setImmediate(true);
		filterField.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				clubTable.filterClubNames(filterField.getValue());
			}
		});
		controlLayout.addComponent(new Button("Filter Names",
				new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						clubTable.filterClubNames(filterField.getValue());
					}
				}));
		tableLayout.addComponent(controlLayout);

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
