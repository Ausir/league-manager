package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.FakeDataWarehouse;
import it.unipd.dei.db.kayak.league_manager.data.Tournament;

import java.util.List;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class Home {
	// private fields
	private VerticalLayout mainLayout;
	private HorizontalLayout headerLayout;
	private HorizontalLayout buttonsLayout;
	private HorizontalLayout bodyLayout;
	private VerticalLayout leftBar;
	private VerticalLayout mainAreaLayout;

	// constructor
	public Home() {
		this.setUpContent();
	}

	private void setUpContent() {
		FakeDataWarehouse.initFakeData();

		mainLayout = new VerticalLayout();

		headerLayout = new HorizontalLayout();
		Label bannerLabel = new Label(
				"PROTOTYPE BANNER - FICK LOGO + OTHER STUFF");
		headerLayout.addComponent(bannerLabel);
		headerLayout.setComponentAlignment(bannerLabel, Alignment.TOP_LEFT);
		headerLayout.setHeight("80px");
		headerLayout.setWidth("100%");

		buttonsLayout = new HorizontalLayout();
		Button btn1 = new Button("Button1");
		buttonsLayout.addComponent(btn1);
		btn1.setWidth("200px");
		Button btn2 = new Button("Button2");
		buttonsLayout.addComponent(btn2);
		btn2.setWidth("200px");
		Button btn3 = new Button("Button3");
		buttonsLayout.addComponent(btn3);
		btn3.setWidth("200px");
		Label buttonsSpacer = new Label();
		buttonsLayout.addComponent(buttonsSpacer);
		buttonsLayout.setExpandRatio(buttonsSpacer, 1);
		buttonsLayout.setHeight("40px");
		buttonsLayout.setWidth("100%");

		bodyLayout = new HorizontalLayout();

		leftBar = new VerticalLayout();
		leftBar.addComponent(new Label("Most recent Tournaments"));
		List<Tournament> tournaments = FakeDataWarehouse
				.getMostRecentTournaments();
		for (final Tournament t : tournaments) {
			leftBar.addComponent(new Button(t.getName() + " - " + t.getYear(),
					new ClickListener() {
						private static final long serialVersionUID = -1853835079781595641L;

						public void buttonClick(ClickEvent event) {
							mainAreaLayout.removeAllComponents();
							mainAreaLayout
									.addComponent(new TournamentCalendarViewer(
											t).getContent());
						}
					}));
		}
		Label leftBarSpacer = new Label();
		leftBar.addComponent(leftBarSpacer);
		leftBar.setExpandRatio(leftBarSpacer, 1);
		leftBar.setWidth("150px");
		leftBar.setHeight("100%");

		mainAreaLayout = new VerticalLayout();
		mainAreaLayout.addComponent(new Label("Main Area Layout"));
		mainAreaLayout.setSizeFull();

		bodyLayout.addComponent(leftBar);
		bodyLayout.addComponent(mainAreaLayout);
		bodyLayout.setExpandRatio(mainAreaLayout, 1);
		bodyLayout.setSizeFull();

		mainLayout.addComponent(headerLayout);
		mainLayout.addComponent(buttonsLayout);
		mainLayout.addComponent(bodyLayout);
		mainLayout.setExpandRatio(bodyLayout, 1);

		mainLayout.setSizeFull();
	}

	public VerticalLayout getContent() {
		return mainLayout;
	}
}
