package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.Player;

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

public class PlayerTable extends Table {
	private StringPropertyFilter nameFilter;
	
	private PlayerTable() {
	}

	public PlayerTable(Collection<Player> players) {
		super("");

		Container playerContainer = (IndexedContainer) this
				.getContainerDataSource();

		playerContainer.addContainerProperty("", Button.class, null);
		playerContainer.addContainerProperty("Name", String.class, null);
		playerContainer.addContainerProperty("Birthday", Date.class, null);

		this.setContainerDataSource(playerContainer);

		for (Player p:players) {
			this.addPlayer(p);
		}
		
		Filterable filterable = (Filterable) playerContainer;
		nameFilter = new StringPropertyFilter("", "Name");
		filterable.addContainerFilter(nameFilter);

		this.setColumnExpandRatio("", 0);
		this.setColumnWidth("", 45);
		this.setColumnExpandRatio("Name", 1);
		this.setColumnExpandRatio("Birthday", 1);
	}

	public void filterPlayerNames(String text) {
		if (!text.toLowerCase().equals(nameFilter.getFilter().toLowerCase())) {
			Filterable container = (Filterable) this.getContainerDataSource();
			container.removeContainerFilter(nameFilter);
			nameFilter.setFilter(text);
			container.addContainerFilter(nameFilter);
		}
	}

	public void addPlayer(Player player) {
		final long playerID = player.getID();
		Button btn = new Button("", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				home.showPlayerCareerInfoSubWindow(playerID);
			}
		});

		String basepath = VaadinService.getCurrent().getBaseDirectory()
				.getAbsolutePath();
		FileResource resource = new FileResource(new File(basepath
				+ "/WEB-INF/images/magnifier.png"));
		btn.setIcon(resource);

		this.addItem(
				new Object[] { btn, player.getName(), player.getBirthday() },
				player.getID());
	}

	public void removePlayer(long playerID) {
		this.removeItem(playerID);
	}

	public boolean hasPlayer(long playerID) {
		return this.containsId(playerID);
	}
}
