package it.unipd.dei.db.kayak.league_manager;

import java.io.File;
import java.util.Collection;

import it.unipd.dei.db.kayak.league_manager.data.Club;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

public class ClubTable extends Table {
	private StringPropertyFilter nameFilter;

	// private StringPropertyFilter phoneFilter;
	// private StringPropertyFilter addressFilter;
	// private StringPropertyFilter websiteFilter;
	// private StringPropertyFilter emailFilter;
	
	private ClubTable() {
	}

	public ClubTable(Collection<Club> clubs) {
		super("");

		Container clubContainer = (IndexedContainer) this
				.getContainerDataSource();
		clubContainer.addContainerProperty("", Button.class, null);
		clubContainer.addContainerProperty("Name", String.class, null);
		clubContainer.addContainerProperty("Address", String.class, null);
		clubContainer.addContainerProperty("Website", String.class, null);
		clubContainer.addContainerProperty("Email", String.class, null);
		clubContainer.addContainerProperty("Phone", String.class, null);

		this.setContainerDataSource(clubContainer);

		for (Club c : clubs) {
			this.addClub(c);
		}

		Filterable filterable = (Filterable) clubContainer;
		nameFilter = new StringPropertyFilter("", "Name");
		filterable.addContainerFilter(nameFilter);
		// phoneFilter = new StringPropertyFilter("", "Phone");
		// filterable.addContainerFilter(phoneFilter);
		// addressFilter = new StringPropertyFilter("", "Address");
		// filterable.addContainerFilter(addressFilter);
		// websiteFilter = new StringPropertyFilter("", "Website");
		// filterable.addContainerFilter(websiteFilter);
		// emailFilter = new StringPropertyFilter("", "Email");
		// filterable.addContainerFilter(emailFilter);

		this.setColumnExpandRatio("", 0);
		this.setColumnWidth("", 45);
		this.setColumnExpandRatio("Name", 1);
		this.setColumnExpandRatio("Address", 1);
		this.setColumnExpandRatio("Website", 1);
		this.setColumnExpandRatio("Email", 1);
		this.setColumnExpandRatio("Phone", 0);
	}

	public void filterClubNames(String text) {
		if (!text.toLowerCase().equals(nameFilter.getFilter().toLowerCase())) {
			Filterable container = (Filterable) this.getContainerDataSource();
			container.removeContainerFilter(nameFilter);
			nameFilter.setFilter(text);
			container.addContainerFilter(nameFilter);
		}
	}

	public void addClub(Club club) {
		final long clubID = club.getID();
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

		this.addItem(new Object[] { btn, club.getName(), club.getAddress(),
				club.getWebsite(), club.getEmail(), club.getPhone() },
				club.getID());
	}

	public void removeClub(long clubID) {
		this.removeItem(clubID);
	}

	public boolean hasClub(long clubID) {
		return this.containsId(clubID);
	}
}
