package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.FakeDataWarehouse;
import it.unipd.dei.db.kayak.league_manager.data.Player;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class PlayerListViewer {
	// private fields
	private VerticalLayout mainLayout;

	private List<Player> playerList;

	public PlayerListViewer() {
		playerList = new ArrayList<Player>(FakeDataWarehouse.getPlayers());

		this.setUpContent();
	}

	private void setUpContent() {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(new MarginInfo(true, true, true, true));

		Label presentation = new Label("Player list");
		mainLayout.addComponent(presentation);

		VerticalLayout tableLayout = new VerticalLayout();
		tableLayout.setMargin(new MarginInfo(false, false, false, true));

		PlayerTable playerTable = new PlayerTable();

		for (Player p : playerList) {
			playerTable.addPlayer(p);
		}

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
