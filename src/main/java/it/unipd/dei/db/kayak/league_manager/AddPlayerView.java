package it.unipd.dei.db.kayak.league_manager;

import java.sql.Date;

import it.unipd.dei.db.kayak.league_manager.data.LMUserDetails;
import it.unipd.dei.db.kayak.league_manager.data.Player;
import it.unipd.dei.db.kayak.league_manager.database.DML;

import org.apache.commons.jexl2.UnifiedJEXL.Exception;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class AddPlayerView {
	private VerticalLayout mainLayout;

	public AddPlayerView() {
		this.setUpContent();
	}

	private void setUpContent() {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(new MarginInfo(true, true, true, true));

		mainLayout.addComponent(new Label("Aggiungi giocatore al database"));

		mainLayout.addComponent(new Label("ID"));
		final TextField idField = new TextField();
		mainLayout.addComponent(idField);

		mainLayout.addComponent(new Label("Nome"));
		final TextField nameField = new TextField();
		mainLayout.addComponent(nameField);

		final DateField birthdayField = new DateField("Giorno di nascita");
		birthdayField.setWidth("60px");
		mainLayout.addComponent(birthdayField);

		HorizontalLayout commitLayout = new HorizontalLayout();
		Button commitButton = new Button("Inserisci", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				LMUserDetails user = home.getLoggedInUser();
				if (user == null || !user.isSectretary()) {
					Notification
							.show("L'utente corrente non ha i privilegi necessari");
				}

				Player player = null;
				try {
					long id = Long.parseLong(idField.getValue());
					String name = nameField.getValue();

					java.util.Date rawDate = birthdayField.getValue();
					Date birthday = new Date(rawDate.getTime());

					player = new Player(id, name, birthday);
				} catch (Exception e) {
					Notification.show("Dati di input non validi");
				}

				MyVaadinUI ui = (MyVaadinUI) UI.getCurrent();
				if (!DML.addPlayer(ui.getConnection(), player)) {
					Notification.show("Errore nell'inserimento");
				} else {
					Notification.show("Giocatore inserito correttamente");
				}
			}
		});
		
		commitLayout.addComponent(commitButton);
		commitLayout.setComponentAlignment(commitButton, Alignment.TOP_CENTER);
		mainLayout.addComponent(commitLayout);

		Label spacer = new Label();
		mainLayout.addComponent(spacer);
		mainLayout.setExpandRatio(spacer, 1);
	}

	public VerticalLayout getContent() {
		return mainLayout;
	}
}
