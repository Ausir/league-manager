package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.LMUserDetails;
import it.unipd.dei.db.kayak.league_manager.data.Player;
import it.unipd.dei.db.kayak.league_manager.database.DML;

import java.sql.Date;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class AddPlayerView {
	private VerticalLayout mainlLayout;

	public AddPlayerView() {
		this.setUpContent();
	}

	private void setUpContent() {
		mainlLayout = new VerticalLayout();
		mainlLayout.setMargin(new MarginInfo(true, true, true, true));

		mainlLayout.addComponent(new Label("Aggiungi giocatore al database"));

		mainlLayout.addComponent(new Label("ID"));
		final TextField idField = new TextField();
		mainlLayout.addComponent(idField);

		mainlLayout.addComponent(new Label("Nome"));
		final TextField nameField = new TextField();
		mainlLayout.addComponent(nameField);

		mainlLayout.addComponent(new Label("Data di nascita"));
		HorizontalLayout dateLayout = new HorizontalLayout();
		final TextField dayField = new TextField("Giorno");
		dateLayout.addComponent(dayField);
		final TextField monthField = new TextField("Mese");
		dateLayout.addComponent(monthField);
		final TextField yearField = new TextField("Anno");
		dateLayout.addComponent(yearField);
		mainlLayout.addComponent(dateLayout);

		HorizontalLayout commitLayout = new HorizontalLayout();
		Button commitButton = new Button("Inserisci", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				LMUserDetails user = home.getLoggedInUser();
				if (user == null || !user.isSectretary()) {
					Notification
							.show("Current user doesn't have the necessary privileges");
				}

				Player player = null;
				try {
					long id = Long.parseLong(idField.getValue());
					String name = nameField.getValue();

					int day = Integer.parseInt(dayField.getValue());
					int month = Integer.parseInt(monthField.getValue()) - 1;
					int year = Integer.parseInt(yearField.getValue()) - 1900;
					Date birthday = new Date(year, month, day);

					player = new Player(id, name, birthday);
				} catch (Exception e) {
					Notification.show("Error in input values");
				}

				MyVaadinUI ui=(MyVaadinUI) UI.getCurrent();
				if (!DML.addPlayer(ui.getConnection(), player)) {
					Notification.show("Error during commit");
				} else {
					Notification.show("Player correctly added");
				}
			}
		});
		commitLayout.addComponent(commitButton);
		commitLayout.setComponentAlignment(commitButton, Alignment.TOP_CENTER);
		mainlLayout.addComponent(commitLayout);

		Label spacer = new Label();
		mainlLayout.addComponent(spacer);
		mainlLayout.setExpandRatio(spacer, 1);
	}

	public VerticalLayout getContent() {
		return mainlLayout;
	}
}
