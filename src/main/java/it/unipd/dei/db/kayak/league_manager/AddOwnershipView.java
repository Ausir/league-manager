package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.LMUserDetails;
import it.unipd.dei.db.kayak.league_manager.data.Ownership;
import it.unipd.dei.db.kayak.league_manager.data.OwnershipResult;
import it.unipd.dei.db.kayak.league_manager.database.DML;

import java.sql.Date;
import java.util.List;

import org.apache.commons.jexl2.UnifiedJEXL.Exception;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class AddOwnershipView {
	private VerticalLayout mainLayout;

	public AddOwnershipView() {
		this.setUpContent();
	}

	private void setUpContent() {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(new MarginInfo(true, true, true, true));

		mainLayout.addComponent(new Label("Aggiungi contratto al database"));

		mainLayout.addComponent(new Label("ID della società"));
		final TextField clubIdField = new TextField();
		mainLayout.addComponent(clubIdField);

		mainLayout.addComponent(new Label("ID del giocatore"));
		final TextField playerIdField = new TextField();
		mainLayout.addComponent(playerIdField);

		final DateField startDateField = new DateField("Inizio del contratto");
		startDateField.setWidth("60px");
		mainLayout.addComponent(startDateField);

		final DateField endDateField = new DateField("Inizio del contratto");
		endDateField.setWidth("60px");
		mainLayout.addComponent(endDateField);

		final CheckBox borrowedCheckBox = new CheckBox("In prestito", false);
		mainLayout.addComponent(borrowedCheckBox);

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

				Ownership ownership = null;
				MyVaadinUI ui = (MyVaadinUI) UI.getCurrent();
				try {

					long clubID = Long.parseLong(clubIdField.getValue());
					long playerID = Long.parseLong(playerIdField.getValue());
					boolean borrowed = borrowedCheckBox.getValue();

					java.util.Date rawDate = startDateField.getValue();
					Date startDate = new Date(rawDate.getTime());

					rawDate = startDateField.getValue();
					Date endDate = new Date(rawDate.getTime());

					if (startDate.before(endDate)) {
						Notification
								.show("La data di inizio contratto non precede la data fine contratto");
						return;
					}

					ownership = new Ownership(0, playerID, clubID, borrowed,
							startDate, endDate);

					if (!borrowed) {
						List<OwnershipResult> playerOwnerships = DML
								.retrieveOwnershipsFromPlayer(
										ui.getConnection(), playerID);
						for (OwnershipResult or : playerOwnerships) {
							if ((startDate.compareTo(or.getStart()) <= 0 && endDate
									.compareTo(or.getStart()) >= 0)
									|| (startDate.compareTo(or.getEnd()) <= 0 && endDate
											.compareTo(or.getEnd()) >= 0)
									|| (startDate.compareTo(or.getStart()) >= 0 && endDate
											.compareTo(or.getEnd()) <= 0)) {
								Notification
										.show("Il contratto da inserire si sovrappone ad un contratto già registrato");
								return;
							}
						}
					}
				} catch (Exception e) {
					Notification.show("Dati di input non validi");
				}

				if (!DML.addOwnership(ui.getConnection(), ownership)) {
					Notification.show("Errore nell'inserimento");
				} else {
					Notification.show("Contratto inserito correttamente");
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
