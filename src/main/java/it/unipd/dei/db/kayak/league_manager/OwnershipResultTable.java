package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.OwnershipResult;

import java.io.File;
import java.sql.Date;
import java.util.Collection;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

public class OwnershipResultTable extends Table {
	private StringPropertyFilter clubFilter;

	public OwnershipResultTable(Collection<OwnershipResult> ownershipResults) {
		super("");

		Container ownershipContainer = new IndexedContainer();

		ownershipContainer.addContainerProperty("", Button.class, null);
		ownershipContainer.addContainerProperty("Club", String.class, null);
		ownershipContainer.addContainerProperty("Start", Date.class, null);
		ownershipContainer.addContainerProperty("End", Date.class, null);
		ownershipContainer
				.addContainerProperty("Borrowed", Boolean.class, null);

		this.setContainerDataSource(ownershipContainer);

		for (OwnershipResult o : ownershipResults) {
			this.addOwnershipResult(o);
		}

		Filterable filterable = (Filterable) ownershipContainer;
		clubFilter = new StringPropertyFilter("", "Club");
		filterable.addContainerFilter(clubFilter);

		this.setColumnExpandRatio("", 0);
		this.setColumnWidth("", 45);
		this.setColumnExpandRatio("Club", 1);
		this.setColumnExpandRatio("Start", 1);
		this.setColumnExpandRatio("End", 1);
		this.setColumnExpandRatio("Borrowed", 1);
	}

	public void filterClubNames(String text) {
		if (!text.toLowerCase().equals(clubFilter.getFilter().toLowerCase())) {
			Filterable container = (Filterable) this.getContainerDataSource();
			container.removeContainerFilter(clubFilter);
			clubFilter.setFilter(text);
			container.addContainerFilter(clubFilter);
		}
	}

	public void addOwnershipResult(OwnershipResult ownershipResult) {
		final long clubID = ownershipResult.getClubID();
		Button btn = new Button("", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				home.showClubDetailsSubWindow(clubID);
			}
		});

		String basepath = VaadinService.getCurrent().getBaseDirectory()
				.getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath
				+ "/WEB-INF/images/magnifier.png"));
		btn.setIcon(resource);

		this.addItem(new Object[] { btn, ownershipResult.getClubName(),
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
