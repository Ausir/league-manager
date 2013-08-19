package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.Tournament;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

public class TournamentTable extends Table {
	private static final long serialVersionUID = -2495524358124554276L;

	public TournamentTable() {
		super("");

		Container tournamentContainer = new IndexedContainer();
		tournamentContainer.addContainerProperty("Name", String.class, null);
		tournamentContainer.addContainerProperty("Year", Integer.class, null);
		tournamentContainer.addContainerProperty("Sex", String.class, null);
		tournamentContainer
				.addContainerProperty("Max Age", Integer.class, null);
		tournamentContainer.addContainerProperty("Organizer Email",
				String.class, null);

		this.setContainerDataSource(tournamentContainer);
		this.addGeneratedColumn("", new ColumnGenerator() {
			private static final long serialVersionUID = 8778605441493432562L;

			@Override
			public Object generateCell(Table source, Object itemId,
					Object columnId) {
				Item item = source.getItem(itemId);
				boolean sex = ((String) item.getItemProperty("Sex").getValue()) == "M" ? true
						: false;
				final Tournament t = new Tournament((String) item
						.getItemProperty("Name").getValue(), (Integer) item
						.getItemProperty("Year").getValue(), (Integer) item
						.getItemProperty("Max Age").getValue(), sex,
						(String) item.getItemProperty("Organizer Email")
								.getValue());
				return new Button("View Tournament", new ClickListener() {
					private static final long serialVersionUID = 894487094616791181L;

					@Override
					public void buttonClick(ClickEvent event) {
						Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
						home.showTournamentCalendarView(t);
					}
				});
			}
		});
	}

	public void addTournament(Tournament tournament) {
		this.addItem(new Object[] { tournament.getName(), tournament.getYear(),
				tournament.getSex() ? "M" : "F", tournament.getMaxAge(),
				tournament.getOrganizerEmail() }, tournament.getName()
				+ tournament.getYear());
	}

	public void removeTournament(String tournamentName, int tournamentYear) {
		this.removeItem(tournamentName + tournamentYear);
	}

	public boolean hasTournament(String tournamentName, int tournamentYear) {
		return this.containsId(tournamentName + tournamentYear);
	}
}
