package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.OwnershipResult;

import java.sql.Date;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

public class OwnershipResultTable extends Table {
	public OwnershipResultTable() {
		super("");

		Container ownershipContainer = new IndexedContainer();

		ownershipContainer.addContainerProperty("Club", String.class, null);
		ownershipContainer.addContainerProperty("Start", Date.class, null);
		ownershipContainer.addContainerProperty("End", Date.class, null);
		ownershipContainer
				.addContainerProperty("Borrowed", Boolean.class, null);
		ownershipContainer.addContainerProperty("", Button.class, null);

		this.setContainerDataSource(ownershipContainer);

		this.setVisibleColumns(new Object[] { "Club", "", "Start", "End",
				"Borrowed" });
	}

	public void addOwnershipResult(OwnershipResult ownershipResult) {
		final long clubID = ownershipResult.getClubID();
		Button btn = new Button("View Club", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				home.showClubDetailsSubWindow(clubID);
			}
		});

		this.addItem(
				new Object[] {// ownershipResult.getPlayerID(),
						// ownershipResult.getClubID(),
						ownershipResult.getClubName(), btn,
						ownershipResult.getStart(), ownershipResult.getEnd(),
						ownershipResult.isBorrowed() },
				ownershipResult.getOwnershipID());
	}

	public void removeOwnershipResult(OwnershipResult ownershipResult) {
		this.removeItem(ownershipResult.getOwnershipID());
	}

	public boolean hasOwnershipResult(OwnershipResult ownershipResult) {
		return this.containsId(ownershipResult.getOwnershipID());
	}
}
