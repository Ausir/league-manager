package it.unipd.dei.db.kayak.league_manager.data;

public class TournamentEssentials implements Comparable<TournamentEssentials>{
	
	private String name;
	private int year;
	private int num;
	
	public TournamentEssentials (String name, int year, int num) {
		this.name = name;
		this.year = year;
		this.num = num;
	}
	
	public int getNum() {
		return num;
	}

	public String getName() {
		return name;
	}

	public int getYear() {
		return year;
	}

	@Override
	public int compareTo(TournamentEssentials o) {
		return this.getNum() - o.getNum();
	}
}
