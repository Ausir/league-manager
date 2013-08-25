package it.unipd.dei.db.kayak.league_manager;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

public class StringPropertyFilter implements Container.Filter {
	private String filter;
	private Object propertyID;

	public StringPropertyFilter(String text, Object propertyID) {
		filter = text.toLowerCase();
		this.propertyID = propertyID;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String txt) {
		this.filter = txt.toLowerCase();
	}

	@Override
	public boolean passesFilter(Object itemId, Item item)
			throws UnsupportedOperationException {
		String property = ((String) item.getItemProperty(propertyID).getValue());
		if (property == null) {
			return true;
		}
		return property.toLowerCase().lastIndexOf(filter) != -1;
	}

	@Override
	public boolean appliesToProperty(Object propertyId) {
		return propertyID.equals(propertyId);
	}
}
