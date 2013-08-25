package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.LMUserDetails;
import it.unipd.dei.db.kayak.league_manager.data.Ownership;
import it.unipd.dei.db.kayak.league_manager.data.Player;
import it.unipd.dei.db.kayak.league_manager.database.DML;

import java.sql.Date;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class AddOwnershipView {
	private VerticalLayout mainlLayout;

	public AddOwnershipView() {
		this.setUpContent();
	}

	private void setUpContent() {
		mainlLayout = new VerticalLayout();
		mainlLayout.setMargin(new MarginInfo(true, true, true, true));

		mainlLayout.addComponent(new Label("Add player to database"));

		mainlLayout.addComponent(new Label("Club ID"));
		final TextField clubIdField = new TextField();
		mainlLayout.addComponent(clubIdField);

		mainlLayout.addComponent(new Label("Player ID"));
		final TextField playerIdField = new TextField();
		mainlLayout.addComponent(playerIdField);

		mainlLayout.addComponent(new Label("Start Date"));
		HorizontalLayout startDateLayout = new HorizontalLayout();
		final TextField startDayField = new TextField("Day");
		startDateLayout.addComponent(startDayField);
		final TextField startMonthField = new TextField("Month");
		startDateLayout.addComponent(startMonthField);
		final TextField startYearField = new TextField("Year");
		startDateLayout.addComponent(startYearField);
		mainlLayout.addComponent(startDateLayout);

		mainlLayout.addComponent(new Label("End Date"));
		HorizontalLayout endDateLayout = new HorizontalLayout();
		final TextField endDayField = new TextField("Day");
		endDateLayout.addComponent(endDayField);
		final TextField endMonthField = new TextField("Month");
		endDateLayout.addComponent(endMonthField);
		final TextField endYearField = new TextField("Year");
		endDateLayout.addComponent(endYearField);
		mainlLayout.addComponent(endDateLayout);

		final CheckBox borrowedCheckBox = new CheckBox("Borrowed", false);
		mainlLayout.addComponent(borrowedCheckBox);

		HorizontalLayout commitLayout = new HorizontalLayout();
		Button commitButton = new Button("Commit", new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
				LMUserDetails user = home.getLoggedInUser();
				if (user == null || !user.isSectretary()) {
					Notification
							.show("Current user doesn't have the necessary privileges");
				}

				Ownership ownership = null;
				try {
					long clubID = Long.parseLong(clubIdField.getValue());
					long playerID = Long.parseLong(playerIdField.getValue());
					boolean borrowed = borrowedCheckBox.getValue();

					int day = Integer.parseInt(startDayField.getValue());
					int month = Integer.parseInt(startMonthField.getValue()) - 1;
					int year = Integer.parseInt(startYearField.getValue()) - 1900;
					Date startDate = new Date(year, month, day);

					day = Integer.parseInt(startDayField.getValue());
					month = Integer.parseInt(startMonthField.getValue()) - 1;
					year = Integer.parseInt(startYearField.getValue()) - 1900;
					Date endDate = new Date(year, month, day);

					ownership = new Ownership(0, playerID, clubID, borrowed,
							startDate, endDate);
				} catch (Exception e) {
					Notification.show("Error in input values");
				}

				if (!DML.addOwnership(ownership)) {
					Notification.show("Error during commit");
				} else {
					Notification.show("Ownership correctly added");
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
