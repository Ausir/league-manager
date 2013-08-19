package it.unipd.dei.db.kayak.league_manager.data;

import java.util.List;

public class ClubDetails {
	private long clubID;

	private String name;
	private String phone;
	private String address;
	private String email;
	private String website;
	private List<Player> clubPlayers;

	public ClubDetails(long clubID, String name, String phone, String address,
			String email, String website, List<Player> clubPlayers) {
		super();
		this.clubID = clubID;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.website = website;
		this.clubPlayers = clubPlayers;
	}

	public long getClubID() {
		return clubID;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public String getWebsite() {
		return website;
	}

	public List<Player> getClubPlayers() {
		return clubPlayers;
	}
}
