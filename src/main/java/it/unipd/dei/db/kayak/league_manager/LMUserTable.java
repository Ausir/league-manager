package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.LMUser;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Table;

public class LMUserTable extends Table {
	// private StringPropertyFilter firstNameFilter;
	// private StringPropertyFilter lastNameFilter;
	// private StringPropertyFilter emailFilter;
	// private StringPropertyFilter phoneFilter;

	public LMUserTable() {
		super("");

		Container userContainer = (IndexedContainer) this
				.getContainerDataSource();
		userContainer.addContainerProperty("First Name", String.class, null);
		userContainer.addContainerProperty("Last Name", String.class, null);
		userContainer.addContainerProperty("Email", String.class, null);
		userContainer.addContainerProperty("Phone", String.class, null);
		// userContainer.addContainerProperty("Birthday", Date.class, null);

		// Filterable filterable = (Filterable) userContainer;
		// firstNameFilter = new StringPropertyFilter("", "First Name");
		// filterable.addContainerFilter(firstNameFilter);
		// lastNameFilter = new StringPropertyFilter("", "Last Name");
		// filterable.addContainerFilter(lastNameFilter);
		// emailFilter = new StringPropertyFilter("", "Email");
		// filterable.addContainerFilter(emailFilter);
		// phoneFilter = new StringPropertyFilter("", "Phone");
		// filterable.addContainerFilter(phoneFilter);

		this.setContainerDataSource(userContainer);
	}

	public void addLMUser(LMUser user) {
		this.addItem(new Object[] { user.getFirstName(), user.getLastName(),
				user.getEmail(), user.getPhone() }, user.getEmail());
	}

	public void removeLMUser(String userEmail) {
		this.removeItem(userEmail);
	}

	public boolean hasLMUser(String userEmail) {
		return this.containsId(userEmail);
	}
}
