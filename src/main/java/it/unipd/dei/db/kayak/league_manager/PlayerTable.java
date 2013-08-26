package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.Player;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

// table for player presentation with filter over name
public class PlayerTable extends Table {
	private StringPropertyFilter nameFilter;

	@Override
    protected String formatPropertyValue(Object rowId,
            Object colId, Property<?> property) {
        // Format by property type
        if (property.getType() == Date.class) {
            SimpleDateFormat df =
                new SimpleDateFormat("dd-MM-yyyy");
            return df.format((Date)property.getValue());
        }

        return super.formatPropertyValue(rowId, colId, property);
    }

	public PlayerTable(Collection<Player> players) {
		super("");

		Container playerContainer = (IndexedContainer) this
				.getContainerDataSource();

		playerContainer.addContainerProperty("", Button.class, null);
		playerContainer.addContainerProperty("name", String.class, null);
		playerContainer.addContainerProperty("birthday", Date.class, null);

		this.setContainerDataSource(playerContainer);
		
		this.setColumnHeader("name", "nome");
		this.setColumnHeader("birthday", "data di nascita");


		for (Player p:players) {
			this.addPlayer(p);
		}

		Filterable filterable = (Filterable) playerContainer;
		nameFilter = new StringPropertyFilter("", "name");
		filterable.addContainerFilter(nameFilter);

		this.setColumnExpandRatio("", 0);
		this.setColumnWidth("", 45);
		this.setColumnExpandRatio("Nome", 1);
		this.setColumnExpandRatio("Data di nascita", 1);
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
			private static final long serialVersionUID = 1L;

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
