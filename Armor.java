package vft;

 

public enum Armor {
	
	None(0, 0),
	Leather(2, 2),
	Chain(3, 4),
	Plate(5, 6);
	
	public final int hits, mod;
	Armor(int hits, int mod) {
		this.hits = hits;
		this.mod = mod;
	}
}