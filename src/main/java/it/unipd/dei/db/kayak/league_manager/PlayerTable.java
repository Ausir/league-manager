package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.Player;

import java.sql.Date;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

public class PlayerTable extends Table {
	public PlayerTable() {
		super("");

		Container playerContainer = (IndexedContainer) this
				.getContainerDataSource();

		// playerContainer.addContainerProperty("First Name", String.class,
		// null);
		// playerContainer.addContainerProperty("Last Name", String.class,
		// null);
		playerContainer.addContainerProperty("Name", String.class, null);
		playerContainer.addContainerProperty("Birthday", Date.class, null);
		playerContainer.addContainerProperty("", Button.class, null);

		this.setContainerDataSource(playerContainer);
	}

	public void addPlayer(Player player) {
		final long playerID = player.getID();
		Button btn = new Button("View Player Career", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				home.showPlayerCareerInfoSubWindow(playerID);
			}
		});

		this.addItem(new Object[] {
				// player.getFirstName(),
				// player.getLastName(),
				player.getName(), player.getBirthday(), btn }, player.getID());
	}

	public void removePlayer(long playerID) {
		this.removeItem(playerID);
	}

	public boolean hasPlayer(long playerID) {
		return this.containsId(playerID);
	}
}
