package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.Club;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

public class ClubTable extends Table {
	public ClubTable() {
		super("");

		Container clubContainer = (IndexedContainer) this
				.getContainerDataSource();
		clubContainer.addContainerProperty("Name", String.class, null);
		clubContainer.addContainerProperty("Phone", String.class, null);
		clubContainer.addContainerProperty("Address", String.class, null);
		clubContainer.addContainerProperty("Website", String.class, null);
		clubContainer.addContainerProperty("Email", String.class, null);
		clubContainer.addContainerProperty("", Button.class, null);

		this.setContainerDataSource(clubContainer);
	}

	public void addClub(Club club) {
		final long clubID = club.getID();
		Button btn = new Button("View Club", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				home.showClubDetailsSubWindow(clubID);
			}
		});

		this.addItem(
				new Object[] { club.getName(), club.getPhone(),
						club.getAddress(), club.getWebsite(), club.getEmail(),
						btn }, club.getID());
	}

	public void removeClub(long clubID) {
		this.removeItem(clubID);
	}

	public boolean hasClub(long clubID) {
		return this.containsId(clubID);
	}
}
