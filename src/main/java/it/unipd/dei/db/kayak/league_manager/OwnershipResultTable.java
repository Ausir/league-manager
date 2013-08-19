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
	private static final long serialVersionUID = -7828718048828975569L;

	public OwnershipResultTable() {
		super("");

		Container ownershipContainer = new IndexedContainer();
		// ownershipContainer.addContainerProperty("PlayerID", Long.class,
		// null);
		// ownershipContainer.addContainerProperty("ClubID", Long.class, null);

		ownershipContainer.addContainerProperty("Club", String.class, null);
		ownershipContainer.addContainerProperty("Start", Date.class, null);
		ownershipContainer.addContainerProperty("End", Date.class, null);
		ownershipContainer
				.addContainerProperty("Borrowed", Boolean.class, null);
		ownershipContainer.addContainerProperty("", Button.class, null);

		this.setContainerDataSource(ownershipContainer);
		// this.addListener(ValueChangeEvent, target, method);
		// this.addGeneratedColumn("View", new ColumnGenerator() {
		// private static final long serialVersionUID = 8778605441493432562L;
		//
		// @Override
		// public Object generateCell(Table source, Object itemId,
		// Object columnId) {
		// // String clubName = (String) source.getItem(itemId)
		// // .getItemProperty("ClubName").getValue();
		// System.out.println("generateCell access item: "
		// + source.getItem(itemId));
		// System.out.println("generateCell found clubID: "
		// + (Long) source.getItem(itemId)
		// .getItemProperty("ClubID").getValue());
		// final long clubID = (Long) source.getItem(itemId)
		// .getItemProperty("ClubID").getValue();
		//
		// return new Button("View Club", new ClickListener() {
		// private static final long serialVersionUID = 894487094616791181L;
		//
		// @Override
		// public void buttonClick(ClickEvent event) {
		// Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
		// // home.showClubDetailsView((int) clubID);
		// }
		// });
		// }
		// });

		this.setVisibleColumns(new Object[] { "Club", "", "Start", "End",
				"Borrowed" });
	}

	public void addOwnershipResult(OwnershipResult ownershipResult) {
		final long clubID = ownershipResult.getClubID();
		Button btn = new Button("View Club", new ClickListener() {
			private static final long serialVersionUID = 894487094616791181L;

			@Override
			public void buttonClick(ClickEvent event) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				home.showClubDetailsView((int) clubID);
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
