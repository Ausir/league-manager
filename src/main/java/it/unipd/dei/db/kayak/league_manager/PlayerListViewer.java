package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.Player;
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

public class PlayerListViewer {
	// private fields
	private VerticalLayout mainLayout;

	private List<Player> playerList;

	public PlayerListViewer() {
		playerList = new ArrayList<Player>(DML.retrieveAllPlayers()
		// FakeDataWarehouse.getPlayers()
		);

		this.setUpContent();
	}

	private void setUpContent() {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(new MarginInfo(true, true, true, true));

		Label presentation = new Label("Player list");
		mainLayout.addComponent(presentation);

		VerticalLayout tableLayout = new VerticalLayout();
		tableLayout.setMargin(new MarginInfo(false, false, false, true));

		final PlayerTable playerTable = new PlayerTable();

		HorizontalLayout controlLayout = new HorizontalLayout();
		final TextField filterField = new TextField();
		controlLayout.addComponent(filterField);
		filterField.setImmediate(true);
		filterField.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				playerTable.filterPlayerNames(filterField.getValue());
			}
		});
		controlLayout.addComponent(new Button("Filter Names",
				new ClickListener() {
					@Override
					public void buttonClick(ClickEvent event) {
						playerTable.filterPlayerNames(filterField.getValue());
					}
				}));
		tableLayout.addComponent(controlLayout);
//		long start = System.currentTimeMillis();
		for (Player p : playerList) {
			playerTable.addPlayer(p);
		}
//		long elapsed = System.currentTimeMillis() - start;
//		System.out.println(elapsed);

		tableLayout.addComponent(playerTable);
		tableLayout.setExpandRatio(playerTable, 1);
		playerTable.setSizeFull();

		mainLayout.addComponent(tableLayout);
		mainLayout.setExpandRatio(tableLayout, 1);
		tableLayout.setSizeFull();

		mainLayout.setSizeFull();
	}

	public VerticalLayout getContent() {
		return mainLayout;
	}
}
