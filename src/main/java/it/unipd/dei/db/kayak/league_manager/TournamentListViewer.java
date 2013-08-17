package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.FakeDataWarehouse;
import it.unipd.dei.db.kayak.league_manager.data.Tournament;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
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

public class TournamentListViewer {
	// private fields
	private VerticalLayout mainLayout;

	private List<Tournament> tournamentList;

	public TournamentListViewer() {
		tournamentList = new ArrayList<Tournament>(
				FakeDataWarehouse.getTournaments());
		// Collections.sort(tournamentList, new
		// TournamentYearComparator(false));

		this.setUpContent();
	}

	private void setUpContent() {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(new MarginInfo(true, true, true, true));

		Label presentation = new Label("Tournament list");
		mainLayout.addComponent(presentation);

		VerticalLayout tableLayout = new VerticalLayout();
		tableLayout.setMargin(new MarginInfo(false, false, false, true));

		Container tournamentContainer = new IndexedContainer();
		tournamentContainer.addContainerProperty("Name", String.class, null);
		tournamentContainer.addContainerProperty("Year", Integer.class, null);
		tournamentContainer.addContainerProperty("Sex", String.class, null);
		tournamentContainer
				.addContainerProperty("Max Age", Integer.class, null);
		tournamentContainer.addContainerProperty("Organizer Email",
				String.class, null);

		Table tournamentTable = new Table("", tournamentContainer);
		tournamentTable.addGeneratedColumn("", new ColumnGenerator() {
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
				return new Button("Open Details", new ClickListener() {
					private static final long serialVersionUID = 894487094616791181L;

					@Override
					public void buttonClick(ClickEvent event) {
						Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
						home.showTournamentCalendarView(t);
					}
				});
			}
		});

		for (Tournament t : tournamentList) {
			tournamentTable.addItem(
					new Object[] { t.getName(), t.getYear(),
							t.getSex() ? "M" : "F", t.getMaxAge(),
							t.getOrganizerEmail() }, t.getName() + t.getYear());
		}

		tableLayout.addComponent(tournamentTable);
		tableLayout.setExpandRatio(tournamentTable, 1);
		tournamentTable.setSizeFull();

		mainLayout.addComponent(tableLayout);
		mainLayout.setExpandRatio(tableLayout, 1);
		tableLayout.setSizeFull();

		mainLayout.setSizeFull();

		// VerticalLayout tournamentLayout = new VerticalLayout();
		// tournamentLayout.setMargin(new MarginInfo(false, false, false,
		// true));
		// for (Tournament t : tournamentList) {
		// String tournamentCaption = t.getName() + " " + t.getYear();
		// final Tournament tournament = t;
		// Button tournamentButton = new Button(tournamentCaption,
		// new ClickListener() {
		// private static final long serialVersionUID = -8006110621462013098L;
		//
		// @Override
		// public void buttonClick(ClickEvent event) {
		// Home home = ((MyVaadinUI) UI.getCurrent())
		// .getHome();
		// home.showTournamentCalendarView(tournament);
		// }
		// });
		//
		// tournamentLayout.addComponent(tournamentButton);
		// }
		// mainLayout.addComponent(tournamentLayout);
		//
		// Label spacer = new Label();
		// mainLayout.addComponent(spacer);
		// mainLayout.setExpandRatio(spacer, 1);
	}

	public VerticalLayout getContent() {
		return mainLayout;
	}
}
