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
	private static final long serialVersionUID = 8358667981809849010L;

	public PlayerTable() {
		super("");

		Container playerContainer = (IndexedContainer) this
				.getContainerDataSource();
		playerContainer.addContainerProperty("ID", Long.class, null);
		playerContainer.addContainerProperty("First Name", String.class, null);
		playerContainer.addContainerProperty("Last Name", String.class, null);
		playerContainer.addContainerProperty("Birthday", Date.class, null);

		this.setContainerDataSource(playerContainer);
		this.addGeneratedColumn("", new ColumnGenerator() {
			private static final long serialVersionUID = 8778605441493432562L;

			@Override
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				final int playerID = ((Long) itemId).intValue();
				return new Button("View Player", new ClickListener() {
					private static final long serialVersionUID = 894487094616791181L;

					@Override
					public void buttonClick(ClickEvent event) {
						Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
						home.showPlayerCareerInfoSubWindow(playerID);
					}
				});
			}
		});
	}

	public void addPlayer(Player player) {
		this.addItem(new Object[] { player.getID(), player.getFirstName(),
				player.getLastName(), player.getBirthday() }, player.getID());
	}

	public void removePlayer(long playerID) {
		this.removeItem(playerID);
	}

	public boolean hasPlayer(long playerID) {
		return this.containsId(playerID);
	}
}
