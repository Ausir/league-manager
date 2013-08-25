package it.unipd.dei.db.kayak.league_manager.data;

public class LMUserDetails {
	private LMUser userData;

	private boolean sectretary;
	private boolean manager;
	private long managedClubID;
	private String managedClubName;
	
	public LMUserDetails(LMUser userData, boolean sectretary, boolean manager,
			long managedClubID, String managedClubName) {
		super();
		this.userData = userData;
		this.sectretary = sectretary;
		this.manager = manager;
		this.managedClubID = managedClubID;
		this.managedClubName = managedClubName;
	}

	public LMUser getUserData() {
		return userData;
	}

	public boolean isSectretary() {
		return sectretary;
	}

	public boolean isManager() {
		return manager;
	}

	public long getManagedClubID() {
		return managedClubID;
	}

	public String getManagedClubName() {
		return managedClubName;
	}
}
