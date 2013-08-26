package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.LMUser;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Table;
 
// table for system users, with no filters
public class LMUserTable extends Table {

	public LMUserTable() {
		super("");

		Container userContainer = (IndexedContainer) this
				.getContainerDataSource();
		userContainer.addContainerProperty("Nome", String.class, null);
		userContainer.addContainerProperty("Cognome", String.class, null);
		userContainer.addContainerProperty("Email", String.class, null);
		userContainer.addContainerProperty("Telefono", String.class, null);

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
