package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.FakeDataWarehouse;
import it.unipd.dei.db.kayak.league_manager.data.Player;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class PlayerTableViewer {
	// private fields
	private VerticalLayout mainLayout;

	private List<Player> playerList;

	public PlayerTableViewer() {
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

		Container playerContainer = new IndexedContainer();
		playerContainer.addContainerProperty("ID", Long.class, null);
		playerContainer.addContainerProperty("First Name", String.class, null);
		playerContainer.addContainerProperty("Last Name", String.class, null);
		playerContainer.addContainerProperty("Birthday", Date.class, null);

		Table playerTable = new Table("", playerContainer);
		playerTable.addGeneratedColumn("", new ColumnGenerator() {
			private static final long serialVersionUID = 8778605441493432562L;

			@Override
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				final int playerID = ((Long) itemId).intValue();
				return new Button("Open Details", new ClickListener() {
					private static final long serialVersionUID = 894487094616791181L;

					@Override
					public void buttonClick(ClickEvent event) {
						Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
						home.showPlayerCareerInfoSubWindow(playerID);
					}
				});
			}
		});

		for (Player p : playerList) {
			playerTable.addItem(
					new Object[] { p.getID(), p.getFirstName(),
							p.getLastName(), p.getBirthday() }, p.getID());
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
